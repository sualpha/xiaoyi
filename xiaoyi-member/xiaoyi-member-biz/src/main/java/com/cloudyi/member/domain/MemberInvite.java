package com.cloudyi.member.domain;

import com.cloudyi.member.api.dto.MemberInviteDTO;
import com.cloudyi.member.domain.entity.MemberInviteEntity;
import lombok.Data;

@Data
public class MemberInvite {

    private Long memberId;

    private Long memberInviteId;

    public MemberInviteEntity toMemberInviteEntity(MemberInvite memberInvite){
        MemberInviteEntity memberInviteEntity = new MemberInviteEntity();
        memberInviteEntity.setMemberId(memberInvite.getMemberId());
        memberInviteEntity.setMemberInviteId(memberInvite.getMemberInviteId());
        return memberInviteEntity;
    }

    public static MemberInvite toMemberInvite(MemberInviteDTO memberInviteDTO){
        MemberInvite memberInvite = new MemberInvite();
        memberInvite.setMemberId(memberInviteDTO.getMemberInitiateId());
        memberInvite.setMemberInviteId(memberInviteDTO.getMemberId());
        return memberInvite;
    }
}
