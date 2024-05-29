package com.cloudyi.member.api;

import com.cloudyi.common.util.ListUtil;
import com.cloudyi.member.api.dto.*;
import com.cloudyi.member.convertor.MemberConvertor;
import com.cloudyi.member.domain.*;
import com.cloudyi.member.service.MemberService;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author subo
 * @date 2023/7/9 14:17
 **/
@Lazy
@Service
public class MemberFacadeImpl implements MemberFacade {

    @Resource
    private MemberService memberService;

    @Override
    public MemberAuthResultDTO auth(String code) {
        MemberAuthResult auth = memberService.auth(code);
        return MemberConvertor.INSTANCE.convertor(auth);
    }

    @Override
    public MemberDetailDTO queryMemberDetail(Long id) {
        MemberDetailDO memberDetailDO = memberService.queryMemberDetail(id);
        return MemberConvertor.INSTANCE.convertor(memberDetailDO);
    }

    @Override
    public void update(MemberDetailDTO memberDetailDTO) {
        MemberDetailDO memberDetailDO = MemberConvertor.INSTANCE.convertor(memberDetailDTO);
        memberService.update(memberDetailDO);
    }

    @Override
    public List<MemberCategoryDTO> queryMemberCategory(MemberCategoryQueryDTO memberCategoryQueryDTO) {
        MemberCategoryCondition condition = MemberConvertor.INSTANCE.convertor(memberCategoryQueryDTO);
        List<MemberCategory> list = memberService.queryMemberCategory(condition);
        return ListUtil.toListOrEmpty(list,MemberConvertor.INSTANCE::convertor);
    }

    @Override
    public String createMemberRedeemCode(MemberRedeemCodeCreateDTO memberRedeemCodeCreateDTO) {
        MemberRedeemCode memberRedeemCode = MemberRedeemCode.toMemberRedeemCode(memberRedeemCodeCreateDTO);
        return memberService.createMemberRedeemCode(memberRedeemCode);
    }

    @Override
    public void useMemberRedeemCode(MemberRedeemCodeUseDTO memberRedeemCodeUseDTO) {
        MemberRedeemCode memberRedeemCode = MemberRedeemCode.toMemberRedeemCode(memberRedeemCodeUseDTO);
        memberService.useMemberRedeemCode(memberRedeemCode);
    }

    @Override
    public void memberInvite(MemberInviteDTO memberInviteDTO) {
        MemberInvite memberInvite = MemberInvite.toMemberInvite(memberInviteDTO);
        memberService.memberInvite(memberInvite);
    }

    @Override
    public MemberInviteQueryResultDTO queryMemberInvite(Long memberId) {
        MemberInviteQueryResult memberInviteQueryResult = memberService.queryMemberInvite(memberId);
        return memberInviteQueryResult.toMemberInviteQueryResultDTO();
    }
}
