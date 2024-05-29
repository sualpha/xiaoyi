package com.cloudyi.ledger.domain;

import com.cloudyi.ledger.domain.entity.LedgerDetailsEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Schema(title = "查询账单明细结果")
public class LedgerDetails {

    private Long id;

    private Long memberId;

    private Integer origin;

    private String ledgerName;

    private Date transactionDate;

    private BigDecimal price;

    private String description;

    private Long memberCategoryId;

    private Integer type;

    private String categoryUrl;

    private String categoryName;

    public LedgerDetailsEntity toEntity(){
        LedgerDetailsEntity ledgerDetailsEntity=new LedgerDetailsEntity();
        ledgerDetailsEntity.setId(id);
        ledgerDetailsEntity.setMemberId(memberId);
        ledgerDetailsEntity.setLedgerId(Long.valueOf(1));
        ledgerDetailsEntity.setOrigin(origin);
        ledgerDetailsEntity.setTransactionDate(transactionDate);
        ledgerDetailsEntity.setPrice(price);
        ledgerDetailsEntity.setDescription(description);
        ledgerDetailsEntity.setMemberCategoryId(memberCategoryId);
        ledgerDetailsEntity.setType(type);
        return ledgerDetailsEntity;
    }

    public static LedgerDetails fromEntity(LedgerDetailsEntity ledgerDetailsEntity){
        LedgerDetails ledgerDetails = new LedgerDetails();
        ledgerDetails.setId(ledgerDetailsEntity.getId());
        ledgerDetails.setMemberId(ledgerDetailsEntity.getMemberId());
        //ledgerDetails.setLedgerName();
        ledgerDetails.setTransactionDate(ledgerDetailsEntity.getTransactionDate());
        ledgerDetails.setPrice(ledgerDetailsEntity.getPrice());
        ledgerDetails.setDescription(ledgerDetailsEntity.getDescription());
        ledgerDetails.setMemberCategoryId(ledgerDetailsEntity.getMemberCategoryId());
        ledgerDetails.setType(ledgerDetailsEntity.getType());
        return ledgerDetails;
    }

}
