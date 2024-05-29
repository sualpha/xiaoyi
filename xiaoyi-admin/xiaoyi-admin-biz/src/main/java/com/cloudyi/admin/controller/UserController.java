package com.cloudyi.admin.controller;

import com.cloudyi.admin.controller.vo.UserLoginVO;
import com.cloudyi.admin.domain.UserLogin;
import com.cloudyi.admin.service.UserService;
import com.cloudyi.member.api.MemberFacade;
import com.cloudyi.member.api.dto.MemberRedeemCodeCreateDTO;
import com.cloudyi.starter.web.annotation.ApiAdminRestController;
import com.cloudyi.starter.web.handler.response.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@ApiAdminRestController(path = "/user")
@Tag(name = "用户管理")
@Slf4j
public class UserController {

    @Resource
    private MemberFacade memberFacade;
    @Resource
    private UserService userService;

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public R<String> login(@RequestBody UserLoginVO request) {
        userService.login(UserLogin.toUserLogin(request));
        return R.data("");
    }

    @Operation(summary = "生成会员兑换码")
    @PostMapping("/createMemberRedeemCode")
    public R<String> createMemberRedeemCode(@RequestBody MemberRedeemCodeCreateDTO request) {
        String memberRedeemCode = memberFacade.createMemberRedeemCode(request);
        return R.data(memberRedeemCode);
    }
}
