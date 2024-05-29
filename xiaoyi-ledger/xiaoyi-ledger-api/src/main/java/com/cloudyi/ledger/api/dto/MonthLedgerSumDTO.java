package com.cloudyi.ledger.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonthLedgerSumDTO {

    private Long memberId;

    private String startTime;

    private String endTime;

}
