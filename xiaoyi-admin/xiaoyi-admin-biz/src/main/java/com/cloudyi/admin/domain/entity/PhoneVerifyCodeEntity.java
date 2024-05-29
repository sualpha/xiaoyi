package com.cloudyi.admin.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cloudyi.orm.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("phone_verify_code")
public class PhoneVerifyCodeEntity extends BaseEntity {

    @TableId
    private Long id;

    private String phone;

    private String verifyCode;

    private Integer bizType;
}
