package com.cloudyi.mini.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.cloudyi.gpt.api.account.GPTVoiceFacade;
import com.cloudyi.gpt.api.account.dto.GPTVoiceDTO;
import com.cloudyi.member.api.MemberFacade;
import com.cloudyi.member.api.dto.MemberApiKeyDTO;
import com.cloudyi.member.api.dto.MemberDetailDTO;
import com.cloudyi.mini.service.MiniLedgerDetailsService;
import com.cloudyi.starter.web.service.HuaWeiYunSisService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @author subo
 * @date 2023/8/20 18:34
 **/
@Service
public class MiniLedgerDetailsServiceImpl implements MiniLedgerDetailsService {

    @Resource
    private HuaWeiYunSisService huaWeiYunSisService;
    @Resource
    private GPTVoiceFacade gptVoiceFacade;

    @Resource
    private MemberFacade memberFacade;

    @Override
    public String voiceAccounting(MultipartFile file, Long memberId) {
        MemberDetailDTO memberDetailDTO = memberFacade.queryMemberDetail(memberId);
        int compare = DateUtil.compare(DateUtil.date(), memberDetailDTO.getVipExpiresTime());
        if(compare>0){
            return "会员已过期";
        }
        String data = huaWeiYunSisService.recognizeShortAudio(file);
        if(StrUtil.isBlank(data)){
            return "语音识别繁忙,请稍后再试";
        }
        GPTVoiceDTO gptVoiceDTO=new GPTVoiceDTO();
        gptVoiceDTO.setVoiceContent(data);
        gptVoiceDTO.setMemberId(memberId);
        UUID uuid = UUID.randomUUID();
        String result = uuid.toString().replaceAll("-", "");
        gptVoiceDTO.setSession(result);
        return gptVoiceFacade.voiceAccount(gptVoiceDTO);
    }
}
