package com.cloudyi.member.service;

import com.cloudyi.member.domain.MemberApiKey;

public interface MemberApiKeyService {

    MemberApiKey selectOneByApikey(String apikey);

    void creeateMemberApiKey(MemberApiKey apikey);

    MemberApiKey queryMemberApiKey(Long memberId);
}
