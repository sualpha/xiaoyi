package com.cloudyi.baseinfo.service.impl;

import com.cloudyi.baseinfo.domain.Icon;
import com.cloudyi.baseinfo.domain.entity.IconEntity;
import com.cloudyi.baseinfo.mapper.IconMapper;
import com.cloudyi.common.util.ListUtil;
import com.cloudyi.baseinfo.service.IconService;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author subo
 * @date 2023/8/12 14:12
 **/
@Lazy
@Service
public class IconServiceImpl implements IconService {

    @Resource
    private IconMapper iconMapper;
    @Override
    public List<Icon> queryList(List<Long> ids) {
        List<IconEntity> iconEntities = iconMapper.queryList(ids);
        return ListUtil.toListOrEmpty(iconEntities,Icon::fromEntity);
    }
}
