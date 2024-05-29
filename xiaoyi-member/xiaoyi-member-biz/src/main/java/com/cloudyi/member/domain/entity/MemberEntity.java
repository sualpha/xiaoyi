package com.cloudyi.member.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cloudyi.orm.domain.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * @author subo
 * @date 2023/7/9 14:23
 **/
@Data
@TableName("member")
public class MemberEntity extends BaseEntity {

    private Long id;

    private String nickName;

    private String openId;

    private String unionId;

    private String avatarUrl;

    private Date vipExpiresTime;

}
