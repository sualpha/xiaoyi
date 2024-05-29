package com.cloudyi.baseinfo.domain;

import com.cloudyi.baseinfo.domain.entity.IconEntity;
import lombok.Data;

/**
 * @author subo
 * @date 2023/8/12 13:03
 **/
@Data
public class Icon {

    private Long id;

    private String url;

    private Integer type;

    public static Icon fromEntity(IconEntity iconEntity){
        Icon icon = new Icon();
        icon.setId(iconEntity.getId());
        icon.setUrl(iconEntity.getUrl());
        icon.setType(iconEntity.getType());
        return icon;
    }
}
