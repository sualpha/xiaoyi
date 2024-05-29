package com.cloudyi.mini.controller.vo.ledger;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "查询账单明细结果")
public class LedgerDetailQueryGroupDayResultVO {

    @Schema(title = "查询账单明细id")
    private Long id;

    @Schema(title = "描述")
    private String description;

    @Schema(title = "金额")
    private String price;

    @Schema(title = "1.收入 2.支出")
    private String type;

    @Schema(title = "分类url")
    private String categoryUrl;

    @Schema(title = "分类名称")
    private String categoryName;

}
