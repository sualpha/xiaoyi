package com.cloudyi.ledger.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Schema(title = "查询账单明细结果")
public class LedgerDetailQueryResultDTO {

    @Schema(title = "查询账单明细id")
    private Long id;

    @Schema(title = "会员id")
    private Long memberId;

    @Schema(title = "消费时间")
    private String transactionDate;

    @Schema(title = "描述")
    private String description;

    @Schema(title = "金额")
    private BigDecimal price;

    @Schema(title = "1.收入 2.支出")
    private Integer type;

    @Schema(title = "分类url")
    private String categoryUrl;

    @Schema(title = "会员分类id")
    private Long memberCategoryId;

    @Schema(title = "分类名称")
    private String categoryName;

}
