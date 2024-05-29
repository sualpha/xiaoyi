package com.cloudyi.ledger.api.dto;

import com.cloudyi.orm.domain.query.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@Schema(title = "查询账单明细")
public class LedgerDetailSummaryDTO extends PageQuery {

    private Long memberId;

    private Long ledgerId;

    private Date startDate;

    private Date endDate;

}
