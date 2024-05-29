package com.cloudyi.mini.controller;


import cn.dev33.satoken.stp.StpUtil;
import com.cloudyi.member.api.dto.MemberDetailDTO;
import com.cloudyi.mini.controller.vo.statistics.CategoryStatisticsResultVO;
import com.cloudyi.mini.controller.vo.statistics.StatisticsByCategoryResultVO;
import com.cloudyi.mini.controller.vo.statistics.StatisticsByCategoryVO;
import com.cloudyi.mini.controller.vo.statistics.StatisticsByTransDateResultVO;
import com.cloudyi.mini.convertor.StatisticsConvertor;
import com.cloudyi.mini.domain.StatisticsByCategory;
import com.cloudyi.mini.domain.StatisticsByCategoryResult;
import com.cloudyi.mini.domain.StatisticsByTransDate;
import com.cloudyi.mini.domain.StatisticsByTransDateResult;
import com.cloudyi.mini.service.MiniMemberService;
import com.cloudyi.mini.service.StatisticsService;
import com.cloudyi.starter.web.annotation.ApiMiniRestController;
import com.cloudyi.starter.web.constant.FilePrefixEnum;
import com.cloudyi.starter.web.handler.response.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Tag(name = "账单统计管理")
@ApiMiniRestController(path = "/statistics")
public class StatisticsController {


    @Resource
    private StatisticsService statisticsService;

    @Operation(summary = "按照账单类型统计")
    @Parameter(in = ParameterIn.HEADER, name = "xiaoYiToken", required = true)
    @RequestMapping(value = "/statisticsByCategory", method = {RequestMethod.POST})
    public R<StatisticsByCategoryResultVO> statisticsByCategory(@RequestBody StatisticsByCategoryVO vo) {
        long id = StpUtil.getLoginIdAsLong();
        StatisticsByCategory statisticsByCategory = StatisticsByCategory.builder().memberId(id).type(vo.getType())
                .startTime(vo.getStartTime()).endTime(vo.getEndTime()).memberCategoryId(vo.getMemberCategoryId()).build();
        StatisticsByCategoryResult result = statisticsService.statisticsByCategory(statisticsByCategory);
        return R.data(StatisticsConvertor.INSTANCE.convertor(result));
    }

    @Operation(summary = "按照交易时间统计")
    @Parameter(in = ParameterIn.HEADER, name = "xiaoYiToken", required = true)
    @RequestMapping(value = "/statisticsByTransDate", method = {RequestMethod.POST})
    public R<List<StatisticsByTransDateResultVO>> statisticsByTransDate(@RequestBody StatisticsByCategoryVO vo) {
        long id = StpUtil.getLoginIdAsLong();
        StatisticsByTransDate statisticsByTransDate = StatisticsByTransDate.builder().memberId(id)
                .type(vo.getType()).startTime(vo.getStartTime()).endTime(vo.getEndTime()).build();
        List<StatisticsByTransDateResult> statisticsByTransDateResults = statisticsService.statisticsByTransDate(statisticsByTransDate);
        return R.data(StatisticsConvertor.INSTANCE.convertor(statisticsByTransDateResults));
    }
}
