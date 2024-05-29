package com.cloudyi.starter.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author subo
 * @date 2023/7/9 15:03
 **/
@Data
@Component
@ConfigurationProperties(prefix = "qiniu")
public class QiNiuConfig {

    private String accessKey;

    private String secretKey;

    private String bucketName;
}
