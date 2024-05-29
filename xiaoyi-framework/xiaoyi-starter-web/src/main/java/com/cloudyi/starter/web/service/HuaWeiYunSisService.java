package com.cloudyi.starter.web.service;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cloudyi.starter.web.config.HuaWeiYunSisConfig;
import com.huaweicloud.sdk.core.auth.BasicCredentials;
import com.huaweicloud.sdk.core.auth.ICredential;
import com.huaweicloud.sdk.sis.v1.SisClient;
import com.huaweicloud.sdk.sis.v1.model.Config;
import com.huaweicloud.sdk.sis.v1.model.PostShortAudioReq;
import com.huaweicloud.sdk.sis.v1.model.RecognizeShortAudioRequest;
import com.huaweicloud.sdk.sis.v1.model.RecognizeShortAudioResponse;
import com.huaweicloud.sdk.sis.v1.region.SisRegion;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

/**
 * @author subo
 * @date 2023/8/20 18:10
 **/
@Component
@Slf4j
public class HuaWeiYunSisService {

    @Resource
    private HuaWeiYunSisConfig huaWeiYunSisConfig;

    public String recognizeShortAudio(MultipartFile file) {

        ICredential auth = new BasicCredentials()
                .withAk(huaWeiYunSisConfig.getAccessKey())
                .withSk(huaWeiYunSisConfig.getSecretKey())
                .withProjectId(huaWeiYunSisConfig.getProjectId());

        SisClient client = SisClient.newBuilder()
                .withCredential(auth)
                .withRegion(SisRegion.valueOf("cn-east-3"))
                .build();
        try {
            RecognizeShortAudioRequest request = new RecognizeShortAudioRequest();
            String data = Base64Encoder.encode(file.getBytes());
            PostShortAudioReq body = new PostShortAudioReq();
            body.setData(data);
            Config config = new Config();
            config.setAudioFormat(Config.AudioFormatEnum.MP3);
            config.setProperty(Config.PropertyEnum.CHINESE_16K_GENERAL);
            body.setConfig(config);
            request.withBody(body);
            RecognizeShortAudioResponse response = client.recognizeShortAudio(request);
            log.info("语音识别的内容:"+ JSONUtil.toJsonStr(response));
            if (StrUtil.isBlank(response.getResult().getText())) {
                return "语音内容为空";
            }
            if (StrUtil.isNotBlank(response.getResult().getText())) {
                return response.getResult().getText();
            }
        } catch (Exception e) {
            log.error("调用华为云sis失败");
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
