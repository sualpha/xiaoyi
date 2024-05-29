package com.cloudyi.member.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cloudyi.orm.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@TableName("member_recharge_records")
public class MemberRechargeRecordsEntity extends BaseEntity {

    @TableId
    private Long id;

    private Long memberId;

    private Date initialTime;

    private Date finalTime;

}
