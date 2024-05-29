package com.cloudyi.gpt.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
public enum ApiFunctionCallObjectEnum {

    /**
     * 数据对象
     */
    TYPE ("type"),
    DESCRIPTION ("description"),
    ENUM ("enum")
    ;

    @Getter
    private final String name;

}
