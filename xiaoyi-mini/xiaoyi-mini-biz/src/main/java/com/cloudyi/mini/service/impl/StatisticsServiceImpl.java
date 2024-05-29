package com.cloudyi.mini.service.impl;

import com.cloudyi.ledger.api.LedgerDetailsFacade;
import com.cloudyi.ledger.api.dto.LedgerDetailQueryResultDTO;
import com.cloudyi.ledger.api.dto.LedgerDetailsListByDateDTO;
import com.cloudyi.mini.domain.*;
import com.cloudyi.mini.service.StatisticsService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Resource
    private LedgerDetailsFacade ledgerDetailsFacade;

    @Override
    public StatisticsByCategoryResult statisticsByCategory(StatisticsByCategory domain) {
        List<LedgerDetailQueryResultDTO> list = ledgerDetailsFacade.selectMemberLedgerListByDate(domain
                .convertorLedgerDetailsListByDateDTO());
        StatisticsByCategoryResult result = new StatisticsByCategoryResult();
        if (CollectionUtils.isEmpty(list)) {
            result.setTotalCount(0);
            result.setTotalAmt(BigDecimal.ZERO);
            return result;
        }
        result.setTotalCount(list.size());
        BigDecimal totalAmt = list.stream().map(LedgerDetailQueryResultDTO::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        result.setTotalAmt(totalAmt);
        Map<Long, List<LedgerDetailQueryResultDTO>> collectByCategoryMap = list.stream().filter(a->Objects.nonNull(a.getMemberCategoryId()))
                .collect(Collectors.groupingBy(LedgerDetailQueryResultDTO::getMemberCategoryId, Collectors.toList()));
        List<CategoryStatisticsResult> categoryList = collectByCategoryMap.entrySet().stream()
                .map(a -> this.createCategoryStatisticsResult(a.getKey(), a.getValue(),totalAmt))
                .filter(Objects::nonNull).toList();
        result.setCategoryList(categoryList);
        return result;
    }

    @Override
    public List<StatisticsByTransDateResult> statisticsByTransDate(StatisticsByTransDate domain) {
        List<LedgerDetailQueryResultDTO> list = ledgerDetailsFacade.selectMemberLedgerListByDate(domain
                .convertorLedgerDetailsListByDateDTO());
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        return list.stream().collect(Collectors.groupingBy(LedgerDetailQueryResultDTO::getTransactionDate,
                        Collectors.mapping(LedgerDetailQueryResultDTO::getPrice, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))))
                .entrySet().stream().map(a -> new StatisticsByTransDateResult(a.getKey(), a.getValue().toString()))
                .sorted(Comparator.comparing(StatisticsByTransDateResult::getTransDate)).toList();
//        return collectMap.entrySet().stream().map(a -> new StatisticsByTransDateResult(a.getKey(), a.getValue().toString()))
//                .sorted(Comparator.comparing(StatisticsByTransDateResult::getTransDate)).toList();
    }

    private CategoryStatisticsResult createCategoryStatisticsResult(Long categoryId, List<LedgerDetailQueryResultDTO> list,BigDecimal totalPrice) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        BigDecimal totalAmt = list.stream().map(LedgerDetailQueryResultDTO::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        CategoryStatisticsResult statisticsResult = new CategoryStatisticsResult();
        statisticsResult.setCategoryId(categoryId);
        statisticsResult.setCategoryUrl(list.get(0).getCategoryUrl());
        statisticsResult.setCategoryName(list.get(0).getCategoryName());
        statisticsResult.setTotalCount(list.size());
        statisticsResult.setTotalAmt(totalAmt);
        BigDecimal percent = totalAmt.divide(totalPrice, 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
        statisticsResult.setPercent(percent);
        return statisticsResult;

    }
}
