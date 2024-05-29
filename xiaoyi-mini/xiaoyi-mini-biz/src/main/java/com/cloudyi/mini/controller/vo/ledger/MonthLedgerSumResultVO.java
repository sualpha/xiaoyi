package com.cloudyi.mini.controller.vo.ledger;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonthLedgerSumResultVO {
    private BigDecimal expenditure;

    private BigDecimal income;

    private BigDecimal sum;

}
