package com.cloudyi.wechat.api;

import com.cloudyi.wechat.api.dto.WechatSessionDTO;

/**
 * @author subo
 * @date 2023/7/9 10:30
 **/
public interface WechatFacade {

    WechatSessionDTO getSession(String code);
}
