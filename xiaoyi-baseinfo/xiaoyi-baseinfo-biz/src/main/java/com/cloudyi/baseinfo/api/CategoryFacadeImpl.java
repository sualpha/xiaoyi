package com.cloudyi.baseinfo.api;

import com.cloudyi.baseinfo.domain.Category;
import com.cloudyi.baseinfo.service.CategoryService;
import com.cloudyi.common.util.ListUtil;
import com.cloudyi.baseinfo.api.dto.CategoryDTO;
import com.cloudyi.baseinfo.convertor.CategoryConvertor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author subo
 * @date 2023/8/12 14:50
 **/
@Lazy
@Service
public class CategoryFacadeImpl implements CategoryFacade {

    @Resource
    private CategoryService categoryService;
    @Override
    public List<CategoryDTO> queryList(List<Long> categoryIds) {
        List<Category> categoryList = categoryService.queryList(categoryIds);
        return ListUtil.toListOrEmpty(categoryList, CategoryConvertor.INSTANCE::convertor);
    }

    @Override
    public List<CategoryDTO> queryAllList() {
        List<Category> categories = categoryService.queryAllList();
        return ListUtil.toListOrEmpty(categories, CategoryConvertor.INSTANCE::convertor);
    }
}
