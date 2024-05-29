package com.cloudyi.mini.service.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.cloudyi.common.constant.SymbolicConstants;
import com.cloudyi.member.api.MemberFacade;
import com.cloudyi.member.api.dto.MemberDetailDTO;
import com.cloudyi.mini.controller.vo.ledger.LedgerYearMonthVO;
import com.cloudyi.mini.service.MiniMemberService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MiniMemberServiceImpl implements MiniMemberService {

    @Resource
    private MemberFacade memberFacade;

    @Override
    public List<LedgerYearMonthVO> queryLedgerYearMonth(Long id) {
        MemberDetailDTO memberDetailDTO = memberFacade.queryMemberDetail(id);
        Date createTime = memberDetailDTO.getCreateTime();
        return DateUtil.rangeToList(createTime, new Date(), DateField.MONTH).stream().map(a -> {
            String format = DateUtil.format(a, DatePattern.NORM_MONTH_PATTERN);
            String[] split = format.split(SymbolicConstants.ZHX);
            return new ImmutablePair<>(split[0], Integer.parseInt(split[1]));
        }).collect(Collectors.groupingBy(ImmutablePair::getLeft)).entrySet().stream().map(a -> {
            List<Integer> monthList = a.getValue().stream().map(ImmutablePair::getRight).collect(Collectors.toList());
            return LedgerYearMonthVO.builder().year(a.getKey()).monthList(monthList).build();
        }).toList();
    }
}
