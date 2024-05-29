package com.cloudyi.member.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author subo
 * @date 2023/7/9 14:10
 **/
@Data
@Schema(title = "登录授权")
public class MemberAuthResultDTO {

    @Schema(title = "登录的Token")
    private String token;

    @Schema(title = "会员ID")
    private Long memberId;
}
