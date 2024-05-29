package com.cloudyi.member.mapper;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudyi.member.domain.MemberCategoryCondition;
import com.cloudyi.member.domain.entity.MemberCategoryEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Objects;

/**
 * @author subo
 * @date 2023/7/9 14:25
 **/
@Mapper
public interface MemberCategoryMapper extends BaseMapper<MemberCategoryEntity> {

    default List<MemberCategoryEntity> queryMemberCategory(MemberCategoryCondition memberCategoryCondition){
        return selectList(new LambdaQueryWrapper<MemberCategoryEntity>()
                .in(CollUtil.isNotEmpty(memberCategoryCondition.getMemberCategoryIds()),MemberCategoryEntity::getId,memberCategoryCondition.getMemberCategoryIds())
                .eq(Objects.nonNull(memberCategoryCondition.getMemberId()),MemberCategoryEntity::getMemberId,memberCategoryCondition.getMemberId())
                .eq(Objects.nonNull(memberCategoryCondition.getType()),MemberCategoryEntity::getType,memberCategoryCondition.getType())
                .eq(MemberCategoryEntity::getIsValid,1));
    }
}
