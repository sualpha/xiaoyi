package com.cloudyi.gpt.service;

import com.cloudyi.gpt.domain.GPTVoice;

public interface GPTVoiceService {

   Integer getEnumCode();

   String voice(GPTVoice GPTVoice);
}
