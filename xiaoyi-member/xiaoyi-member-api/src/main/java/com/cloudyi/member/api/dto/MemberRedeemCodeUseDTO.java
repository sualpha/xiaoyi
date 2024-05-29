package com.cloudyi.member.api.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(title = "会员兑换码使用接口")
public class MemberRedeemCodeUseDTO {

    @Schema(title = "会员id")
    @NotNull(message = "会员id不能为空!")
    private Long memberId;

    @Schema(title = "兑换码")
    @NotBlank(message = "兑换码不能为空!")
    private String redeemCode;

}
