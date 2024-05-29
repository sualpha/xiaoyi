package com.cloudyi.admin.mapper;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudyi.admin.domain.entity.PhoneVerifyCodeEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface PhoneVerifyCodeMapper extends BaseMapper<PhoneVerifyCodeEntity> {

    default PhoneVerifyCodeEntity selectByPhone(String phone,String verifyCode,Integer bizType){
        List<PhoneVerifyCodeEntity> phoneVerifyCodeEntitys = selectList(
                new LambdaQueryWrapper<PhoneVerifyCodeEntity>().eq(PhoneVerifyCodeEntity::getPhone, phone)
                        .eq(PhoneVerifyCodeEntity::getVerifyCode,verifyCode)
                        .eq(PhoneVerifyCodeEntity::getBizType, bizType));
        if(CollectionUtil.isNotEmpty(phoneVerifyCodeEntitys)){
            return phoneVerifyCodeEntitys.get(0);
        }
        return null;
    }
}
