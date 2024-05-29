package com.cloudyi.member.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
public enum MemberCategoryOriginEnum {

    SYSTEM_DEFAULT(1,"系统默认"),
    MEMBER_CUSTOM(2,"用户自定义"),
    ;

    @Getter
    private final Integer code;

    @Getter
    private final String name;

}
