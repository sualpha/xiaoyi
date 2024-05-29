package com.cloudyi.ledger.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
public enum LedgerTypeEnum {

    INCOME (1,"收入"),
    EXPENSES (2,"支出"),
    ;

    @Getter
    private final Integer code;

    @Getter
    private final String name;

}
