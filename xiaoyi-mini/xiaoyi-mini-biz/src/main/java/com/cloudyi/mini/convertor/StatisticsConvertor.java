package com.cloudyi.mini.convertor;

import com.cloudyi.mini.controller.vo.statistics.StatisticsByCategoryResultVO;
import com.cloudyi.mini.controller.vo.statistics.StatisticsByTransDateResultVO;
import com.cloudyi.mini.domain.StatisticsByCategoryResult;
import com.cloudyi.mini.domain.StatisticsByTransDateResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface StatisticsConvertor {
    StatisticsConvertor INSTANCE = Mappers.getMapper(StatisticsConvertor.class);

    StatisticsByCategoryResultVO convertor(StatisticsByCategoryResult ledgerDetailQueryVO);
    List<StatisticsByTransDateResultVO> convertor(List<StatisticsByTransDateResult> list);
}
