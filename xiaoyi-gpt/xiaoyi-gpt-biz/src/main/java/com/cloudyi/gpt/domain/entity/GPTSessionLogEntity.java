package com.cloudyi.gpt.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cloudyi.orm.domain.entity.BaseEntity;
import lombok.Data;

@Data
@TableName("gpt_session_log")
public class GPTSessionLogEntity extends BaseEntity {

    @TableId
    private Long id;

    private String session;

    private String request;

    private String response;

    private Integer hasError;

    private String errorMessage;
}
