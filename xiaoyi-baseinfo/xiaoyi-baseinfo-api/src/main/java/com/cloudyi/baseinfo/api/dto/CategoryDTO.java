package com.cloudyi.baseinfo.api.dto;

import lombok.Data;

/**
 * @author subo
 * @date 2023/8/12 14:00
 **/
@Data
public class CategoryDTO {

    private Long id;
    private Integer type;
    private String name;
    private String url;
}
