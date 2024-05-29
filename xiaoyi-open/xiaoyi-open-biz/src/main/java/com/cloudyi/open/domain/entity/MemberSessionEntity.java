package com.cloudyi.open.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cloudyi.orm.domain.entity.BaseEntity;
import lombok.Data;

@Data
@TableName("member_session")
public class MemberSessionEntity extends BaseEntity {

    @TableId
    private Long id;

    private String session;

    private Long memberId;

    private String request;

    private String response;

}
