package com.cloudyi.ledger.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonthLedgerSumResultDTO {

    private BigDecimal expenditure;

    private BigDecimal income;

    private BigDecimal sum;
}
