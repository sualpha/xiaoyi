package com.cloudyi.ledger.domain;

import com.cloudyi.orm.domain.query.PageQuery;
import lombok.Data;

import java.util.Date;

@Data
public class LedgerDetailPageListCondition extends PageQuery {

    /**
     * 会员id
     */
    private Long memberId;

    /**
     * 1.收入 2.支出
     */
    private Integer type;

    /**
     * 交易时间 起
     */
    private Date startTime;

    /**
     * 交易时间 止
     */
    private Date endTime;

    private Long memberCategoryId;
}
