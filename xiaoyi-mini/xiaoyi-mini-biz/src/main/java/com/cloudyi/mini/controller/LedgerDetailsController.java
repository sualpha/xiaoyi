package com.cloudyi.mini.controller;


import cn.dev33.satoken.stp.StpUtil;
import com.cloudyi.ledger.api.dto.*;
import com.cloudyi.ledger.enums.LedgerOriginEnum;
import com.cloudyi.mini.controller.vo.ledger.*;
import com.cloudyi.mini.service.MiniLedgerDetailsService;
import com.cloudyi.orm.domain.query.PageResult;
import com.cloudyi.ledger.api.LedgerDetailsFacade;
import com.cloudyi.mini.convertor.LedgerDetailConvertor;
import com.cloudyi.mini.convertor.LedgerDetailConvertorCustom;
import com.cloudyi.starter.web.annotation.ApiMiniRestController;
import com.cloudyi.starter.web.handler.response.R;
import com.cloudyi.starter.web.service.IpRateLimiterEmitterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@ApiMiniRestController(path="/ledger/details")
@Tag(name = "账单明细管理")
@Slf4j
public class LedgerDetailsController {
    @Resource
    private LedgerDetailsFacade ledgerDetailsFacade;

    @Resource
    private MiniLedgerDetailsService miniLedgerDetailsService;

    @Resource
    private IpRateLimiterEmitterService ipRateLimiterEmitterService;

    @Operation(summary = "查询账单明细")
    @PostMapping("/queryList")
    public R<PageResult<LedgerDetailQueryGroupResultVO>> queryList(@RequestBody @Valid LedgerDetailQueryVO reqVO) {
        LedgerDetailQueryDTO ledgerDetailQueryDTO = LedgerDetailConvertor.INSTANCE.convertor(reqVO);
        ledgerDetailQueryDTO.setMemberId(StpUtil.getLoginIdAsLong());
        PageResult<LedgerDetailQueryResultDTO> ledgerDetailQueryResultDTOPageResult = ledgerDetailsFacade.queryLedgerDetails(ledgerDetailQueryDTO);
        PageResult<LedgerDetailQueryGroupResultVO> ledgerDetailQueryGroupResultVOPageResult = LedgerDetailConvertorCustom.convertorLedgerDetailQueryResultVO(ledgerDetailQueryResultDTOPageResult);
        return R.data(ledgerDetailQueryGroupResultVOPageResult);
    }

    @Operation(summary = "查询账单明细")
    @PostMapping("/queryDetail/{id}")
    public R<LedgerDetailQueryResultDTO> queryDetail(@PathVariable(value = "id") Long id) {
        LedgerDetailSingleQueryDTO ledgerDetailSingleQueryDTO=new LedgerDetailSingleQueryDTO();
        ledgerDetailSingleQueryDTO.setId(id);
        ledgerDetailSingleQueryDTO.setMemberId(StpUtil.getLoginIdAsLong());
        LedgerDetailQueryResultDTO ledgerDetailQueryResultDTO = ledgerDetailsFacade.queryDetail(ledgerDetailSingleQueryDTO);
        return R.data(ledgerDetailQueryResultDTO);
    }

    @Operation(summary = "新增账单明细")
    @PostMapping("/create")
    public R<Long> create(@RequestBody @Valid LedgerDetailCreateDTO request) {
        request.setMemberId(StpUtil.getLoginIdAsLong());
        request.setOrigin(LedgerOriginEnum.HAND.getCode());
        Long id = ledgerDetailsFacade.create(request);
        return R.data(id);
    }

    @Operation(summary = "修改账单明细")
    @PostMapping("/update")
    public R<Long> update(@RequestBody @Valid LedgerDetailCreateDTO request) {
        request.setMemberId(StpUtil.getLoginIdAsLong());
        Long id = ledgerDetailsFacade.update(request);
        return R.data(id);
    }

    @Operation(summary = "删除账单明细")
    @PostMapping("/delete/{id}")
    public R<String> delete(@PathVariable("id") Long id) {
        LedgerDetailDeleteDTO ledgerDetailDeleteDTO=new LedgerDetailDeleteDTO();
        ledgerDetailDeleteDTO.setId(id);
        ledgerDetailDeleteDTO.setMemberId(StpUtil.getLoginIdAsLong());
        ledgerDetailsFacade.delete(ledgerDetailDeleteDTO);
        return R.data("");
    }

    /**
     * 查询会员月份账单汇总信息
     *
     * @return 汇总结果
     */
    @Operation(summary = "查询账单汇总")
    @PostMapping("/queryMemberMonthLedgerSum")
    public R<MonthLedgerSumResultVO> queryMemberMonthLedgerSum(@RequestBody @Valid MonthLedgerSumQueryVO reqVO) {
        MonthLedgerSumDTO monthLedgerSumDTO = LedgerDetailConvertor.INSTANCE.convertor(reqVO);
        monthLedgerSumDTO.setMemberId(StpUtil.getLoginIdAsLong());
        MonthLedgerSumResultDTO dto = ledgerDetailsFacade.queryMemberMonthLedgerSum(monthLedgerSumDTO);
        MonthLedgerSumResultVO vo = LedgerDetailConvertor.INSTANCE.convertor(dto);
        return R.data(vo);
    }

    @RequestMapping(value = "/voice", method = {RequestMethod.POST})
    @Operation(summary = "语音记账")
    @ResponseBody
    @Parameter(in = ParameterIn.HEADER,name = "xiaoYiToken", required=true)
    public R<String> voice(@RequestParam("file") MultipartFile file) {
        long id = StpUtil.getLoginIdAsLong();
        String ipRateLimit = ipRateLimiterEmitterService.doExecute();
        if(ipRateLimit.equals("success")){
            String result = miniLedgerDetailsService.voiceAccounting(file, id);
            return R.data(result);
        }else {
            return R.data(ipRateLimit);
        }
    }


    @Operation(summary = "分页查询账单明细")
    @PostMapping("/queryPageList")
    @Parameter(in = ParameterIn.HEADER, name = "xiaoYiToken", required = true)
    public R<PageResult<LedgerDetailQueryResultDTO>> queryPageList(@RequestBody @Valid LedgerDetailQueryListVO reqVO) {
        LedgerDetailPageListDTO dto = LedgerDetailConvertor.INSTANCE.convertor(reqVO);
        dto.setMemberId(StpUtil.getLoginIdAsLong());
        PageResult<LedgerDetailQueryResultDTO> ledgerDetailQueryResultDTOPageResult = ledgerDetailsFacade.selectLedgerDetailPageList(dto);
        return R.data(ledgerDetailQueryResultDTOPageResult);
    }
}
