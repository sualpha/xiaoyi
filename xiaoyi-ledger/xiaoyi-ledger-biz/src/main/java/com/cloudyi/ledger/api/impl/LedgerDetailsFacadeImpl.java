package com.cloudyi.ledger.api.impl;

import com.cloudyi.ledger.api.dto.*;
import com.cloudyi.ledger.domain.*;
import com.cloudyi.orm.domain.query.PageResult;
import com.cloudyi.ledger.api.LedgerDetailsFacade;
import com.cloudyi.ledger.convertor.LedgerDetailsConvertor;
import com.cloudyi.ledger.service.LedgerDetailsService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LedgerDetailsFacadeImpl implements LedgerDetailsFacade {

    @Resource
    private LedgerDetailsService ledgerDetailsService;

    @Override
    public PageResult<LedgerDetailQueryResultDTO> queryLedgerDetails(LedgerDetailQueryDTO ledgerDetailQueryDTO) {
        LedgerDetailPageCondition condition = LedgerDetailsConvertor.INSTANCE.convertor(ledgerDetailQueryDTO);
        PageResult<LedgerDetails> ledgerDetailsPageResult = ledgerDetailsService.queryPage(condition);
        return LedgerDetailsConvertor.INSTANCE.convertor(ledgerDetailsPageResult);
    }

    @Override
    public LedgerDetailQueryResultDTO queryDetail(LedgerDetailSingleQueryDTO dto) {
        LedgerDetailCondition condition = LedgerDetailsConvertor.INSTANCE.convertor(dto);
        LedgerDetails ledgerDetails = ledgerDetailsService.queryDetail(condition);
        return LedgerDetailsConvertor.INSTANCE.convertor(ledgerDetails);
    }

    @Override
    public Long create(LedgerDetailCreateDTO ledgerDetailCreateDTO) {
        LedgerDetails ledgerDetails = LedgerDetailsConvertor.INSTANCE.convertor(ledgerDetailCreateDTO);
        return ledgerDetailsService.create(ledgerDetails);
    }

    @Override
    public Long update(LedgerDetailCreateDTO ledgerDetailCreateDTO) {
        LedgerDetails ledgerDetails = LedgerDetailsConvertor.INSTANCE.convertor(ledgerDetailCreateDTO);
        return ledgerDetailsService.update(ledgerDetails);
    }

    @Override
    public void delete(LedgerDetailDeleteDTO dto) {
        LedgerDetails ledgerDetails = LedgerDetailsConvertor.INSTANCE.convertor(dto);
        ledgerDetailsService.delete(ledgerDetails);
    }

    @Override
    public MonthLedgerSumResultDTO queryMemberMonthLedgerSum(MonthLedgerSumDTO ledgerDetailQueryDTO) {
        MonthLedgerSumDO monthLedgerSumDO = LedgerDetailsConvertor.INSTANCE.convertor(ledgerDetailQueryDTO);
        MonthLedgerSumResultDO monthLedgerSumResultDO = ledgerDetailsService.queryMemberMonthLedgerSum(monthLedgerSumDO);
        return LedgerDetailsConvertor.INSTANCE.convertor(monthLedgerSumResultDO);
    }

    @Override
    public List<LedgerDetailQueryResultDTO> selectMemberLedgerListByDate(LedgerDetailsListByDateDTO ledgerDetailQueryDTO) {
        LedgerDetailsListByDateCondition condition = LedgerDetailsConvertor.INSTANCE.convertor(ledgerDetailQueryDTO);
        List<LedgerDetails> ledgerDetails = ledgerDetailsService.selectMemberLedgerListByDate(condition);
        return LedgerDetailsConvertor.INSTANCE.convertor(ledgerDetails);
    }

    @Override
    public PageResult<LedgerDetailQueryResultDTO> selectLedgerDetailPageList(LedgerDetailPageListDTO dto) {
        LedgerDetailPageListCondition condition = LedgerDetailsConvertor.INSTANCE.convertor(dto);
        PageResult<LedgerDetails> ledgerDetailsPageResult = ledgerDetailsService.selectLedgerDetailPageList(condition);
        return LedgerDetailsConvertor.INSTANCE.convertor(ledgerDetailsPageResult);
    }

    @Override
    public MemberStatisticsResultDTO memberStatistics(Long memberId) {
        MemberStatisticsResult memberStatisticsResult = ledgerDetailsService.memberStatistics(memberId);
        return LedgerDetailsConvertor.INSTANCE.convertor(memberStatisticsResult);
    }
}
