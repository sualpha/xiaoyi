package com.cloudyi.gpt.util;

import com.azure.core.http.HttpClient;
import com.azure.core.util.HttpClientOptions;
import com.cloudyi.gpt.enums.ApiTypeEnum;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class HttpClientUtil {

    private static final Map<ApiTypeEnum, HttpClient> INSTANCE_MAP = new ConcurrentHashMap<>();

    private HttpClientUtil() {
        // private constructor to prevent instantiation from outside
    }

    /**
     * 获取一个单例 OkHttpClient 实例
     *
     * @param apiTypeEnum    Api 类型
     * @param connectTimeout 连接超时时间（毫秒）
     * @param readTimeout    读取超时时间（毫秒）
     * @param writeTimeout   写入超时时间（毫秒）
     * @return OkHttpClient 实例
     */
    public static HttpClient getInstance(ApiTypeEnum apiTypeEnum, int connectTimeout, int readTimeout, int writeTimeout) {
        HttpClient instance = INSTANCE_MAP.get(apiTypeEnum);
        if (instance == null) {
            synchronized (HttpClientUtil.class) {
                instance = INSTANCE_MAP.get(apiTypeEnum);
                if (instance == null) {
                    HttpClientOptions httpClientOptions=new HttpClientOptions();
                    httpClientOptions.setConnectTimeout(Duration.ofMillis(connectTimeout))
                            .readTimeout(Duration.ofMillis(readTimeout))
                            .setWriteTimeout(Duration.ofMillis(readTimeout));
                    instance = HttpClient.createDefault(httpClientOptions);
                    INSTANCE_MAP.put(apiTypeEnum, instance);
                }
            }
        }
        return instance;
    }
}
