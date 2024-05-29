package com.cloudyi.member.domain;

import com.cloudyi.member.domain.entity.MemberEntity;
import lombok.Data;

import java.util.Date;

@Data
public class MemberDetailDO {
    private Long id;

    private String nickName;

    private String openId;

    private String avatarUrl;

    private Date createTime;

    private Date vipExpiresTime;

    public static MemberDetailDO fromEntity(MemberEntity entity) {
        if (entity == null) {
            return null;
        }
        MemberDetailDO memberDetailDO = new MemberDetailDO();
        memberDetailDO.setId(entity.getId());
        memberDetailDO.setNickName(entity.getNickName());
        memberDetailDO.setOpenId(entity.getOpenId());
        memberDetailDO.setAvatarUrl(entity.getAvatarUrl());
        memberDetailDO.setCreateTime(entity.getCreateTime());
        memberDetailDO.setVipExpiresTime(entity.getVipExpiresTime());
        return memberDetailDO;
    }

    public  MemberEntity toUpdateNickNameAndAvatarUrlEntity() {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(this.getId());
        memberEntity.setNickName(this.getNickName());
        memberEntity.setAvatarUrl(this.getAvatarUrl());
        return memberEntity;
    }
}
