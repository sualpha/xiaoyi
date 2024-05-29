package com.cloudyi.admin.mapper;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudyi.admin.domain.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {

    default UserEntity selectByUserName(String userName){
        List<UserEntity> memberEntities = selectList(new LambdaQueryWrapper<UserEntity>().eq(UserEntity::getUserName,userName));
        if(CollectionUtil.isNotEmpty(memberEntities)){
            return memberEntities.get(0);
        }
        return null;
    }

}
