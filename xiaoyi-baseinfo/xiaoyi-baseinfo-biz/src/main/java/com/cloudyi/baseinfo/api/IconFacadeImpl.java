package com.cloudyi.baseinfo.api;

import com.cloudyi.common.util.ListUtil;
import com.cloudyi.baseinfo.api.dto.IconDTO;
import com.cloudyi.baseinfo.convertor.IconConvertor;
import com.cloudyi.baseinfo.domain.Icon;
import com.cloudyi.baseinfo.service.IconService;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author subo
 * @date 2023/8/12 14:06
 **/

@Lazy
@Service
public class IconFacadeImpl implements IconFacade {

    @Resource
    private IconService iconService;
    @Override
    public List<IconDTO> queryList(List<Long> ids) {
        List<Icon> icons = iconService.queryList(ids);
        return ListUtil.toListOrEmpty(icons,IconConvertor.INSTANCE::convertor);
    }
}
