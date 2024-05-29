package com.cloudyi.ledger.api.dto;

import com.cloudyi.orm.domain.query.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "查询账单明细")
public class LedgerDetailPageListDTO extends PageQuery {
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
    private String startTime;

    /**
     * 交易时间 止
     */
    private String endTime;

    /**
     * 排序字段
     */
    private String orderBy;

    /**
     * 是否正序
     */
    private Boolean asc;

    private Long memberCategoryId;
}
