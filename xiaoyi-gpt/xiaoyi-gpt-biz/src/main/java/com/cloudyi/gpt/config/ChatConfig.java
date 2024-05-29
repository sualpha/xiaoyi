package com.cloudyi.gpt.config;

import cn.hutool.core.util.StrUtil;
import com.cloudyi.gpt.enums.ApiKeyModelEnum;
import com.cloudyi.gpt.enums.ApiTypeEnum;
import com.cloudyi.gpt.enums.ConversationModelEnum;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author hncboy
 * 聊天配置参数
 */
@Data
@Component
@ConfigurationProperties(prefix = "chat")
public class ChatConfig implements InitializingBean {

    /**
     * openai
     */
    private String openaiApiKey;

    /**
     */
    private String openaiApiModel;

    /**
     */
    private String openaiApiBaseUrl;


    /**
     * azureOpenAi
     */
    private String azureOpenaiKey;

    private String azureOpenaiEndpoint;

    private String azureOpenaiDeployment;

    /**
     * openaiAccessToken
     */
    private String openaiAccessToken;

    /**
     * 反向代理地址，AccessToken 时用到
     */
    private String apiReverseProxy;

    /**
     * 超时毫秒
     */
    private Integer timeoutMs;

    /**
     * HTTP 代理主机
     */
    private String httpProxyHost;

    /**
     * HTTP 代理端口
     */
    private Integer httpProxyPort;


    private Integer firstGpt;


    /**
     * 判断是否有 http 代理
     *
     * @return true/false
     */
    public Boolean hasHttpProxy() {
        return StrUtil.isNotBlank(httpProxyHost) && Objects.nonNull(httpProxyPort);
    }

    /**
     * 获取 API 类型枚举
     *
     * @return API 类型枚举
     */
    public ApiTypeEnum getApiTypeEnum() {
        // 优先 API KEY
        if (StrUtil.isNotBlank(openaiApiKey)) {
            return ApiTypeEnum.API_KEY;
        }
        return ApiTypeEnum.ACCESS_TOKEN;
    }

    @Override
    public void afterPropertiesSet() {
        if (StrUtil.isBlank(openaiApiKey) && StrUtil.isBlank(openaiAccessToken)) {
            throw new RuntimeException("apiKey 或 accessToken 必须有值");
        }

        // ApiKey
        if (getApiTypeEnum() == ApiTypeEnum.API_KEY) {
            // apiBaseUrl 必须有值
            if (StrUtil.isBlank(openaiApiBaseUrl)) {
                throw new RuntimeException("openaiApiBaseUrl 必须有值");
            }

            // 校验 apiModel
            if (StrUtil.isBlank(openaiApiModel)) {
                openaiApiModel = ApiKeyModelEnum.GPT_3_5_TURBO.getName();
                return;
            }
            for (String modelName : ApiKeyModelEnum.NAME_MAP.keySet()) {
                if (openaiApiModel.equals(modelName)) {
                    return;
                }
            }
            throw new RuntimeException("ApiKey apiModel 填写错误");
        }

        // AccessToken
        if (getApiTypeEnum() == ApiTypeEnum.ACCESS_TOKEN) {
            // apiReverseProxy 必须有值
            if (StrUtil.isBlank(apiReverseProxy)) {
                throw new RuntimeException("apiReverseProxy 必须有值");
            }

            // 校验 apiModel
            if (StrUtil.isBlank(openaiApiModel)) {
                openaiApiModel = ConversationModelEnum.DEFAULT_GPT_3_5.getName();
                return;
            }

            if (!ConversationModelEnum.NAME_MAP.containsKey(openaiApiModel)) {
                throw new RuntimeException("AccessToken apiModel 填写错误");
            }
        }
    }
}
