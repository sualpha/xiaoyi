package com.cloudyi.ledger.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * @author subo
 * @date 2023/9/17 15:39
 **/
@Getter
@Setter
public class MemberStatisticsResult {

    /**
     * 记账天数
     */
    private Integer ledgerDays;

    /**
     * 记账总数
     */
    private Integer ledgerCount;
}
