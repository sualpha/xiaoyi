package com.cloudyi.wechat.api;

import com.cloudyi.wechat.api.dto.WechatSessionDTO;
import com.cloudyi.wechat.convertor.WechatConvertor;
import com.cloudyi.wechat.domain.WeChatSession;
import com.cloudyi.wechat.service.WeChatService;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * @author subo
 * @date 2023/7/9 10:33
 **/
@Lazy
@Service
public class WeChatFacadeImpl implements WechatFacade{

    @Resource
    private WeChatService weChatService;

    @Override
    public WechatSessionDTO getSession(String code) {
        WeChatSession session = weChatService.getSession(code);
        return WechatConvertor.INSTANCE.convertor(session);
    }
}
