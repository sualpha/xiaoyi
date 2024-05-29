package com.cloudyi.mini.controller;

import com.cloudyi.starter.web.annotation.ApiMiniRestController;
import com.cloudyi.starter.web.handler.response.R;
import com.cloudyi.starter.web.service.QiNiuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Tag(name = "配置管理")
@ApiMiniRestController(path = "/config")
public class ConfigController {

    @Resource
    private QiNiuService qiNiuService;

    @GetMapping(value = "/customerQrCode")
    @Operation(summary = "客服二维码")
    public R<String> customerQrCode() {
        String url="xiaoyi/config/customer_service_qrcode.jpg";
        String downloadUrl = qiNiuService.downloadUrl(url);
        return R.data(downloadUrl);
    }
}
