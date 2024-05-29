package com.cloudyi.orm.util;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloudyi.orm.domain.query.PageResult;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;


@UtilityClass
public class PageUtil {

    public static <T, R> PageResult<R> toPage(Page<T> page, Function<T,R> function) {
        PageResult<R> pageResult = new PageResult<>();
        pageResult.setCurrent(page.getCurrent());
        pageResult.setSize(page.getSize());
        pageResult.setPages(page.getPages());
        pageResult.setTotal(page.getTotal());
        if (CollUtil.isEmpty(page.getRecords())) {
            pageResult.setList(new ArrayList<>());
        }else {
            List<R> result = page.getRecords().stream().filter(Objects::nonNull).map(function)
                    .filter(Objects::nonNull).collect(Collectors.toList());
            pageResult.setList(result);
        }
        return pageResult;
    }
}
