package com.cloudyi.mini.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CategoryStatisticsResult {

    private Long categoryId;

    private Integer totalCount;

    private BigDecimal percent;

    private BigDecimal totalAmt;

    private String categoryUrl;

    private String categoryName;

}
