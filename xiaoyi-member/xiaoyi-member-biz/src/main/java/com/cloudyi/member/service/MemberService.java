package com.cloudyi.member.service;

import com.cloudyi.member.domain.*;

import java.util.List;

/**
 * @author subo
 * @date 2023/7/9 14:29
 **/
public interface MemberService {

    MemberAuthResult auth(String code);

    MemberDetailDO queryMemberDetail(Long id);

    void update(MemberDetailDO id);

    List<MemberCategory> queryMemberCategory(MemberCategoryCondition memberCategoryCondition);

    String createMemberRedeemCode(MemberRedeemCode memberRedeemCode);
    void useMemberRedeemCode(MemberRedeemCode memberRedeemCode);

    void memberInvite(MemberInvite memberInvite);

    MemberInviteQueryResult queryMemberInvite(Long memberId);
}
