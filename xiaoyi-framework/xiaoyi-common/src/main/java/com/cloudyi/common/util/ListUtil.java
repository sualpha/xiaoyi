package com.cloudyi.common.util;

import cn.hutool.core.collection.CollUtil;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author subo
 * @date 2023/8/12 14:23
 **/
@NoArgsConstructor
public class ListUtil {
    public static <T, R> List<R> toListOrEmpty(Collection<T> collection, Function<T,R> function) {
        if(CollUtil.isNotEmpty(collection)){
            return collection.stream().map(function).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
