package com.cloudyi.baseinfo.api;

import com.cloudyi.baseinfo.api.dto.IconDTO;

import java.util.List;

/**
 * @author subo
 * @date 2023/8/12 13:45
 **/
public interface IconFacade {

    List<IconDTO> queryList(List<Long> ids);
}
