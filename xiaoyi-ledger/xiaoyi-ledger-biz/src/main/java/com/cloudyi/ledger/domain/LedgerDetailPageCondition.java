package com.cloudyi.ledger.domain;


import com.cloudyi.orm.domain.query.PageQuery;
import lombok.Data;

import java.util.Date;

@Data
public class LedgerDetailPageCondition extends PageQuery {

    private Long memberId;

    private Date startTime;

    private Date endTime;

    /**
     * 搜索内容
     */
    private String content;

}
