package com.cloudyi.member.mapper;


import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudyi.member.domain.entity.MemberRedeemCodeEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberRedeemCodeMapper extends BaseMapper<MemberRedeemCodeEntity> {

    default MemberRedeemCodeEntity selectOneByRedeemCode(String redeemCode,Long memberId) {
        List<MemberRedeemCodeEntity> MemberRedeemCodeEntities = selectList(new LambdaQueryWrapper<MemberRedeemCodeEntity>()
                .eq(MemberRedeemCodeEntity::getRedeemCode, redeemCode)
                .eq(MemberRedeemCodeEntity::getMemberId, memberId))
                ;
        if(CollectionUtil.isNotEmpty(MemberRedeemCodeEntities)){
            return MemberRedeemCodeEntities.get(0);
        }
        return null;
    }
}
