package com.cloudyi.ledger.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
public enum LedgerOriginEnum {

    HAND (1,"手动"),
    VOICE (2,"语音"),

    REGULAR_TIME (3,"定时"),
    ;

    @Getter
    private final Integer code;

    @Getter
    private final String name;

}
