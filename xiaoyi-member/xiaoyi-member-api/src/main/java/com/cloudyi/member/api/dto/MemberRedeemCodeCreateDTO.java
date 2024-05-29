package com.cloudyi.member.api.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(title = "会员兑换码创建接口")
public class MemberRedeemCodeCreateDTO {

    @Schema(title = "会员id")
    @NotNull(message = "会员id不能为空!")
    private Long memberId;

    @Schema(title = "数量")
    @NotBlank(message = "数量不能为空!")
    private Integer num;

    @Schema(title = "手机号",example = "1.月 2.年")
    @NotBlank(message = "手机号不能为空!")
    private Integer unit;

}
