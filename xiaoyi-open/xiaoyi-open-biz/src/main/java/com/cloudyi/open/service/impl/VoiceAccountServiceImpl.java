package com.cloudyi.open.service.impl;

import com.cloudyi.gpt.api.account.GPTVoiceFacade;
import com.cloudyi.gpt.api.account.dto.GPTVoiceDTO;
import com.cloudyi.member.api.MemberApiKeyFacade;
import com.cloudyi.member.api.dto.MemberApiKeyDTO;
import com.cloudyi.open.domain.VoiceAccount;
import com.cloudyi.open.domain.entity.MemberSessionEntity;
import com.cloudyi.open.mapper.MemberSessionMapper;
import com.cloudyi.open.service.VoiceAccountService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class VoiceAccountServiceImpl implements VoiceAccountService {


    @Resource
    private MemberApiKeyFacade memberApiKeyFacade;

    @Resource
    private MemberSessionMapper memberSessionMapper;

    @Resource
    private GPTVoiceFacade gptVoiceFacade;


    @Override
    public String voiceAccount(VoiceAccount voiceAccount) {
        MemberApiKeyDTO memberApiKeyDTO = memberApiKeyFacade.selectOneByApikey(voiceAccount.getApikey());
        if(Objects.isNull(memberApiKeyDTO)){
            return "apikey未查询到有效账户";
        }
        MemberSessionEntity memberSessionEntity = voiceAccount.createMemberSessionEntity(memberApiKeyDTO.getMemberId());
        memberSessionMapper.insert(memberSessionEntity);
        GPTVoiceDTO gptVoiceDTO = voiceAccount.createGPTVoiceDTO(memberApiKeyDTO.getMemberId());
        String response = gptVoiceFacade.voiceAccount(gptVoiceDTO);
        memberSessionEntity.setResponse(response);
        memberSessionMapper.updateById(memberSessionEntity);
        return response;
    }
}
