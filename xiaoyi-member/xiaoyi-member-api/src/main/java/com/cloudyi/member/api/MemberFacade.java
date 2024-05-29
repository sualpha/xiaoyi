package com.cloudyi.member.api;

import com.cloudyi.member.api.dto.*;

import java.util.List;

/**
 * @author subo
 * @date 2023/7/9 14:09
 **/
public interface MemberFacade {

    MemberAuthResultDTO auth(String code);
    MemberDetailDTO queryMemberDetail(Long id);

    void update(MemberDetailDTO memberDetailDTO);
    List<MemberCategoryDTO> queryMemberCategory(MemberCategoryQueryDTO memberCategoryQueryDTO);
    String createMemberRedeemCode(MemberRedeemCodeCreateDTO memberRedeemCodeCreateDTO);
    void useMemberRedeemCode(MemberRedeemCodeUseDTO memberRedeemCodeUseDTO);
    void memberInvite(MemberInviteDTO memberInviteDTO);
    MemberInviteQueryResultDTO queryMemberInvite(Long memberId);
}
