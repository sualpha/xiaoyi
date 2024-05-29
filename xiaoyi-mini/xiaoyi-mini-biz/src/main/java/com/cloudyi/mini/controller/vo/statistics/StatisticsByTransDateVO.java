package com.cloudyi.mini.controller.vo.statistics;

import lombok.Data;

@Data
public class StatisticsByTransDateVO {
    /**
     * 1.收入 2.支出
     */
    private Integer type;

    /**
     * yyyy-MM-dd
     */
    private String startTime;

    /**
     * yyyy-MM-dd
     */
    private String endTime;
}
