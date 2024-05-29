package com.cloudyi.member.domain;

import lombok.Data;

import java.util.List;

/**
 * @author subo
 * @date 2023/8/12 11:31
 **/
@Data
public class MemberCategoryCondition {

    private Long memberId;

    private Integer type;

    private List<Long> memberCategoryIds;

}
