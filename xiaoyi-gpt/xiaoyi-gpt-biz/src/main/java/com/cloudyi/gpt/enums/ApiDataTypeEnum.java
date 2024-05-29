package com.cloudyi.gpt.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
public enum ApiDataTypeEnum {

    /**
     * 数据类型
     */
    NUMBER ("number"),
    STRING ("string"),
    OBJECT ("object"),
    INTEGER ("integer"),
    ARRAY ("array"),
    BOOLEAN ("boolean"),
    MAP ("map"),
    DATE ("date"),
    BIGDECIMAL ("bigDecimal"),
    ;

    @Getter
    private final String name;

}
