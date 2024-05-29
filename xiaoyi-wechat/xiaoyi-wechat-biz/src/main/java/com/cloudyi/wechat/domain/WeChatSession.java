package com.cloudyi.wechat.domain;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import lombok.Data;

/**
 * @author subo
 * @date 2023/7/9 13:40
 **/
@Data
public class WeChatSession {

    /**
     * 用户openId
     */
    private String openId;

    /**
     * 用户unionId
     */
    private String unionId;

    /**
     * 用户sessionKey
     */
    private String sessionKey;

    public static WeChatSession toWeChatSession(WxMaJscode2SessionResult wxMaJscode2SessionResult) {
        WeChatSession miniUserSession = new WeChatSession();
        miniUserSession.setOpenId(wxMaJscode2SessionResult.getOpenid());
        miniUserSession.setUnionId(wxMaJscode2SessionResult.getUnionid());
        miniUserSession.setSessionKey(wxMaJscode2SessionResult.getSessionKey());
        return miniUserSession;
    }
}
