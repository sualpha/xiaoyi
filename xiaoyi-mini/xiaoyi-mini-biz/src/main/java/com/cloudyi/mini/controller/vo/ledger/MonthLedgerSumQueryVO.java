package com.cloudyi.mini.controller.vo.ledger;

import lombok.Data;

@Data
public class MonthLedgerSumQueryVO {
    private Long ledgerId;

    /**
     * 搜索内容
     */
    private String content;

    private String startTime;

    private String endTime;

}
