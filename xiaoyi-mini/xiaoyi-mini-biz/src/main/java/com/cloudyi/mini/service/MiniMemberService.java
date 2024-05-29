package com.cloudyi.mini.service;

import com.cloudyi.mini.controller.vo.ledger.LedgerYearMonthVO;

import java.util.List;

public interface MiniMemberService {

    List<LedgerYearMonthVO> queryLedgerYearMonth(Long id);
}
