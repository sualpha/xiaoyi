package com.cloudyi.member.domain;

import lombok.Builder;
import lombok.Data;

/**
 * @author subo
 * @date 2023/7/9 14:29
 **/
@Builder
@Data
public class MemberAuthResult {

    /**
     * 登录的Token
     */
    private String token;

    /**
     * 会员ID
     */
    private Long memberId;

}
