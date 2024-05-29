package com.cloudyi.open.domain;

import com.cloudyi.gpt.api.account.dto.GPTVoiceDTO;
import com.cloudyi.open.domain.entity.MemberSessionEntity;
import lombok.Data;

@Data
public class VoiceAccount {

    /**
     * apikey
     */
    private String apikey;

    /**
     * 语音记账内容
     */
    private String voiceContent;

    /**
     * 会话id
     */
    private String session;

    public MemberSessionEntity createMemberSessionEntity(Long memberId){
        MemberSessionEntity memberSessionEntity=new MemberSessionEntity();
        memberSessionEntity.setMemberId(memberId);
        memberSessionEntity.setSession(session);
        memberSessionEntity.setRequest(voiceContent);
        return memberSessionEntity;
    }

    public GPTVoiceDTO createGPTVoiceDTO(Long memberId){
        GPTVoiceDTO gptVoiceDTO=new GPTVoiceDTO();
        gptVoiceDTO.setVoiceContent(voiceContent);
        gptVoiceDTO.setSession(session);
        gptVoiceDTO.setMemberId(memberId);
        return gptVoiceDTO;
    }
}
