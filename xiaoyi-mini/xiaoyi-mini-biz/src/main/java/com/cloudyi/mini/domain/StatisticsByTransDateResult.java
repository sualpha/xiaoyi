package com.cloudyi.mini.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsByTransDateResult {

    private String transDate;

    private String totalAmt;
}
