package com.cloudyi.member.mapper;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudyi.member.domain.entity.MemberApiKeyEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberApiKeyMapper extends BaseMapper<MemberApiKeyEntity> {

    default MemberApiKeyEntity selectOneByApikey(String Apikey) {
        List<MemberApiKeyEntity> memberApiKeyEntities = selectList(new LambdaQueryWrapper<MemberApiKeyEntity>()
                .eq(MemberApiKeyEntity::getApiKey, Apikey)
                .eq(MemberApiKeyEntity::getIsValid, 1));
        if(CollectionUtil.isNotEmpty(memberApiKeyEntities)){
            return memberApiKeyEntities.get(0);
        }
        return null;
    }

    default MemberApiKeyEntity selectOneByMemberId(Long memberId) {
        List<MemberApiKeyEntity> memberApiKeyEntities = selectList(new LambdaQueryWrapper<MemberApiKeyEntity>()
                .eq(MemberApiKeyEntity::getMemberId, memberId)
                .eq(MemberApiKeyEntity::getIsValid, 1));
        if(CollectionUtil.isNotEmpty(memberApiKeyEntities)){
            return memberApiKeyEntities.get(0);
        }
        return null;
    }
}
