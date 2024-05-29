package com.cloudyi.member.domain;

import com.cloudyi.member.domain.entity.MemberApiKeyEntity;
import lombok.Data;

@Data
public class MemberApiKey {

    private Long memberId;

    private String apiKey;

    public static MemberApiKey fromEntity(MemberApiKeyEntity memberApiKeyEntity){
        MemberApiKey memberApiKey=new MemberApiKey();
        memberApiKey.setMemberId(memberApiKeyEntity.getMemberId());
        memberApiKey.setApiKey(memberApiKeyEntity.getApiKey());
        return memberApiKey;
    }

    public  MemberApiKeyEntity toEntity(MemberApiKey memberApiKey){
        MemberApiKeyEntity memberApiKeyEntity = new MemberApiKeyEntity();
        memberApiKeyEntity.setMemberId(memberApiKey.getMemberId());
        memberApiKeyEntity.setApiKey(memberApiKey.apiKey);
        return memberApiKeyEntity;
    }
}
