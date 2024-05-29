package com.cloudyi.ledger.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "删除账单明细")
public class LedgerDetailDeleteDTO {

    private Long id;

    private Long memberId;

}
