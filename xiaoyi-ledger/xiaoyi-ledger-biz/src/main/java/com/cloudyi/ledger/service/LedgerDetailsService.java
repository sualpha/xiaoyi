package com.cloudyi.ledger.service;

import com.cloudyi.ledger.domain.*;
import com.cloudyi.orm.domain.query.PageResult;

import java.util.List;

/**
 * @author subo
 * @date 2023/7/19 23:12
 **/
public interface LedgerDetailsService {

    PageResult<LedgerDetails> queryPage(LedgerDetailPageCondition ledgerDetailPageCondition);
    LedgerDetails queryDetail(LedgerDetailCondition condition);
    Long create(LedgerDetails ledgerDetails);

    Long update(LedgerDetails ledgerDetails);
    void delete(LedgerDetails ledgerDetails);

    MonthLedgerSumResultDO queryMemberMonthLedgerSum(MonthLedgerSumDO monthLedgerSumDO);

    List<LedgerDetails> selectMemberLedgerListByDate(LedgerDetailsListByDateCondition condition);
    PageResult<LedgerDetails> selectLedgerDetailPageList(LedgerDetailPageListCondition condition);

    MemberStatisticsResult memberStatistics(Long memberId);
}
