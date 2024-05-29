package com.cloudyi.baseinfo.convertor;

import com.cloudyi.baseinfo.api.dto.CategoryDTO;
import com.cloudyi.baseinfo.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author subo
 * @date 2023/8/12 14:18
 **/
@Mapper
public interface CategoryConvertor {

    CategoryConvertor INSTANCE = Mappers.getMapper(CategoryConvertor.class);

    CategoryDTO convertor(Category category);
}
