package com.cloudyi.member.service.impl;

import com.cloudyi.member.domain.MemberApiKey;
import com.cloudyi.member.domain.entity.MemberApiKeyEntity;
import com.cloudyi.member.mapper.MemberApiKeyMapper;
import com.cloudyi.member.service.MemberApiKeyService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class MemberApiKeyServiceImpl implements MemberApiKeyService {

    @Resource
    private MemberApiKeyMapper memberApiKeyMapper;


    @Override
    public MemberApiKey selectOneByApikey(String apikey) {
        MemberApiKeyEntity memberApiKeyEntity = memberApiKeyMapper.selectOneByApikey(apikey);
        if(Objects.nonNull(memberApiKeyEntity)){
            return MemberApiKey.fromEntity(memberApiKeyEntity);
        }
        return null;
    }

    @Override
    public void creeateMemberApiKey(MemberApiKey apikey) {
        MemberApiKeyEntity entity = apikey.toEntity(apikey);
        memberApiKeyMapper.insert(entity);
    }

    @Override
    public MemberApiKey queryMemberApiKey(Long memberId) {
        MemberApiKeyEntity memberApiKeyEntity = memberApiKeyMapper.selectOneByMemberId(memberId);
        if(Objects.nonNull(memberApiKeyEntity)){
            return MemberApiKey.fromEntity(memberApiKeyEntity);
        }
        return null;
    }
}
