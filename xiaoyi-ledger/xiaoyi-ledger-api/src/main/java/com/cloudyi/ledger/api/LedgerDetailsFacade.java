package com.cloudyi.ledger.api;


import com.cloudyi.ledger.api.dto.*;
import com.cloudyi.orm.domain.query.PageResult;

import java.util.List;

public interface LedgerDetailsFacade {

    PageResult<LedgerDetailQueryResultDTO> queryLedgerDetails(LedgerDetailQueryDTO ledgerDetailQueryDTO);

    LedgerDetailQueryResultDTO queryDetail(LedgerDetailSingleQueryDTO dto);

    Long create(LedgerDetailCreateDTO ledgerDetailCreateDTO);

    Long update(LedgerDetailCreateDTO ledgerDetailCreateDTO);
    void delete(LedgerDetailDeleteDTO dto);

    MonthLedgerSumResultDTO queryMemberMonthLedgerSum(MonthLedgerSumDTO ledgerDetailQueryDTO);

    List<LedgerDetailQueryResultDTO> selectMemberLedgerListByDate(LedgerDetailsListByDateDTO ledgerDetailQueryDTO);

    PageResult<LedgerDetailQueryResultDTO> selectLedgerDetailPageList(LedgerDetailPageListDTO dto);

    MemberStatisticsResultDTO memberStatistics(Long memberId);

}
