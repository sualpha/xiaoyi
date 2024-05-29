package com.cloudyi.ledger.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "查询账单明细")
public class LedgerDetailSingleQueryDTO {

    private Long id;

    private Long memberId;

}
