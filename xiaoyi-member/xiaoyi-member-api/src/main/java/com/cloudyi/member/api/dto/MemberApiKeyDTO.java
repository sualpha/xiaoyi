package com.cloudyi.member.api.dto;

import lombok.Data;

@Data
public class MemberApiKeyDTO {

    private Long memberId;

    private String apiKey;
}
