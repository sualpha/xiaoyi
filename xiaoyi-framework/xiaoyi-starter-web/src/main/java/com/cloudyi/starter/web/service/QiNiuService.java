package com.cloudyi.starter.web.service;

import com.cloudyi.starter.web.config.QiNiuConfig;
import com.cloudyi.starter.web.constant.FilePrefixEnum;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.DownloadUrl;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @author subo
 * @date 2023/8/18 11:22
 **/
@Component
@Slf4j
public class QiNiuService {

    @Resource
    private QiNiuConfig qiNiuConfig;

    @Value("${cdn.domain}")
    private String cdnDomain;

    public String upload(MultipartFile file, FilePrefixEnum filePrefixEnum) {
        String originalFilename = file.getOriginalFilename();
        // 默认不指定key的情况下，以文件内容的hash值作为文件名
        String fileName = getRandomImageName(originalFilename);
        fileName = filePrefixEnum.getName()+fileName;
        // 构造一个带指定Region对象的配置类
        Configuration cfg = new Configuration(Region.huanan());
        UploadManager uploadManager = new UploadManager(cfg);
        try {
            byte[] fileDataBytes = file.getBytes();
            Auth auth = Auth.create(qiNiuConfig.getAccessKey(), qiNiuConfig.getSecretKey());
            String uploadToken = auth.uploadToken(qiNiuConfig.getBucketName());
            Response response = uploadManager.put(fileDataBytes, fileName, uploadToken);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            String url = null;
            if (fileName == null) {
                url = putRet.hash;
            } else {
                url =  fileName;
            }
          return url;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getRandomImageName(String fileName) {
        int index = fileName.lastIndexOf(".");
        if (fileName.isEmpty() || index == -1) {
            return null;
        }
        String suffix = fileName.substring(index).toLowerCase();
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return uuid + suffix;
    }

    public String downloadUrl(String key){
        DownloadUrl url = new DownloadUrl(cdnDomain, false, key);
        // 带有效期
        long expireInSeconds = 57600;//3600 等于1小时，可以自定义链接过期时间
        long deadline = System.currentTimeMillis()/1000 + expireInSeconds;
        Auth auth = Auth.create(qiNiuConfig.getAccessKey(), qiNiuConfig.getSecretKey());
        try {
            return url.buildURL(auth, deadline);
        } catch (QiniuException e) {
          log.error(e.getMessage(),e);
        }
        return "";
    }

}
