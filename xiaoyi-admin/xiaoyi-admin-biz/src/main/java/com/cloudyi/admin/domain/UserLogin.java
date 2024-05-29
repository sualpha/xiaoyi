package com.cloudyi.admin.domain;

import com.cloudyi.admin.controller.vo.UserLoginVO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLogin {

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 验证码
     */
    private String verifyCode;

    public static UserLogin toUserLogin(UserLoginVO userLoginVO){
        UserLogin userLogin = new UserLogin();
        userLogin.setUserName(userLoginVO.getUserName());
        userLogin.setPassword(userLoginVO.getPassword());
        userLogin.setPhone(userLoginVO.getPhone());
        userLogin.setVerifyCode(userLoginVO.getVerifyCode());
        return userLogin;
    }

}
