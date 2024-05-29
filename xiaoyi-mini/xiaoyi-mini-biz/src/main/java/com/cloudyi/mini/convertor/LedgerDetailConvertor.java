package com.cloudyi.mini.convertor;


import com.cloudyi.ledger.api.dto.LedgerDetailPageListDTO;
import com.cloudyi.ledger.api.dto.LedgerDetailQueryDTO;
import com.cloudyi.ledger.api.dto.MonthLedgerSumDTO;
import com.cloudyi.ledger.api.dto.MonthLedgerSumResultDTO;
import com.cloudyi.mini.controller.vo.ledger.LedgerDetailQueryListVO;
import com.cloudyi.mini.controller.vo.ledger.LedgerDetailQueryVO;
import com.cloudyi.mini.controller.vo.ledger.MonthLedgerSumQueryVO;
import com.cloudyi.mini.controller.vo.ledger.MonthLedgerSumResultVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author subo
 * @date 2023/7/9 14:52
 **/
@Mapper
public interface LedgerDetailConvertor {

    LedgerDetailConvertor INSTANCE = Mappers.getMapper(LedgerDetailConvertor.class);

    LedgerDetailQueryDTO convertor(LedgerDetailQueryVO ledgerDetailQueryVO);

    MonthLedgerSumResultVO convertor(MonthLedgerSumResultDTO resultDTO);

    MonthLedgerSumDTO convertor(MonthLedgerSumQueryVO ledgerDetailQueryVO);

    LedgerDetailPageListDTO convertor(LedgerDetailQueryListVO ledgerDetailQueryVO);



}
