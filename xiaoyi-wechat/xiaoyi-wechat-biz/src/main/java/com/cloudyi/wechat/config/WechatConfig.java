package com.cloudyi.wechat.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author subo
 * @date 2023/7/9 15:03
 **/
@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatConfig {

    private String miniAppId;

    private String miniAppSecret;
}
