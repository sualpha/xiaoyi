package com.cloudyi.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "chat-limit")
public class ChatLimitConfig{

    /**
     * ip 时间内最大请求次数，默认无限制
     */
    private Integer ipMaxRequest;

    /**
     * ip 最大请求时间间隔（秒）
     */
    private Integer ipMaxRequestSecond;

}
