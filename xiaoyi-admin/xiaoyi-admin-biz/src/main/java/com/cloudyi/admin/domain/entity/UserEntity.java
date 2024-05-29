package com.cloudyi.admin.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cloudyi.orm.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("user")
public class UserEntity extends BaseEntity {

    @TableId
    private Long id;

    private String userName;

    private String passWord;

}
