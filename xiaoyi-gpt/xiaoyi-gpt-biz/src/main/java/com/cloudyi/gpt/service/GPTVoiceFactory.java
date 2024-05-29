package com.cloudyi.gpt.service;

import cn.hutool.core.collection.CollectionUtil;
import com.cloudyi.gpt.config.ChatConfig;
import com.cloudyi.gpt.domain.GPTVoice;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GPTVoiceFactory {

    @Resource
    private List<GPTVoiceService> gptVoiceServices;

    @Resource
    private ChatConfig chatConfig;

    public String execute(GPTVoice gptVoice){
       if(CollectionUtil.isNotEmpty(gptVoiceServices)){
           for (GPTVoiceService gptVoiceService : gptVoiceServices) {
               if(gptVoiceService.getEnumCode().equals(chatConfig.getFirstGpt())){
                  return gptVoiceService.voice(gptVoice);
               }
           }
       }
       return null;
    }
}
