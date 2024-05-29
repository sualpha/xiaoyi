package com.cloudyi.gpt.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cloudyi.orm.domain.entity.BaseEntity;
import lombok.Data;

@Data
@TableName("member_gpt_tokens")
public class MemberGptTokensEntity extends BaseEntity {

    @TableId
    private Long id;

    private String uuid;

    private Long memberId;

    private Long totalTokens;

    private Long promptTokens;

    private Long completionTokens;

}
