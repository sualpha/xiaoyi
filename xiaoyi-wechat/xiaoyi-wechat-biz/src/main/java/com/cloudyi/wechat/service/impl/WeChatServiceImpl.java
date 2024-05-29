package com.cloudyi.wechat.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.cloudyi.starter.web.exception.ServiceException;
import com.cloudyi.wechat.domain.WeChatSession;
import com.cloudyi.wechat.service.WeChatService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * @author subo
 * @date 2023/7/9 13:38
 **/
@Lazy
@Service
public class WeChatServiceImpl implements WeChatService {

    @Resource
    private WxMaService wxMaService;

    @Override
    public WeChatSession getSession(String code) {
        try {
            WxMaJscode2SessionResult sessionInfo = wxMaService.getUserService().getSessionInfo(code);
            return WeChatSession.toWeChatSession(sessionInfo);
        }catch (Exception e){
            throw new ServiceException(e.getMessage());
        }
    }
}
