package com.cloudyi.mini.controller.vo.ledger;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(title = "查询账单明细结果")
public class LedgerDetailQueryGroupResultVO {

    @Schema(title = "日期")
    private String date;

    @Schema(title = "周几")
    private String week;

    @Schema(title = "总收入")
    private String totalIncome;

    @Schema(title = "总消费")
    private String totalExpenses;

    @Schema(title = "日详情")
    private List<LedgerDetailQueryGroupDayResultVO> dayResult;

}
