package com.cloudyi.gpt.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
public enum GPTFunctionEnum {

    CREATE_LEDGER_DETAILS("createLedgerDetails","创建账本详情"),
    ;

    @Getter
    private final String name;

    @Getter
    private final String description;

}
