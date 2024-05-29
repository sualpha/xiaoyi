package com.cloudyi.baseinfo.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cloudyi.baseinfo.domain.Category;
import com.cloudyi.baseinfo.mapper.CategoryMapper;
import com.cloudyi.baseinfo.service.CategoryService;
import com.cloudyi.common.util.ListUtil;
import com.cloudyi.baseinfo.domain.Icon;
import com.cloudyi.baseinfo.domain.entity.CategoryEntity;
import com.cloudyi.baseinfo.service.IconService;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author subo
 * @date 2023/8/12 14:55
 **/
@Lazy
@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private IconService iconService;

    @Override
    public List<Category> queryList(List<Long> categoryIds) {
        List<CategoryEntity> categoryEntities = categoryMapper.queryList(categoryIds);
        List<Category> categoryList = ListUtil.toListOrEmpty(categoryEntities, Category::fromEntity);
        if(CollUtil.isNotEmpty(categoryList)){
            List<Long> iconList = categoryList.stream().map(Category::getIconId).collect(Collectors.toList());
            List<Icon> icons = iconService.queryList(iconList);
            if(CollUtil.isNotEmpty(icons)){
                Map<Long, List<Icon>> iconMap = icons.stream().collect(Collectors.groupingBy(Icon::getId));
                for (Category category : categoryList) {
                    List<Icon> iconsExist = iconMap.get(category.getIconId());
                    if(CollUtil.isNotEmpty(iconsExist)){
                        Icon icon = iconsExist.get(0);
                        category.setUrl(icon.getUrl());
                        category.setType(icon.getType());
                    }
                }
            }
        }
        return categoryList;
    }

    @Override
    public List<Category> queryAllList() {
        List<CategoryEntity> categoryEntities = categoryMapper.selectList(new LambdaQueryWrapper<>());
        List<Category> categoryList = ListUtil.toListOrEmpty(categoryEntities, Category::fromEntity);
        if(CollUtil.isNotEmpty(categoryList)){
            List<Long> iconList = categoryList.stream().map(Category::getIconId).collect(Collectors.toList());
            List<Icon> icons = iconService.queryList(iconList);
            if(CollUtil.isNotEmpty(icons)){
                Map<Long, List<Icon>> iconMap = icons.stream().collect(Collectors.groupingBy(Icon::getId));
                for (Category category : categoryList) {
                    List<Icon> iconsExist = iconMap.get(category.getIconId());
                    if(CollUtil.isNotEmpty(iconsExist)){
                        Icon icon = iconsExist.get(0);
                        category.setUrl(icon.getUrl());
                        category.setType(icon.getType());
                    }
                }
            }
        }
        return categoryList;
    }
}
