package com.cloudyi.mini.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class StatisticsByCategoryResult {

    private Integer totalCount;

    private BigDecimal totalAmt;

    private List<CategoryStatisticsResult> categoryList;
}
