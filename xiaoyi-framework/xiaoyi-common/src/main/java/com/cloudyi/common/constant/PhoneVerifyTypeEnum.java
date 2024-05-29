package com.cloudyi.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum PhoneVerifyTypeEnum {

    SYSTEM_USER_LOGIN(1,"后台用户登录"),
   ;

    @Getter
    private final Integer code;

    @Getter
    private final String name;
}
