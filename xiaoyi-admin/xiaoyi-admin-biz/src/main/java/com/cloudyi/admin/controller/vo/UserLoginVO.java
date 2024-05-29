package com.cloudyi.admin.controller.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(title = "用户登录接口")
public class UserLoginVO {

    @Schema(title = "用户名称",example = "sualpha")
    @NotBlank(message = "用户名称不能为空!")
    private String userName;

    @Schema(title = "密码",example = "")
    @NotBlank(message = "密码不能为空!")
    private String password;

    @Schema(title = "手机号",example = "1")
    @NotBlank(message = "手机号不能为空!")
    private String phone;

    @Schema(title = "验证码",example = "1")
    @NotBlank(message = "验证码不能为空!")
    private String verifyCode;
}
