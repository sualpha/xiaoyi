
package com.cloudyi.member.domain;


import com.cloudyi.member.api.dto.MemberInviteQueryResultDTO;
import lombok.Data;

@Data
public class MemberInviteQueryResult {

    private Integer inviteCount;

    private Integer inviteDay;

    public MemberInviteQueryResultDTO toMemberInviteQueryResultDTO(){
        MemberInviteQueryResultDTO memberInviteQueryResultDTO=new MemberInviteQueryResultDTO();
        memberInviteQueryResultDTO.setInviteCount(this.getInviteCount());
        memberInviteQueryResultDTO.setInviteDay(this.getInviteDay());
        return memberInviteQueryResultDTO;
    }

}
