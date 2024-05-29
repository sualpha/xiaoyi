package com.cloudyi.mini.controller.vo.ledger;

import com.cloudyi.orm.domain.query.PageQuery;
import lombok.Data;

@Data
public class LedgerDetailQueryListVO extends PageQuery {
    /**
     * 1.收入 2.支出
     */
    private Integer type;

    /**
     * yyyy-MM-dd
     */
    private String startTime;

    /**
     * yyyy-MM-dd
     */
    private String endTime;

    private Long memberCategoryId;
}
