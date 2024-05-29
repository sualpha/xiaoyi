package com.cloudyi.member.api.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(title = "邀请查询")
public class MemberInviteQueryResultDTO {

    @Schema(title = "邀请次数")
    private Integer inviteCount;

    @Schema(title = "邀请天数")
    @NotNull(message = "邀请天数不能为空!")
    private Integer inviteDay;

}
