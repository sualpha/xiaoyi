package com.cloudyi.ledger.api.dto;

import lombok.Data;

@Data
public class MemberStatisticsResultDTO {

    /**
     * 记账天数
     */
    private Integer ledgerDays;

    /**
     * 记账总数
     */
    private Integer ledgerCount;

}
