package com.cloudyi.baseinfo.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cloudyi.orm.domain.entity.BaseEntity;
import lombok.Data;

/**
 * @author subo
 * @date 2023/8/12 14:52
 **/
@Data
@TableName("category")
public class CategoryEntity extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private Long iconId;
}
