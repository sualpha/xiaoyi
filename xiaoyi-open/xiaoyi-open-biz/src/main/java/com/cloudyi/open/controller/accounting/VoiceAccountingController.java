package com.cloudyi.open.controller.accounting;


import com.cloudyi.open.convertor.VoiceAccountConvertor;
import com.cloudyi.open.domain.VoiceAccount;
import com.cloudyi.open.domain.vo.VoiceAccountRequestVO;
import com.cloudyi.open.service.VoiceAccountService;
import com.cloudyi.starter.web.annotation.ApiOpenRestController;
import com.cloudyi.starter.web.handler.response.R;
import com.cloudyi.starter.web.service.IpRateLimiterEmitterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@ApiOpenRestController(path = "/voice")
@Tag(name = "语音记账相关接口")
@Slf4j
public class VoiceAccountingController {

    @Resource
    private VoiceAccountService voiceAccountService;

    @Resource
    private IpRateLimiterEmitterService ipRateLimiterEmitterService;

    @Operation(summary = "记账")
    @PostMapping("/accounting")
    public R<String> accounting(@Validated @RequestBody VoiceAccountRequestVO voiceAccountRequestVO) {
        VoiceAccount voiceAccount = VoiceAccountConvertor.INSTANCE.convertor(voiceAccountRequestVO);
        String ipRateLimit = ipRateLimiterEmitterService.doExecute();
        if(ipRateLimit.equals("success")){
            String result = voiceAccountService.voiceAccount(voiceAccount);
            return R.data(result);
        }else {
            return R.data(ipRateLimit);
        }
    }

    @Operation(summary = "记账会话UUID")
    @GetMapping("/session")
    public R<String> session() {
        UUID uuid = UUID.randomUUID();
        String result = uuid.toString().replaceAll("-", "");
        return R.data(result);
    }

}
