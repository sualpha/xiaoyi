package com.cloudyi.mini.controller.vo.ledger;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerYearMonthVO {
    private String year;

    private List<Integer> monthList;
}
