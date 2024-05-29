package com.cloudyi.gpt.client.chatgpt;

import cn.hutool.extra.spring.SpringUtil;
import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.http.HttpClient;
import com.cloudyi.gpt.config.ChatConfig;
import com.cloudyi.gpt.enums.ApiTypeEnum;
import com.cloudyi.gpt.util.HttpClientUtil;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AzureOpenAiClientBuilder {

    /**
     * 构建 API 流式请求客户端
     *
     * @return OpenAiStreamClient
     */
    public OpenAIClient buildAzureOpenAiClient() {
        ChatConfig chatConfig = SpringUtil.getBean(ChatConfig.class);
        HttpClient httpClient = HttpClientUtil.getInstance(ApiTypeEnum.API_KEY, chatConfig.getTimeoutMs(),
                chatConfig.getTimeoutMs(), chatConfig.getTimeoutMs());
        return new OpenAIClientBuilder()
                .httpClient(httpClient)
                .endpoint(chatConfig.getAzureOpenaiEndpoint())
                .credential(new AzureKeyCredential(chatConfig.getAzureOpenaiKey()))
                .buildClient();
    }
}
