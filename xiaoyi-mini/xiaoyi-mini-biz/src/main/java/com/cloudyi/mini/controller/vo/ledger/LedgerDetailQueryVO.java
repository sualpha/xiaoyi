package com.cloudyi.mini.controller.vo.ledger;

import com.cloudyi.orm.domain.query.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
@Schema(title = "查询账单明细")
public class LedgerDetailQueryVO extends PageQuery {

    private Long ledgerId;
    /**
     * 搜索内容
     */
    private String content;

    private String startTime;

    private String endTime;

}
