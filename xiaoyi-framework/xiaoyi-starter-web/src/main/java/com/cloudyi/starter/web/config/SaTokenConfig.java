package com.cloudyi.starter.web.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.jwt.StpLogicJwtForStateless;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;
import com.cloudyi.starter.web.util.StpAdminUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class SaTokenConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，校验规则为 StpUtil.checkLogin() 登录校验。
        registry.addInterceptor(new SaInterceptor(handle -> {
                    // 管理端接口都必须管理端登录
                    SaRouter.match("/admin/**").check(r -> StpAdminUtil.checkLogin());
                    // 小程序端接口都必须用户登录
                    SaRouter.match("/mini/**").check(r ->
                            StpUtil.checkLogin()
                    );
                }))
                // 小程序授权 放行
                .excludePathPatterns("/mini/member/auth")
                // 后台登录 放行
                .excludePathPatterns("/admin/user/login")
                // swagger 放行
                .excludePathPatterns("/swagger-ui/**");
    }

    @Bean
    public StpLogic getStpLogicJwt() {
        return new StpLogicJwtForStateless();
    }
}
