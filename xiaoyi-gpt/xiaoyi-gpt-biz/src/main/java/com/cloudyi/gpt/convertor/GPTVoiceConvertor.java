package com.cloudyi.gpt.convertor;


import com.cloudyi.gpt.api.account.dto.GPTVoiceDTO;
import com.cloudyi.gpt.domain.GPTVoice;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GPTVoiceConvertor {

    GPTVoiceConvertor INSTANCE = Mappers.getMapper(GPTVoiceConvertor.class);

    GPTVoice convertor(GPTVoiceDTO gptVoiceDTO);
}
