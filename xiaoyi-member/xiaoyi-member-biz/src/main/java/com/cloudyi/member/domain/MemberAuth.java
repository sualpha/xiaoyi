package com.cloudyi.member.domain;

import com.cloudyi.member.domain.entity.MemberEntity;
import lombok.Data;

/**
 * @author subo
 * @date 2023/7/9 14:29
 **/
@Data
public class MemberAuth {

    /**
     * openId
     */
    private String openId;

    /**
     * nickName
     */
    private String nickName;

    /**
     * union_id
     */
    private String unionId;

    /**
     * avatarUrl
     */
    private String avatarUrl;


    /**
     * lastLoginIp
     */
    private String lastLoginIp;


    public MemberEntity updateMemberEntity(Long id){
        MemberEntity memberEntity=new MemberEntity();
        memberEntity.setId(id);
        memberEntity.setNickName(this.getNickName());
        memberEntity.setOpenId(this.getOpenId());
        memberEntity.setUnionId(this.getUnionId());
        memberEntity.setAvatarUrl(this.getAvatarUrl());
        return memberEntity;
    }

    public MemberEntity createMemberEntity(){
        MemberEntity memberEntity=new MemberEntity();
        memberEntity.setNickName(this.getNickName());
        memberEntity.setOpenId(this.getOpenId());
        memberEntity.setUnionId(this.getUnionId());
        memberEntity.setAvatarUrl(this.getAvatarUrl());
        return memberEntity;
    }

}
