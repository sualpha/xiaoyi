package com.cloudyi.member.domain;


import com.cloudyi.member.api.dto.MemberRedeemCodeCreateDTO;
import com.cloudyi.member.api.dto.MemberRedeemCodeUseDTO;
import com.cloudyi.member.domain.entity.MemberRedeemCodeEntity;

import com.cloudyi.member.enums.MemberRedeemCodeEnums;
import lombok.Data;

@Data
public class MemberRedeemCode {

    /**
     * 会员id
     */
    private Long memberId;

    private String redeemCode;

    /**
     * 数量
     */
    private Integer num;

    /**
     * 手机号
     */
    private Integer unit;

    public static MemberRedeemCode toMemberRedeemCode(MemberRedeemCodeCreateDTO memberRedeemCodeCreateDTO){
        MemberRedeemCode memberRedeemCode = new MemberRedeemCode();
        memberRedeemCode.setMemberId(memberRedeemCodeCreateDTO.getMemberId());
        memberRedeemCode.setNum(memberRedeemCodeCreateDTO.getNum());
        memberRedeemCode.setUnit(memberRedeemCodeCreateDTO.getUnit());
        return memberRedeemCode;
    }

    public MemberRedeemCodeEntity toEntity(String redeemCode){
        MemberRedeemCodeEntity memberRedeemCodeEntity = new MemberRedeemCodeEntity();
        memberRedeemCodeEntity.setMemberId(getMemberId());
        memberRedeemCodeEntity.setRedeemCode(redeemCode);
        memberRedeemCodeEntity.setNum(getNum());
        memberRedeemCodeEntity.setUnit(getUnit());
        memberRedeemCodeEntity.setStatus(MemberRedeemCodeEnums.MemberRedeemCodeStatusEnum.NOT_USED.getCode());
        return memberRedeemCodeEntity;
    }

    public static MemberRedeemCode toMemberRedeemCode(MemberRedeemCodeUseDTO memberRedeemCodeUseDTO){
        MemberRedeemCode memberRedeemCode = new MemberRedeemCode();
        memberRedeemCode.setMemberId(memberRedeemCodeUseDTO.getMemberId());
        memberRedeemCode.setRedeemCode(memberRedeemCodeUseDTO.getRedeemCode());
        return memberRedeemCode;
    }

}
