package com.cloudyi.mini.domain;

import com.cloudyi.ledger.api.dto.LedgerDetailsListByDateDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsByCategory {

    /**
     * 会员id
     */
    private Long memberId;

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

    public LedgerDetailsListByDateDTO convertorLedgerDetailsListByDateDTO() {
        LedgerDetailsListByDateDTO dto = new LedgerDetailsListByDateDTO();
        dto.setMemberId(memberId);
        dto.setType(type);
        dto.setStartTime(startTime);
        dto.setEndTime(endTime);
        dto.setMemberCategoryId(memberCategoryId);
        return dto;
    }
}
