package com.cloudyi.baseinfo.service;

import com.cloudyi.baseinfo.domain.Icon;

import java.util.List;

/**
 * @author subo
 * @date 2023/8/12 14:08
 **/
public interface IconService {

    List<Icon> queryList(List<Long> ids);
}
