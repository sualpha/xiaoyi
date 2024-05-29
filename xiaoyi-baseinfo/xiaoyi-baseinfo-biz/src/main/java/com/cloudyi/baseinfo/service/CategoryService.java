package com.cloudyi.baseinfo.service;

import com.cloudyi.baseinfo.domain.Category;

import java.util.List;

/**
 * @author subo
 * @date 2023/8/12 14:51
 **/
public interface CategoryService {

    List<Category> queryList(List<Long> categoryIds);
    List<Category> queryAllList();
}
