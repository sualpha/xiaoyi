package com.cloudyi.starter.web.service.impl;

import cn.hutool.core.lang.Pair;
import cn.hutool.extra.spring.SpringUtil;
import com.cloudyi.common.config.ChatLimitConfig;
import com.cloudyi.common.handler.RateLimiterHandler;
import com.cloudyi.starter.web.util.WebUtil;
import com.cloudyi.starter.web.service.IpRateLimiterEmitterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * @author subo
 * @date 2023/9/17 18:18
 **/
@Service
@Slf4j
public class IpRateLimiterServiceImpl implements IpRateLimiterEmitterService {
    @Override
    public String doExecute() {
        try {
            String ip = WebUtil.getIp();
            ChatLimitConfig chatConfig = SpringUtil.getBean(ChatLimitConfig.class);
            // 根据ip判断是够可放行
            Pair<Boolean, String> limitPair = RateLimiterHandler.allowRequest(chatConfig,ip);
            if (limitPair.getKey()) {
                return "success";
            } else {
                return "记录频繁,语音记账1小时限制"+chatConfig.getIpMaxRequest()+"条记录,请稍后再试";
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return "请稍后再试";
        }
    }
}
