package com.cloudyi.member.api;

import com.cloudyi.member.convertor.MemberApiKeyConvertor;
import com.cloudyi.member.domain.MemberApiKey;
import com.cloudyi.member.api.dto.MemberApiKeyDTO;
import com.cloudyi.member.service.MemberApiKeyService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class MemberApiKeyFacadeImpl implements MemberApiKeyFacade{

    @Resource
    private MemberApiKeyService memberApiKeyService;

    @Override
    public MemberApiKeyDTO selectOneByApikey(String apikey) {
        MemberApiKey memberApiKey = memberApiKeyService.selectOneByApikey(apikey);
        return MemberApiKeyConvertor.INSTANCE.convertor(memberApiKey);
    }

    @Override
    public void createMemberApiKey(MemberApiKeyDTO memberApiKeyDTO) {
        MemberApiKey memberApiKey = MemberApiKeyConvertor.INSTANCE.convertor(memberApiKeyDTO);
        memberApiKeyService.creeateMemberApiKey(memberApiKey);
    }

    @Override
    public MemberApiKeyDTO queryMemberApiKey(Long memberId) {
        MemberApiKey memberApiKey = memberApiKeyService.queryMemberApiKey(memberId);
        return MemberApiKeyConvertor.INSTANCE.convertor(memberApiKey);
    }
}
