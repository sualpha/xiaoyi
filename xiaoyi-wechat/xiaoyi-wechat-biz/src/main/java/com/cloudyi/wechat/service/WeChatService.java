package com.cloudyi.wechat.service;

import com.cloudyi.wechat.domain.WeChatSession;

/**
 * @author subo
 * @date 2023/7/9 13:27
 **/
public interface WeChatService {

    WeChatSession getSession(String code);
}
