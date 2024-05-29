package com.cloudyi.gpt.api.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "gpt语音服务")
public class GPTVoiceDTO {

    @Schema(title = "会员id", description = "会员id")
    private Long memberId;

    @Schema(title = "语音内容", description = "语音内容")
    private String voiceContent;

    @Schema(title = "session", description = "会话id")
    private String session;
}
