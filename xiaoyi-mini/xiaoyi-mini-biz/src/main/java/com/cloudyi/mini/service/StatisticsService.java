package com.cloudyi.mini.service;

import com.cloudyi.mini.domain.StatisticsByCategory;
import com.cloudyi.mini.domain.StatisticsByCategoryResult;
import com.cloudyi.mini.domain.StatisticsByTransDate;
import com.cloudyi.mini.domain.StatisticsByTransDateResult;

import java.util.List;

public interface StatisticsService {

    StatisticsByCategoryResult statisticsByCategory(StatisticsByCategory domain);

    List<StatisticsByTransDateResult> statisticsByTransDate(StatisticsByTransDate domain);
}
