package com.cloudyi.ledger.mapper;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloudyi.ledger.domain.LedgerDetailCondition;
import com.cloudyi.ledger.domain.LedgerDetailPageCondition;
import com.cloudyi.ledger.domain.LedgerDetailPageListCondition;
import com.cloudyi.ledger.domain.LedgerDetailsListByDateCondition;
import com.cloudyi.ledger.domain.entity.LedgerDetailsEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;
import java.util.Objects;


@Mapper
public interface LedgerDetailsMapper extends BaseMapper<LedgerDetailsEntity> {

    default List<LedgerDetailsEntity> selectMonthLedger(Long memberId, Date start, Date end) {
        return selectList(new LambdaQueryWrapper<LedgerDetailsEntity>().eq(LedgerDetailsEntity::getMemberId,
                memberId).between(LedgerDetailsEntity::getTransactionDate, start, end));
    }

    default LedgerDetailsEntity selectByIdAndMemberId(LedgerDetailCondition condition) {
        List<LedgerDetailsEntity> ledgerDetailsEntities = selectList(new LambdaQueryWrapper<LedgerDetailsEntity>().eq(LedgerDetailsEntity::getMemberId,
                condition.getMemberId()).eq(LedgerDetailsEntity::getId,
                condition.getId()));
        if (CollUtil.isNotEmpty(ledgerDetailsEntities)) {
            return ledgerDetailsEntities.get(0);
        }
        return null;
    }

    default Page<LedgerDetailsEntity> selectPage(LedgerDetailPageCondition ledgerDetailPageCondition) {
        return selectPage(new Page<>(ledgerDetailPageCondition.getPageNumber(), ledgerDetailPageCondition.getPageSize()), new LambdaQueryWrapper<LedgerDetailsEntity>()
                .between(LedgerDetailsEntity::getTransactionDate, ledgerDetailPageCondition.getStartTime(), ledgerDetailPageCondition.getEndTime())
                .eq(LedgerDetailsEntity::getMemberId, ledgerDetailPageCondition.getMemberId())
                .and(StringUtils.isNoneBlank(ledgerDetailPageCondition.getContent()),
                        wrapper->
                                wrapper.like(StringUtils.isNoneBlank(ledgerDetailPageCondition.getContent()),LedgerDetailsEntity::getDescription,ledgerDetailPageCondition.getContent())
                                        .or()
                                        .eq(StringUtils.isNoneBlank(ledgerDetailPageCondition.getContent()),LedgerDetailsEntity::getPrice,ledgerDetailPageCondition.getContent())
                )
                .orderByDesc(LedgerDetailsEntity::getTransactionDate));
    }

    default List<LedgerDetailsEntity> selectList(LedgerDetailsListByDateCondition condition) {
        LambdaQueryWrapper<LedgerDetailsEntity> lambdaQueryWrapper = new LambdaQueryWrapper<LedgerDetailsEntity>()
                .eq(LedgerDetailsEntity::getMemberId, condition.getMemberId())
                .eq(Objects.nonNull(condition.getMemberCategoryId()), LedgerDetailsEntity::getMemberCategoryId, condition.getMemberCategoryId())
                .eq(Objects.nonNull(condition.getType()), LedgerDetailsEntity::getType, condition.getType())
                .between(condition.getStartTime() != null && condition.getEndTime() != null, LedgerDetailsEntity::getTransactionDate, condition.getStartTime(), condition.getEndTime());
        condition.completeOrderBySql(lambdaQueryWrapper);
        return selectList(lambdaQueryWrapper);
    }

    default Page<LedgerDetailsEntity> selectList(LedgerDetailPageListCondition condition) {
        LambdaQueryWrapper<LedgerDetailsEntity> lambdaQueryWrapper = new LambdaQueryWrapper<LedgerDetailsEntity>()
                .eq(LedgerDetailsEntity::getMemberId, condition.getMemberId())
                .eq(Objects.nonNull(condition.getMemberCategoryId()), LedgerDetailsEntity::getMemberCategoryId, condition.getMemberCategoryId())
                .eq(Objects.nonNull(condition.getType()), LedgerDetailsEntity::getType, condition.getType())
                .between(condition.getStartTime() != null && condition.getEndTime() != null,
                        LedgerDetailsEntity::getTransactionDate, condition.getStartTime(), condition.getEndTime());
        condition.completeOrderBySql(lambdaQueryWrapper);
        return selectPage(new Page<>(condition.getPageNumber(), condition.getPageSize()), lambdaQueryWrapper);
    }

    default Integer statisticsMemberLedgerDays(Long memberId){
        LambdaQueryWrapper<LedgerDetailsEntity> wrapper = new LambdaQueryWrapper<LedgerDetailsEntity>().select(LedgerDetailsEntity::getTransactionDate)
                .eq(LedgerDetailsEntity::getMemberId, memberId).groupBy(LedgerDetailsEntity::getTransactionDate);
        return selectList(wrapper).size();
    }

    default Integer statisticsMemberLedgerCounts(Long memberId){
        Long count = selectCount(new LambdaQueryWrapper<LedgerDetailsEntity>()
                .eq(LedgerDetailsEntity::getMemberId, memberId));
        return count.intValue();
    }
}
