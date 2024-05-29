package com.cloudyi.member.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author subo
 * @date 2023/8/12 16:38
 **/
@Data
@Schema(title = "用户分类查询")
public class MemberCategoryQueryDTO {

    private Long memberId;

    private Integer type;

    private List<Long> memberCategoryIds;
}
