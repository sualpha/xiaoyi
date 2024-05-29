package com.cloudyi.member.api;

import com.cloudyi.member.api.dto.MemberApiKeyDTO;

/**
 * @author subo
 * @date 2023/7/9 14:09
 **/
public interface MemberApiKeyFacade {

    MemberApiKeyDTO selectOneByApikey(String apikey);

    void createMemberApiKey(MemberApiKeyDTO memberApiKeyDTO);

    MemberApiKeyDTO queryMemberApiKey(Long memberId);
}
