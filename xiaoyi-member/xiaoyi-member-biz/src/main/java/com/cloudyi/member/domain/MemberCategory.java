package com.cloudyi.member.domain;

import com.cloudyi.member.domain.entity.MemberCategoryEntity;
import lombok.Data;

/**
 * @author subo
 * @date 2023/8/12 11:31
 **/
@Data
public class MemberCategory {

    private Long id;

    private Integer type;

    private Integer origin;

    private Long memberId;

    private Long iconId;

    private Long categoryId;

    private String name;

    private Integer sort;

    private String categoryUrl;

    public static MemberCategory fromEntity(MemberCategoryEntity memberCategoryEntity){
        MemberCategory memberCategory = new MemberCategory();
        memberCategory.setId(memberCategoryEntity.getId());
        memberCategory.setType(memberCategoryEntity.getType());
        memberCategory.setOrigin(memberCategoryEntity.getOrigin());
        memberCategory.setMemberId(memberCategoryEntity.getMemberId());
        memberCategory.setIconId(memberCategoryEntity.getIconId());
        memberCategory.setCategoryId(memberCategoryEntity.getCategoryId());
        memberCategory.setName(memberCategoryEntity.getName());
        memberCategory.setSort(memberCategoryEntity.getSort());
        return memberCategory;
    }

}
