package com.cloudyi.baseinfo.domain;

import com.cloudyi.baseinfo.domain.entity.CategoryEntity;
import lombok.Data;

/**
 * @author subo
 * @date 2023/8/12 14:52
 **/
@Data
public class Category {

    private Long id;

    private Integer type;

    private String name;

    private Long iconId;

    private String url;

    public static Category fromEntity(CategoryEntity categoryEntity){
        Category category = new Category();
        category.setId(categoryEntity.getId());
        category.setName(categoryEntity.getName());
        category.setIconId(categoryEntity.getIconId());
        return category;
    }
}
