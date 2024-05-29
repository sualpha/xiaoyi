package com.cloudyi.member.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * @author subo
 * @date 2023/7/9 14:10
 **/
@Data
@Schema(title = "用户分类")
public class MemberCategoryDTO {

    @Schema(title = "id")
    private Long id;

    private Integer type;

    private String categoryUrl;

    private String categoryGreyUrl;

    private String categoryName;

}
