package com.cloudyi.ledger.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "新增账单明细")
public class LedgerDetailCreateDTO {

    private Long id;

    private Long memberId;

    private Integer origin;

    private Integer type;

    private String transactionDate;

    private Long memberCategoryId;

    private String price;

    private String description;

}
