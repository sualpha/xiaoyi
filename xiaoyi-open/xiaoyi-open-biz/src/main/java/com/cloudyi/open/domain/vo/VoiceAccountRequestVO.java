package com.cloudyi.open.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(title = "记账请求")
public class VoiceAccountRequestVO {

    @Schema(title = "apikey", description = "apikey")
    @NotBlank(message = "apiKey不能为空")
    private String apikey;

    @Schema(title = "语音记账内容", description = "语音记账内容")
    @NotBlank(message = "语音记账内容不能为空")
    private String voiceContent;

    @Schema(title = "session", description = "会话id")
    @NotBlank(message = "会话id不能为空")
    private String session;
}
