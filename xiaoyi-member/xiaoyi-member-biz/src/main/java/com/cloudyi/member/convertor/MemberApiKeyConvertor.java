package com.cloudyi.member.convertor;

import com.cloudyi.member.domain.MemberApiKey;
import com.cloudyi.member.api.dto.MemberApiKeyDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author subo
 * @date 2023/7/9 14:52
 **/
@Mapper
public interface MemberApiKeyConvertor {

    MemberApiKeyConvertor INSTANCE = Mappers.getMapper(MemberApiKeyConvertor.class);

    MemberApiKeyDTO convertor(MemberApiKey memberApiKey);
    MemberApiKey convertor(MemberApiKeyDTO memberApiKeyDTO);
}
