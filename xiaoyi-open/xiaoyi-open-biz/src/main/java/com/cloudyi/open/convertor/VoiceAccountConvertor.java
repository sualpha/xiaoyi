package com.cloudyi.open.convertor;

import com.cloudyi.open.domain.VoiceAccount;
import com.cloudyi.open.domain.vo.VoiceAccountRequestVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author subo
 * @date 2023/7/9 14:52
 **/
@Mapper
public interface VoiceAccountConvertor {

    VoiceAccountConvertor INSTANCE = Mappers.getMapper(VoiceAccountConvertor.class);

    VoiceAccount convertor(VoiceAccountRequestVO voiceAccountRequestVO);

}
