package com.cloudyi.baseinfo.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudyi.baseinfo.domain.entity.IconEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author subo
 * @date 2023/8/12 14:07
 **/
@Mapper
public interface IconMapper extends BaseMapper<IconEntity> {

    default List<IconEntity> queryList(List<Long> ids){
       return selectList(new LambdaQueryWrapper<IconEntity>().in(IconEntity::getId,ids).eq(IconEntity::getIsValid,1));
    }
}
