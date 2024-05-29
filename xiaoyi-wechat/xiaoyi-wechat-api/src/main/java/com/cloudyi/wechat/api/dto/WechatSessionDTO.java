package com.cloudyi.wechat.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author subo
 * @date 2023/7/9 10:05
 **/
@Data
@Schema(title = "登录成功后返回前端用户信息")
public class WechatSessionDTO {

    @Schema(title = "用户openId")
    private String openId;

    @Schema(title = "用户unionId")
    private String unionId;

    @Schema(title = "用户sessionKey")
    private String sessionKey;
}
