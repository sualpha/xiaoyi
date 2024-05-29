package com.cloudyi.mini.controller;


import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.cloudyi.ledger.api.LedgerDetailsFacade;
import com.cloudyi.ledger.api.dto.MemberStatisticsResultDTO;
import com.cloudyi.member.api.MemberApiKeyFacade;
import com.cloudyi.member.api.MemberFacade;
import com.cloudyi.member.api.dto.*;
import com.cloudyi.mini.controller.vo.ledger.LedgerYearMonthVO;
import com.cloudyi.mini.controller.vo.member.MemberVipInfoVO;
import com.cloudyi.mini.service.MiniMemberService;
import com.cloudyi.starter.web.annotation.ApiMiniRestController;
import com.cloudyi.starter.web.constant.FilePrefixEnum;
import com.cloudyi.starter.web.handler.response.R;
import com.cloudyi.starter.web.service.QiNiuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@ApiMiniRestController(path = "/member")
@Tag(name = "会员管理")
@Slf4j
public class MemberController {
    @Resource
    private MemberFacade memberFacade;

    @Resource
    private MemberApiKeyFacade memberApiKeyFacade;
    @Resource
    private MiniMemberService miniMemberService;
    @Resource
    private QiNiuService qiNiuService;

    @Resource
    private LedgerDetailsFacade ledgerDetailsFacade;

    @Operation(summary = "查询Apikey")
    @PostMapping("/queryApikey")
    public R<String> queryApikey() {
        long id = StpUtil.getLoginIdAsLong();
        MemberApiKeyDTO memberApiKeyDTO = memberApiKeyFacade.queryMemberApiKey(id);
        if(Objects.nonNull(memberApiKeyDTO)){
            return R.data(memberApiKeyDTO.getApiKey());
        }
        return R.data("");
    }

    @Operation(summary = "创建Apikey")
    @PostMapping("/createApikey")
    public R<String> createApikey() {
        long id = StpUtil.getLoginIdAsLong();
        MemberApiKeyDTO memberApiKeyDTO = memberApiKeyFacade.queryMemberApiKey(id);
        if(Objects.nonNull(memberApiKeyDTO)){
            return R.data(memberApiKeyDTO.getApiKey());
        }
        MemberApiKeyDTO createMemberApiKeyDTO=new MemberApiKeyDTO();
        createMemberApiKeyDTO.setMemberId(id);
        String apikey = RandomUtil.randomString(51);
        createMemberApiKeyDTO.setApiKey(apikey);
        memberApiKeyFacade.createMemberApiKey(createMemberApiKeyDTO);
        return R.data(apikey);
    }

    @Operation(summary = "授权")
    @GetMapping("/auth")
    public R<MemberAuthResultDTO> auth(@RequestParam("code") String code) {
        MemberAuthResultDTO auth = memberFacade.auth(code);
        return R.data(auth);
    }

    @Operation(summary = "查询会员信息")
    @PostMapping("/queryMemberDetail")
    public R<MemberDetailDTO> queryMemberDetail() {
        long id = StpUtil.getLoginIdAsLong();
        MemberDetailDTO memberDetailDTO = memberFacade.queryMemberDetail(id);
        if(StrUtil.isNotBlank(memberDetailDTO.getAvatarUrl())){
            String downloadUrl = qiNiuService.downloadUrl(memberDetailDTO.getAvatarUrl());
            memberDetailDTO.setAvatarUrl(downloadUrl);
        }
        return R.data(memberDetailDTO);
    }

    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    @Operation(summary = "修改会员信息")
    @ResponseBody
    public R<String> update(@RequestBody MemberDetailDTO memberDetailDTO ) {
        long id = StpUtil.getLoginIdAsLong();
        memberDetailDTO.setId(id);
        memberFacade.update(memberDetailDTO);
        return R.data("");
    }

    @Operation(summary = "查询会员信息")
    @PostMapping("/queryLedgerYearMonth")
    @Parameter(in = ParameterIn.HEADER,name = "xiaoYiToken", required=true)
    public R<List<LedgerYearMonthVO>> queryLedgerYearMonth() {
        long id = StpUtil.getLoginIdAsLong();
        List<LedgerYearMonthVO> ledgerYearMonthVOS = miniMemberService.queryLedgerYearMonth(id);
        return R.data(ledgerYearMonthVOS);
    }

    @Operation(summary = "查询会员类别")
    @PostMapping("/queryMemberCategory")
    @Parameter(in = ParameterIn.HEADER,name = "xiaoYiToken", required=true)
    public R<List<MemberCategoryDTO>> queryMemberCategory(@RequestBody MemberCategoryQueryDTO memberCategoryQueryDTO) {
        long id = StpUtil.getLoginIdAsLong();
        memberCategoryQueryDTO.setMemberId(id);
        return R.data(memberFacade.queryMemberCategory(memberCategoryQueryDTO));
    }

    @RequestMapping(value = "/uploadHead", method = {RequestMethod.POST})
    @Operation(summary = "头像和名称修改")
    @ResponseBody
    public R<String> uploadHead(@RequestParam("file") MultipartFile file,@RequestParam("nickName") String nickName) {
        String url = qiNiuService.upload(file, FilePrefixEnum.AVATAR);
        long id = StpUtil.getLoginIdAsLong();
        MemberDetailDTO memberDetailDTO=new MemberDetailDTO();
        memberDetailDTO.setId(id);
        memberDetailDTO.setNickName(nickName);
        memberDetailDTO.setAvatarUrl(url);
        memberFacade.update(memberDetailDTO);
        String downloadUrl = qiNiuService.downloadUrl(memberDetailDTO.getAvatarUrl());
        return R.data(downloadUrl);
    }

    @Operation(summary = "查询是否是会员")
    @PostMapping("/isVip")
    public R<MemberVipInfoVO> isVip() {
        long id = StpUtil.getLoginIdAsLong();
        MemberDetailDTO memberDetailDTO = memberFacade.queryMemberDetail(id);
        MemberVipInfoVO memberVipInfoVO=new MemberVipInfoVO();
        memberVipInfoVO.setIsVip(new Date().before(memberDetailDTO.getVipExpiresTime()));
        memberVipInfoVO.setVipExpireTime(DateUtil.formatDate(memberDetailDTO.getVipExpiresTime()));
        return R.data(memberVipInfoVO);
    }

    @Operation(summary = "使用兑换码")
    @PostMapping("/useRedeemCode")
    public R<String> useRedeemCode(@RequestBody MemberRedeemCodeUseDTO request) {
        request.setMemberId(StpUtil.getLoginIdAsLong());
        memberFacade.useMemberRedeemCode(request);
        return R.data("兑换成功");
    }

    @Operation(summary = "会员邀请")
    @PostMapping("/memberInvite")
    public R<String> memberInvite(@RequestBody MemberInviteDTO request) {
        request.setMemberId(StpUtil.getLoginIdAsLong());
        memberFacade.memberInvite(request);
        return R.data("邀请成功");
    }

    @Operation(summary = "查询会员邀请")
    @PostMapping("/queryMemberInvite")
    public R<MemberInviteQueryResultDTO> queryMemberInvite() {
        MemberInviteQueryResultDTO memberInviteQueryResultDTO = memberFacade.queryMemberInvite(StpUtil.getLoginIdAsLong());
        return R.data(memberInviteQueryResultDTO);
    }

    @Operation(summary = "统计")
    @Parameter(in = ParameterIn.HEADER, name = "xiaoYiToken", required = true)
    @RequestMapping(value = "/statistics", method = {RequestMethod.POST})
    public R<MemberStatisticsResultDTO> statistics() {
        long id = StpUtil.getLoginIdAsLong();
        MemberStatisticsResultDTO memberStatisticsResultDTO = ledgerDetailsFacade.memberStatistics(id);
        return R.data(memberStatisticsResultDTO);
    }
}
