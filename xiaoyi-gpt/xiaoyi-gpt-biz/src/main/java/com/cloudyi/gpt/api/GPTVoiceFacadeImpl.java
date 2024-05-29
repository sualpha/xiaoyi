package com.cloudyi.gpt.api;

import com.cloudyi.gpt.api.account.GPTVoiceFacade;
import com.cloudyi.gpt.api.account.dto.GPTVoiceDTO;
import com.cloudyi.gpt.convertor.GPTVoiceConvertor;
import com.cloudyi.gpt.domain.GPTVoice;
import com.cloudyi.gpt.service.GPTVoiceFactory;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Lazy
@Service
public class GPTVoiceFacadeImpl implements GPTVoiceFacade {


    @Resource
    private GPTVoiceFactory gptVoiceFactory;

    @Override
    public String voiceAccount(GPTVoiceDTO gptVoiceDTO) {
        GPTVoice gptVoice = GPTVoiceConvertor.INSTANCE.convertor(gptVoiceDTO);
        return gptVoiceFactory.execute(gptVoice);
    }
}
