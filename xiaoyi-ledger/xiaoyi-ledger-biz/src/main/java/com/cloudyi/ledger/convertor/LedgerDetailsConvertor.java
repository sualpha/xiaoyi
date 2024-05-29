package com.cloudyi.ledger.convertor;

import com.cloudyi.ledger.api.dto.*;
import com.cloudyi.ledger.domain.*;
import com.cloudyi.orm.domain.query.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface LedgerDetailsConvertor {

    LedgerDetailsConvertor INSTANCE = Mappers.getMapper(LedgerDetailsConvertor.class);

    @Mapping(target = "startTime", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "endTime", dateFormat = "yyyy-MM-dd")
    LedgerDetailPageCondition convertor(LedgerDetailQueryDTO ledgerDetailQueryDTO);
    LedgerDetailCondition convertor(LedgerDetailSingleQueryDTO ledgerDetailQueryDTO);

    PageResult<LedgerDetailQueryResultDTO> convertor(PageResult<LedgerDetails> pageResult);

    @Mapping(target = "transactionDate", dateFormat = "yyyy-MM-dd")
    LedgerDetailQueryResultDTO convertor(LedgerDetails ledgerDetails);

    @Mapping(target = "startTime", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "endTime", dateFormat = "yyyy-MM-dd")
    MonthLedgerSumDO convertor(MonthLedgerSumDTO dto);

    MonthLedgerSumResultDTO convertor(MonthLedgerSumResultDO dto);
    LedgerDetails convertor(LedgerDetailDeleteDTO dto);

    @Mapping(target = "transactionDate", dateFormat = "yyyy-MM-dd")
    LedgerDetails convertor(LedgerDetailCreateDTO dto);

    @Mapping(target = "startTime", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "endTime", dateFormat = "yyyy-MM-dd")
    LedgerDetailsListByDateCondition convertor(LedgerDetailsListByDateDTO ledgerDetailQueryDTO);


    @Mapping(target = "transactionDate", dateFormat = "yyyy-MM-dd")
    List<LedgerDetailQueryResultDTO> convertor(List<LedgerDetails> pageResult);

    @Mapping(target = "startTime", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "endTime", dateFormat = "yyyy-MM-dd")
    LedgerDetailPageListCondition convertor(LedgerDetailPageListDTO ledgerDetailQueryDTO);


    MemberStatisticsResultDTO convertor(MemberStatisticsResult memberStatisticsResult);

}
