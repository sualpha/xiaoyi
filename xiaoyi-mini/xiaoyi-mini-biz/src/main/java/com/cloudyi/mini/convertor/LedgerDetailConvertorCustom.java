package com.cloudyi.mini.convertor;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.cloudyi.orm.domain.query.PageResult;
import com.cloudyi.ledger.api.dto.LedgerDetailQueryResultDTO;
import com.cloudyi.ledger.enums.LedgerTypeEnum;
import com.cloudyi.mini.controller.vo.ledger.LedgerDetailQueryGroupDayResultVO;
import com.cloudyi.mini.controller.vo.ledger.LedgerDetailQueryGroupResultVO;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author subo
 * @date 2023/7/19 22:27
 **/
public class LedgerDetailConvertorCustom {

    private final static String[] weekStr={"周日","周一","周二","周三","周四","周五","周六"};

     public static PageResult<LedgerDetailQueryGroupResultVO> convertorLedgerDetailQueryResultVO(PageResult<LedgerDetailQueryResultDTO> result){
        if(CollUtil.isNotEmpty(result.getList())){
            List<LedgerDetailQueryGroupResultVO> list=new ArrayList<>();
            Comparator<Date> reverseDateComparator = Comparator.reverseOrder(); // 创建倒序比较器
            TreeMap<Date, List<LedgerDetailQueryResultDTO>> sortedTreeMap = new TreeMap<>(reverseDateComparator);
            TreeMap<Date, List<LedgerDetailQueryResultDTO>> map = result.getList().stream().collect(Collectors.groupingBy(a->DateUtil.parseDate(a.getTransactionDate()),TreeMap::new,Collectors.toList()));
            sortedTreeMap.putAll(map);
            for (Map.Entry<Date, List<LedgerDetailQueryResultDTO>> dateListEntry : sortedTreeMap.entrySet()) {
                LedgerDetailQueryGroupResultVO ledgerDetailQueryGroupResultVO=new LedgerDetailQueryGroupResultVO();
                ledgerDetailQueryGroupResultVO.setDate(DateUtil.formatDate(dateListEntry.getKey()));
                int index = DateUtil.dayOfWeek(dateListEntry.getKey());
                ledgerDetailQueryGroupResultVO.setWeek(weekStr[index-1]);
                BigDecimal totalIncome = dateListEntry.getValue().stream().filter(a -> LedgerTypeEnum.INCOME.getCode().equals(a.getType())).map(LedgerDetailQueryResultDTO::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal totalExpenses = dateListEntry.getValue().stream().filter(a -> LedgerTypeEnum.EXPENSES.getCode().equals(a.getType())).map(LedgerDetailQueryResultDTO::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
                ledgerDetailQueryGroupResultVO.setTotalExpenses(String.valueOf(totalExpenses));
                ledgerDetailQueryGroupResultVO.setTotalIncome(String.valueOf(totalIncome));
                List<LedgerDetailQueryGroupDayResultVO> dayResult=new ArrayList<>();
                for (LedgerDetailQueryResultDTO ledgerDetailQueryResultDTO : dateListEntry.getValue()) {
                    LedgerDetailQueryGroupDayResultVO ledgerDetailQueryGroupDayResultVO = convertorLedgerDetailQueryGroupDayResultVO(ledgerDetailQueryResultDTO);
                    dayResult.add(ledgerDetailQueryGroupDayResultVO);
                }
                ledgerDetailQueryGroupResultVO.setDayResult(dayResult);
                list.add(ledgerDetailQueryGroupResultVO);
            }
            return new PageResult<>(list, result.getTotal());
        }
        return PageResult.empty();
    }

    public static LedgerDetailQueryGroupDayResultVO convertorLedgerDetailQueryGroupDayResultVO(LedgerDetailQueryResultDTO result) {
        LedgerDetailQueryGroupDayResultVO ledgerDetailQueryGroupDayResultVO = new LedgerDetailQueryGroupDayResultVO();
        ledgerDetailQueryGroupDayResultVO.setId(result.getId());
        ledgerDetailQueryGroupDayResultVO.setDescription(result.getDescription());
        ledgerDetailQueryGroupDayResultVO.setPrice(String.valueOf(result.getPrice()));
        ledgerDetailQueryGroupDayResultVO.setType(String.valueOf(result.getType()));
        ledgerDetailQueryGroupDayResultVO.setCategoryName(result.getCategoryName());
        ledgerDetailQueryGroupDayResultVO.setCategoryUrl(result.getCategoryUrl());
        return ledgerDetailQueryGroupDayResultVO;
    }
}
