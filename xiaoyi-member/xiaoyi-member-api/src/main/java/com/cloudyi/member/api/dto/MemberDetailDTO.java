package com.cloudyi.member.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * @author subo
 * @date 2023/7/9 14:10
 **/
@Data
@Schema(title = "用户详情")
public class MemberDetailDTO {

    @Schema(title = "id")
    private Long id;

    @Schema(title = "nickName")
    private String nickName;

    @Schema(title = "avatarUrl")
    private String avatarUrl;

    @Schema(title = "createTime")
    private Date createTime;

    @Schema(title = "vipExpiresTime")
    private Date vipExpiresTime;

}
