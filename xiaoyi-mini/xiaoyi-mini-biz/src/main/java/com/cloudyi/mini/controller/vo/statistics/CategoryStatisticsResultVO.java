package com.cloudyi.mini.controller.vo.statistics;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CategoryStatisticsResultVO {
    private Long categoryId;

    private Integer totalCount;

    private BigDecimal percent;

    private BigDecimal totalAmt;

    private String categoryUrl;

    private String categoryName;

}
