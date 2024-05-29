package com.cloudyi.member.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cloudyi.orm.domain.entity.BaseEntity;
import lombok.Data;

/**
 * @author subo
 * @date 2023/8/12 11:31
 **/
@Data
@TableName("member_category")
public class MemberCategoryEntity extends BaseEntity {

    @TableId
    private Long id;

    private Integer origin;

    private Integer type;

    private Long memberId;

    private Long iconId;

    private Long categoryId;

    private String name;

    private Integer sort;
    
}
