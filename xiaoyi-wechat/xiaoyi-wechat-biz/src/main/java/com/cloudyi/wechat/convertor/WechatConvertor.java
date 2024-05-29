package com.cloudyi.wechat.convertor;

import com.cloudyi.wechat.api.dto.WechatSessionDTO;
import com.cloudyi.wechat.domain.WeChatSession;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WechatConvertor {

    WechatConvertor INSTANCE = Mappers.getMapper(WechatConvertor.class);

    WechatSessionDTO convertor(WeChatSession weChatSession);
}
