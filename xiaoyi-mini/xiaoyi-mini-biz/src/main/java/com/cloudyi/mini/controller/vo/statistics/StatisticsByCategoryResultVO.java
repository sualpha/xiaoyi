package com.cloudyi.mini.controller.vo.statistics;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class StatisticsByCategoryResultVO {

    private Integer totalCount;

    private BigDecimal totalAmt;

    private List<CategoryStatisticsResultVO> categoryList;
}
