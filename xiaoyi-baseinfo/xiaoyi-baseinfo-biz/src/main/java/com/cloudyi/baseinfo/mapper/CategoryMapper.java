package com.cloudyi.baseinfo.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudyi.baseinfo.domain.entity.CategoryEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author subo
 * @date 2023/8/12 14:55
 **/
@Mapper
public interface CategoryMapper extends BaseMapper<CategoryEntity> {

    default List<CategoryEntity> queryList(List<Long> categoryIds){
        return selectList(new LambdaQueryWrapper<CategoryEntity>().in(CategoryEntity::getId,categoryIds).eq(CategoryEntity::getIsValid,1));
    }
}
