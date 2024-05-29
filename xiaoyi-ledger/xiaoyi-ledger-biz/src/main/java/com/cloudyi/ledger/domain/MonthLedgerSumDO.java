package com.cloudyi.ledger.domain;

import lombok.Data;

import java.util.Date;

@Data
public class MonthLedgerSumDO {

    private Long memberId;

    private Date startTime;

    private Date endTime;

}
