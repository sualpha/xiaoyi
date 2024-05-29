package com.cloudyi.wechat.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class WxMaConfiguration {

    @Resource
    private WechatConfig wechatConfig;
    @Bean
    public WxMaService wxMaService() {
        WxMaService maService = new WxMaServiceImpl();
        Map<String, WxMaConfig> configs=new HashMap<>();
        WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
        config.setAppid(wechatConfig.getMiniAppId());
        config.setSecret(wechatConfig.getMiniAppSecret());
        configs.put(wechatConfig.getMiniAppId(),config);
        maService.setMultiConfigs(configs);
        return maService;
    }
}
