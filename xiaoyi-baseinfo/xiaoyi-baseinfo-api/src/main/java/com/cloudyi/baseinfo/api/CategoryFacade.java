package com.cloudyi.baseinfo.api;

import com.cloudyi.baseinfo.api.dto.CategoryDTO;

import java.util.List;

/**
 * @author subo
 * @date 2023/8/12 13:45
 **/
public interface CategoryFacade {

   List<CategoryDTO> queryList(List<Long> categoryIds);
   List<CategoryDTO> queryAllList();
}
