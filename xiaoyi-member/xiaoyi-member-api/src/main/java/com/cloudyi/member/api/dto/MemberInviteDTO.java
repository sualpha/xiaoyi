package com.cloudyi.member.api.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(title = "会员邀请")
public class MemberInviteDTO {

    @Schema(title = "会员id")
    private Long memberId;

    @Schema(title = "发起邀请会员id")
    @NotNull(message = "发起邀请会员id不能为空!")
    private Long memberInitiateId;

}
