package com.cloudyi.admin.service.impl;

import com.cloudyi.admin.domain.UserLogin;
import com.cloudyi.admin.domain.entity.PhoneVerifyCodeEntity;
import com.cloudyi.admin.domain.entity.UserEntity;
import com.cloudyi.admin.mapper.PhoneVerifyCodeMapper;
import com.cloudyi.admin.mapper.UserMapper;
import com.cloudyi.admin.service.UserService;
import com.cloudyi.admin.util.PwdUtils;
import com.cloudyi.starter.web.util.StpAdminUtil;
import com.cloudyi.common.constant.PhoneVerifyTypeEnum;
import com.cloudyi.starter.web.exception.ServiceException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private PhoneVerifyCodeMapper phoneVerifyCodeMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    public void login(UserLogin userLogin) {
        PhoneVerifyCodeEntity phoneVerifyCodeEntity = phoneVerifyCodeMapper.selectByPhone(userLogin.getPhone(),userLogin.getVerifyCode(),PhoneVerifyTypeEnum.SYSTEM_USER_LOGIN.getCode());
        if(Objects.isNull(phoneVerifyCodeEntity)){
            throw new ServiceException("验证码不正确");
        }
        UserEntity userEntity = userMapper.selectByUserName(userLogin.getUserName());
        if(Objects.isNull(userEntity)){
            throw new ServiceException("用户不存在");
        }
        String encryptPassword = PwdUtils.encryptPassword(userLogin.getUserName(), userLogin.getPassword());
        if(!userEntity.getPassWord().equals(encryptPassword)){
            throw new ServiceException("密码不正确");
        }
        StpAdminUtil.login(userLogin.getUserName());
    }
}
