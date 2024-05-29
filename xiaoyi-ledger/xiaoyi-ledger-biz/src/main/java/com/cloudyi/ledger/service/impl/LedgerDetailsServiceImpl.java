package com.cloudyi.ledger.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloudyi.ledger.domain.*;
import com.cloudyi.ledger.enums.LedgerTypeEnum;
import com.cloudyi.member.api.MemberFacade;
import com.cloudyi.member.api.dto.MemberCategoryDTO;
import com.cloudyi.member.api.dto.MemberCategoryQueryDTO;
import com.cloudyi.orm.domain.query.PageResult;
import com.cloudyi.ledger.domain.entity.LedgerDetailsEntity;
import com.cloudyi.ledger.mapper.LedgerDetailsMapper;
import com.cloudyi.ledger.service.LedgerDetailsService;
import com.cloudyi.orm.util.PageUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LedgerDetailsServiceImpl implements LedgerDetailsService {

    @Resource
    private LedgerDetailsMapper ledgerDetailsMapper;

    @Resource
    private MemberFacade memberFacade;

    @Override
    public PageResult<LedgerDetails> queryPage(LedgerDetailPageCondition ledgerDetailPageCondition) {
        Page<LedgerDetailsEntity> page = ledgerDetailsMapper.selectPage(ledgerDetailPageCondition);
        PageResult<LedgerDetails> pageResult = PageUtil.toPage(page, LedgerDetails::fromEntity);
        complete(pageResult.getList());
        return pageResult;
    }

    @Override
    public LedgerDetails queryDetail(LedgerDetailCondition condition) {
        LedgerDetailsEntity ledgerDetailsEntity = ledgerDetailsMapper.selectByIdAndMemberId(condition);
        LedgerDetails ledgerDetails = LedgerDetails.fromEntity(ledgerDetailsEntity);
        complete(Collections.singletonList(ledgerDetails));
        return ledgerDetails;
    }

    @Override
    public Long create(LedgerDetails ledgerDetails) {
        LedgerDetailsEntity entity = ledgerDetails.toEntity();
        ledgerDetailsMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public Long update(LedgerDetails ledgerDetails) {
        LedgerDetailsEntity entity = ledgerDetails.toEntity();
        ledgerDetailsMapper.updateById(entity);
        return entity.getId();
    }

    @Override
    public void delete(LedgerDetails ledgerDetails) {
        LedgerDetailCondition ledgerDetailCondition=new LedgerDetailCondition();
        ledgerDetailCondition.setId(ledgerDetails.getId());
        ledgerDetailCondition.setMemberId(ledgerDetails.getMemberId());
        LedgerDetailsEntity ledgerDetailsEntity = ledgerDetailsMapper.selectByIdAndMemberId(ledgerDetailCondition);
        if(Objects.nonNull(ledgerDetailsEntity)){
            ledgerDetailsMapper.deleteById(ledgerDetails.getId());
        }
    }

    private void complete(List<LedgerDetails> list) {
        if(CollUtil.isNotEmpty(list)){
            List<Long> memberCategoryIds =list.stream().map(LedgerDetails::getMemberCategoryId).distinct().collect(Collectors.toList());
            MemberCategoryQueryDTO memberCategoryQueryDTO=new MemberCategoryQueryDTO();
            memberCategoryQueryDTO.setMemberCategoryIds(memberCategoryIds);
            List<MemberCategoryDTO> memberCategoryDTOS = memberFacade.queryMemberCategory(memberCategoryQueryDTO);
            Map<Long, List<MemberCategoryDTO>> memberCategoryMap = memberCategoryDTOS.stream().collect(Collectors.groupingBy(MemberCategoryDTO::getId));
            for (LedgerDetails ledgerDetails : list) {
                List<MemberCategoryDTO> memberCategoryExist = memberCategoryMap.get(ledgerDetails.getMemberCategoryId());
                if(CollUtil.isNotEmpty(memberCategoryExist)){
                    MemberCategoryDTO memberCategoryDTO = memberCategoryExist.get(0);
                    ledgerDetails.setCategoryUrl(memberCategoryDTO.getCategoryUrl());
                    ledgerDetails.setCategoryName(memberCategoryDTO.getCategoryName());
                }
            }
        }
    }

    @Override
    public MonthLedgerSumResultDO queryMemberMonthLedgerSum(MonthLedgerSumDO monthLedgerSumDO) {
        List<LedgerDetailsEntity> list = ledgerDetailsMapper.selectMonthLedger(
                monthLedgerSumDO.getMemberId(), monthLedgerSumDO.getStartTime(), monthLedgerSumDO.getEndTime());
        if(CollectionUtils.isEmpty(list)){
            return MonthLedgerSumResultDO.builder().sum(BigDecimal.ZERO).income(BigDecimal.ZERO)
                    .expenditure(BigDecimal.ZERO).build();
        }
        Map<Integer, BigDecimal> sunMap = list.stream().collect(Collectors.groupingBy(LedgerDetailsEntity::getType
                , Collectors.mapping(LedgerDetailsEntity::getPrice, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
        BigDecimal income = sunMap.get(LedgerTypeEnum.INCOME.getCode())==null? BigDecimal.ZERO:sunMap.get(LedgerTypeEnum.INCOME.getCode());
        BigDecimal expenses = sunMap.get(LedgerTypeEnum.EXPENSES.getCode())==null? BigDecimal.ZERO:sunMap.get(LedgerTypeEnum.EXPENSES.getCode());
        return MonthLedgerSumResultDO.builder().income(income)
                .expenditure(expenses)
                .sum(income.subtract(expenses)).build();
    }

    @Override
    public List<LedgerDetails> selectMemberLedgerListByDate(LedgerDetailsListByDateCondition queryCO) {
        List<LedgerDetailsEntity> ledgerDetailsEntities = ledgerDetailsMapper.selectList(queryCO);
        if (CollectionUtils.isEmpty(ledgerDetailsEntities)) {
            return new ArrayList<>();
        }
        List<LedgerDetails> list = ledgerDetailsEntities.stream().map(LedgerDetails::fromEntity).toList();
        complete(list);
        return list;
    }

    @Override
    public PageResult<LedgerDetails> selectLedgerDetailPageList(LedgerDetailPageListCondition condition) {
        Page<LedgerDetailsEntity> page = ledgerDetailsMapper.selectList(condition);
        PageResult<LedgerDetails> pageResult = PageUtil.toPage(page, LedgerDetails::fromEntity);
        complete(pageResult.getList());
        return pageResult;
    }

    @Override
    public MemberStatisticsResult memberStatistics(Long memberId) {
        MemberStatisticsResult memberStatisticsResult=new MemberStatisticsResult();
        Integer days = ledgerDetailsMapper.statisticsMemberLedgerDays(memberId);
        Integer counts = ledgerDetailsMapper.statisticsMemberLedgerCounts(memberId);
        memberStatisticsResult.setLedgerDays(days);
        memberStatisticsResult.setLedgerCount(counts);
        return memberStatisticsResult;
    }
}
