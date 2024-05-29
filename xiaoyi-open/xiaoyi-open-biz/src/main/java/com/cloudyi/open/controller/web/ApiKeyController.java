package com.cloudyi.open.controller.web;

import com.cloudyi.starter.web.service.QiNiuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Tag(name = "外部跳转页面")
@Controller
@RequestMapping(path="/open")
public class ApiKeyController {


    @Resource
    private QiNiuService qiNiuService;

    @Operation(summary = "apikey中转页")
    @RequestMapping(path = "/toApikey", method = { RequestMethod.GET })
    public ModelAndView toApiKey(@RequestParam("apikey") String apikey) {
        String url="xiaoyi/config/xiaoyi.png";
        String avatarUrl = qiNiuService.downloadUrl(url);
        ModelMap map=new ModelMap();
        map.put("apikey",apikey);
        map.put("avatarUrl",avatarUrl);
        return new ModelAndView("apikey",map);
    }
}
