package com.cloudyi.common.handler;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.lang.Pair;
import cn.hutool.extra.spring.SpringUtil;
import com.cloudyi.common.config.ChatLimitConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Slf4j
public class RateLimiterHandler {

    /**
     * 限流文件路径
     */
    public static final String FILE_PATH = "xiaoyi/RateLimiterTimestampMap.ser";

    /**
     * 创建一个最大线程数为 1 的线程池
     * 单线程写入文件
     */
    private static final ExecutorService executorService = Executors.newFixedThreadPool(1);

    /**
     * 存储每个 IP 的请求时间队列
     */
    public static final Map<String, Deque<LocalDateTime>> IP_REQUEST_TIMESTAMP_MAP = new ConcurrentHashMap<>();

    /**
     * 检查指定 IP 的请求是否允许发送
     *
     * @param ip ip
     * @return 是否允许
     */
    public static Pair<Boolean, String> allowRequest(ChatLimitConfig chatConfig,String ip) {
        // 初始化指定 ip 的队列
        Deque<LocalDateTime> ipTimestampDeque = IP_REQUEST_TIMESTAMP_MAP.computeIfAbsent(ip, k -> new ArrayDeque<>());
        LocalDateTime now = LocalDateTime.now();

        // 移除超出滑动窗口的请求时间
        removeExpiredRequests(ipTimestampDeque, now, chatConfig.getIpMaxRequestSecond());

        // 检查 IP 和全局限流是否触发
        boolean ipAllowed = chatConfig.getIpMaxRequest() <= 0 || ipTimestampDeque.size() < chatConfig.getIpMaxRequest();
        // 如果 IP 限流未触发，则添加请求到队列
        if (ipAllowed) {
            ipTimestampDeque.addLast(now);
            // 异步写入文件，避免阻塞当前线程
            executorService.submit(RateLimiterHandler::writeToFile);
            return new Pair<>(true, null);
        }

        // 异步写入文件，避免阻塞当前线程
        executorService.submit(RateLimiterHandler::writeToFile);

        // 计算 IP 和全局下次可发送请求的时间，选择被限流情况的时间
        Optional<LocalDateTime> ipNextSendTime = ipAllowed ? Optional.empty() :
                Optional.ofNullable(ipTimestampDeque.peekFirst()).map(dt -> dt.plusSeconds(chatConfig.getIpMaxRequestSecond()));

        // 返回限流状态和下次可发送请求的时间
        return new Pair<>(false, LocalDateTimeUtil.format(ipNextSendTime.orElse(null), DatePattern.NORM_DATETIME_PATTERN));
    }


    /**
     * 将 REQUEST_TIMESTAMP_MAP 写入文件
     */
    private static void writeToFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.registerModule(new JavaTimeModule());
        try (FileWriter fw = new FileWriter(FILE_PATH)) {
            objectMapper.writeValue(fw, TimestampPair.builder()
                    .ipMap(IP_REQUEST_TIMESTAMP_MAP).build());
        } catch (Exception e) {
            log.warn("限流数据写入文件失败", e);
        }
    }

    /**
     * 移除超出滑动窗口的请求时间
     *
     * @param timestampDeque   请求时间队列
     * @param now              当前时间
     * @param maxRequestSecond 滑动窗口时长
     */
    private static void removeExpiredRequests(Deque<LocalDateTime> timestampDeque, LocalDateTime now, int maxRequestSecond) {
        while (!timestampDeque.isEmpty() && now.minusSeconds(maxRequestSecond).isAfter(timestampDeque.peekFirst())) {
            timestampDeque.pollFirst();
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TimestampPair {

        /**
         * ip Map
         */
        private Map<String, Deque<LocalDateTime>> ipMap;
    }
}
