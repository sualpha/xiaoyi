package com.cloudyi.starter.web.config;


import com.cloudyi.starter.web.annotation.ApiAdminRestController;
import com.cloudyi.starter.web.annotation.ApiMiniRestController;
import com.cloudyi.starter.web.annotation.ApiOpenRestController;
import com.cloudyi.starter.web.constant.ApplicationConstant;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix(ApplicationConstant.ADMIN_PATH_PREFIX, c -> c.isAnnotationPresent(ApiAdminRestController.class));
        configurer.addPathPrefix(ApplicationConstant.OPEN_PATH_PREFIX, c -> c.isAnnotationPresent(ApiOpenRestController.class));
        configurer.addPathPrefix(ApplicationConstant.MINI_PATH_PREFIX, c -> c.isAnnotationPresent(ApiMiniRestController.class));
    }
}

