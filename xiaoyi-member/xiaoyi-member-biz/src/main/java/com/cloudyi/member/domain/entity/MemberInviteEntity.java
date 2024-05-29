package com.cloudyi.member.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cloudyi.orm.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("member_invite")
public class MemberInviteEntity extends BaseEntity {

    @TableId
    private Long id;

    private Long memberId;

    private Long memberInviteId;
}
