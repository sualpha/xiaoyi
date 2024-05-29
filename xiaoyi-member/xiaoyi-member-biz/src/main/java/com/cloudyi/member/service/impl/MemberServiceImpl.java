package com.cloudyi.member.service.impl;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.cloudyi.member.domain.*;
import com.cloudyi.member.domain.entity.*;
import com.cloudyi.member.enums.ApplicationConstant;
import com.cloudyi.member.enums.MemberCategoryOriginEnum;
import com.cloudyi.member.enums.MemberRedeemCodeEnums;
import com.cloudyi.member.mapper.*;
import com.cloudyi.member.service.MemberService;
import com.cloudyi.member.util.MemberRedeemCodeUtil;
import com.cloudyi.starter.web.exception.ServiceException;
import com.cloudyi.wechat.api.WechatFacade;
import com.cloudyi.wechat.api.dto.WechatSessionDTO;
import com.cloudyi.baseinfo.api.CategoryFacade;
import com.cloudyi.baseinfo.api.IconFacade;
import com.cloudyi.baseinfo.api.dto.CategoryDTO;
import com.cloudyi.baseinfo.api.dto.IconDTO;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author subo
 * @date 2023/7/9 14:33
 **/
@Lazy
@Service
public class MemberServiceImpl implements MemberService {

    @Resource
    private MemberMapper mapper;

    @Resource
    private WechatFacade wechatFacade;

    @Resource
    private MemberCategoryMapper memberCategoryMapper;

    @Resource
    private IconFacade iconFacade;
    @Resource
    private CategoryFacade categoryFacade;

    @Resource
    private MemberRedeemCodeMapper memberRedeemCodeMapper;

    @Resource
    private MemberRechargeRecordsMapper memberRechargeRecordsMapper;

    @Resource
    private MemberInviteMapper memberInviteMapper;

    @Override
    public MemberAuthResult auth(String code) {
        WechatSessionDTO session = wechatFacade.getSession(code);
        MemberEntity memberEntity = mapper.selectByOpenId(session.getOpenId());
        if(Objects.isNull(memberEntity)){
            MemberEntity createMemberEntity =new MemberEntity();
            createMemberEntity.setOpenId(session.getOpenId());
            String name = RandomUtil.randomNumbers(8);
            createMemberEntity.setNickName("微信用户"+name);
            //新用户会员免费1周
            DateTime dateTime = DateUtil.offsetMonth(DateUtil.date(), 12 * 10);
            //DateTime dateTime = DateUtil.offsetDay(DateUtil.date(), 7);
            createMemberEntity.setVipExpiresTime(dateTime);
            mapper.insert(createMemberEntity);
            Long memberId=createMemberEntity.getId();
            List<CategoryDTO> categoryDTOS = categoryFacade.queryAllList();
            for (int i = 0; i < categoryDTOS.size(); i++) {
                MemberCategoryEntity memberCategory=new MemberCategoryEntity();
                CategoryDTO categoryDTO = categoryDTOS.get(i);
                memberCategory.setCategoryId(categoryDTO.getId());
                memberCategory.setMemberId(memberId);
                memberCategory.setOrigin(1);
                memberCategory.setSort(i);
                memberCategory.setType(categoryDTO.getType());
                memberCategoryMapper.insert(memberCategory);
            }

            // 执行登录
            StpUtil.login(memberId, SaLoginModel.create()
                    .setExtra(ApplicationConstant.MINI_JWT_NICKNAME, createMemberEntity.getNickName())
                    .setExtra(ApplicationConstant.MINI_JWT_EXTRA_MEMBER_ID, memberId));
            return MemberAuthResult.builder().token(StpUtil.getTokenValue()).memberId(memberId).build();
        }else{
            // 执行登录
            StpUtil.login(memberEntity.getId(), SaLoginModel.create()
                    .setExtra(ApplicationConstant.MINI_JWT_NICKNAME, memberEntity.getNickName())
                    .setExtra(ApplicationConstant.MINI_JWT_EXTRA_MEMBER_ID, memberEntity.getId()));
            return MemberAuthResult.builder().token(StpUtil.getTokenValue()).memberId(memberEntity.getId()).build();
        }
    }

    @Override
    public MemberDetailDO queryMemberDetail(Long id) {
        MemberEntity memberEntity = mapper.selectById(id);
        return MemberDetailDO.fromEntity(memberEntity);
    }

    @Override
    public void update(MemberDetailDO memberDetailDO) {
        MemberEntity entity = memberDetailDO.toUpdateNickNameAndAvatarUrlEntity();
        mapper.updateMemberEntity(entity);
    }

    @Override
    public List<MemberCategory> queryMemberCategory(MemberCategoryCondition memberCategoryCondition) {
        List<MemberCategoryEntity> memberCategoryEntities = memberCategoryMapper.queryMemberCategory(memberCategoryCondition);
        if(CollUtil.isNotEmpty(memberCategoryEntities)){
            List<MemberCategory> list = memberCategoryEntities.stream().map(MemberCategory::fromEntity).collect(Collectors.toList());
            complete(list);
            return list;
        }
        return Collections.emptyList();
    }

    @Override
    public String createMemberRedeemCode(MemberRedeemCode memberRedeemCode) {
        String redeemCode = MemberRedeemCodeUtil.generateMemberRedeemCode();
        MemberRedeemCodeEntity memberRedeemCodeEntity = memberRedeemCode.toEntity(redeemCode);
        memberRedeemCodeMapper.insert(memberRedeemCodeEntity);
        return redeemCode;
    }

    @Override
    @Transactional
    public void useMemberRedeemCode(MemberRedeemCode memberRedeemCode) {
        MemberRedeemCodeEntity memberRedeemCodeEntity = memberRedeemCodeMapper.selectOneByRedeemCode(memberRedeemCode.getRedeemCode(), memberRedeemCode.getMemberId());
        if(Objects.isNull(memberRedeemCodeEntity)){
            throw new ServiceException("兑换码不存在");
        }
        if(MemberRedeemCodeEnums.MemberRedeemCodeStatusEnum.USED.getCode().equals(memberRedeemCodeEntity.getStatus())){
            throw new ServiceException("兑换码已使用");
        }
        MemberEntity memberEntity = mapper.selectById(memberRedeemCode.getMemberId());
        Integer unit = memberRedeemCodeEntity.getUnit();
        Integer num = memberRedeemCodeEntity.getNum();
        DateField dateField=null;
        if(MemberRedeemCodeEnums.MemberRedeemCodeUnitEnum.MONTH.getCode().equals(unit)){
            dateField =DateField.MONTH;
        }
        if(MemberRedeemCodeEnums.MemberRedeemCodeUnitEnum.YEAR.getCode().equals(unit)){
            dateField =DateField.YEAR;
        }
        if(Objects.isNull(dateField)){
            return;
        }
        Date currentDate = new Date();
        DateTime initialTime;
        DateTime finalTime;
        if(memberEntity.getVipExpiresTime().after(currentDate)){
             initialTime = DateUtil.offset(memberEntity.getVipExpiresTime(), dateField, num);
             finalTime = DateUtil.offset(memberEntity.getVipExpiresTime(), dateField, num);
        }else {
             initialTime = DateUtil.offset(currentDate, dateField, num);
             finalTime = DateUtil.offset(currentDate, dateField, num);
        }
        MemberEntity member=new MemberEntity();
        DateTime dateTime = DateUtil.endOfDay(finalTime);
        member.setVipExpiresTime(dateTime);
        member.setId(memberRedeemCode.getMemberId());
        mapper.updateMemberEntity(member);

        MemberRechargeRecordsEntity rechargeRecords=new MemberRechargeRecordsEntity();
        rechargeRecords.setMemberId(memberRedeemCode.getMemberId());
        rechargeRecords.setInitialTime(initialTime);
        rechargeRecords.setFinalTime(dateTime);
        memberRechargeRecordsMapper.insert(rechargeRecords);

        MemberRedeemCodeEntity newMemberRedeemCodeEntity=new MemberRedeemCodeEntity();
        newMemberRedeemCodeEntity.setId(memberRedeemCodeEntity.getId());
        newMemberRedeemCodeEntity.setStatus(MemberRedeemCodeEnums.MemberRedeemCodeStatusEnum.USED.getCode());
        memberRedeemCodeMapper.updateById(newMemberRedeemCodeEntity);

    }

    @Override
    @Transactional
    public void memberInvite(MemberInvite memberInvite) {
       if(memberInvite.getMemberId().equals(memberInvite.getMemberInviteId())){
            return;
        }
        MemberEntity newMemberEntity = mapper.selectById(memberInvite.getMemberInviteId());
       if(Objects.nonNull(newMemberEntity) && newMemberEntity.getCreateTime().before(DateUtil.offset(DateUtil.date(),DateField.MINUTE,5))){
           MemberInviteEntity memberInviteEntity = memberInvite.toMemberInviteEntity(memberInvite);
           MemberInviteEntity memberInviteEntityExist = memberInviteMapper.queryMemberSelectOne(memberInviteEntity);
           if(Objects.isNull(memberInviteEntityExist)){
               memberInviteMapper.insert(memberInviteEntity);

               MemberInviteQueryResult memberInviteQueryResult = queryMemberInvite(memberInvite.getMemberId());
               if(memberInviteQueryResult.getInviteCount() < 15){
                   MemberEntity memberEntity = mapper.selectById(memberInvite.getMemberId());
                   Date currentDate = new Date();
                   DateTime finalTime;
                   if(memberEntity.getVipExpiresTime().after(currentDate)){
                       finalTime = DateUtil.offsetDay(memberEntity.getVipExpiresTime(), 2);
                   }else {
                       finalTime = DateUtil.offsetDay(memberEntity.getVipExpiresTime(), 2);
                   }
                   MemberEntity member=new MemberEntity();
                   DateTime dateTime = DateUtil.endOfDay(finalTime);
                   member.setVipExpiresTime(dateTime);
                   member.setId(memberInvite.getMemberId());
                   mapper.updateMemberEntity(member);
               }
           }
       }
    }

    @Override
    public MemberInviteQueryResult queryMemberInvite(Long memberId) {
        List<MemberInviteEntity> memberInviteEntities = memberInviteMapper.queryListByMemberId(memberId);
        MemberInviteQueryResult memberInviteQueryResult=new MemberInviteQueryResult();
        if(CollUtil.isNotEmpty(memberInviteEntities)){
            int count = (int) memberInviteEntities.stream().count();
            memberInviteQueryResult.setInviteCount(count);
            if(count<=15){
                memberInviteQueryResult.setInviteDay(count * 2);
            }else {
                memberInviteQueryResult.setInviteDay(30);
            }
        }else {
            memberInviteQueryResult.setInviteCount(0);
            memberInviteQueryResult.setInviteDay(0);
        }
        return memberInviteQueryResult;
    }

    private void complete(List<MemberCategory> list) {
        List<Long> categoryIds = list.stream().filter(a -> MemberCategoryOriginEnum.SYSTEM_DEFAULT.getCode().equals(a.getOrigin())).map(MemberCategory::getCategoryId).collect(Collectors.toList());
        List<Long> iconIds = list.stream().filter(a -> MemberCategoryOriginEnum.MEMBER_CUSTOM.getCode().equals(a.getOrigin())).map(MemberCategory::getIconId).collect(Collectors.toList());
        Map<Long, List<IconDTO>> iconMap=new HashMap<>();
        Map<Long, List<CategoryDTO>> categoryMap=new HashMap<>();
        if(CollUtil.isNotEmpty(categoryIds)){
            List<CategoryDTO> categorys = categoryFacade.queryList(categoryIds);
             categoryMap = categorys.stream().collect(Collectors.groupingBy(CategoryDTO::getId));
        }
        if(CollUtil.isNotEmpty(iconIds)){
            List<IconDTO> iconDTOS = iconFacade.queryList(iconIds);
            iconMap = iconDTOS.stream().collect(Collectors.groupingBy(IconDTO::getId));
        }
        for (MemberCategory memberCategory : list) {
            if(CollUtil.isNotEmpty(iconMap)){
                List<IconDTO> iconDTOS = iconMap.get(memberCategory.getIconId());
                if(CollUtil.isNotEmpty(iconDTOS)){
                    IconDTO iconDTO = iconDTOS.get(0);
                    memberCategory.setCategoryUrl(iconDTO.getUrl());
                }
            }
            if(CollUtil.isNotEmpty(categoryMap)){
                List<CategoryDTO> categoryDTOS = categoryMap.get(memberCategory.getCategoryId());
                if(CollUtil.isNotEmpty(categoryDTOS)){
                    CategoryDTO categoryDTO = categoryDTOS.get(0);
                    memberCategory.setType(categoryDTO.getType());
                    memberCategory.setName(categoryDTO.getName());
                    memberCategory.setCategoryUrl(categoryDTO.getUrl());
                }
            }
        }

    }
}
