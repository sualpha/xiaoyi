package com.cloudyi.ledger.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cloudyi.orm.domain.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("ledger_details")
public class LedgerDetailsEntity extends BaseEntity {

    /**
     * 主键
     */
    @TableId
    private Long id;

    private Long memberId;

    private Long ledgerId;

    private Integer origin;

    private Date transactionDate;

    private BigDecimal price;

    private String description;

    private Long memberCategoryId;

    private Integer type;

}
