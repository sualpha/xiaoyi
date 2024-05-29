package com.cloudyi.member.mapper;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudyi.member.domain.entity.MemberEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author subo
 * @date 2023/7/9 14:25
 **/
@Mapper
public interface MemberMapper extends BaseMapper<MemberEntity> {

    default MemberEntity selectByOpenId(String openId){
        List<MemberEntity> memberEntities = selectList(new LambdaQueryWrapper<MemberEntity>().eq(MemberEntity::getOpenId, openId).eq(MemberEntity::getIsValid, 1));
        if(CollectionUtil.isNotEmpty(memberEntities)){
            return memberEntities.get(0);
        }
        return null;
    }

    default void updateMemberEntity(MemberEntity memberEntity){
        update(memberEntity,new LambdaUpdateWrapper<MemberEntity>().eq(MemberEntity::getId,memberEntity.getId()));
    }
}
