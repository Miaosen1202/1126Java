package com.wdcloud.lms.config;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.wdcloud.utils.HmacSHA1Utils;
import com.wdcloud.utils.httpClient.HttpClientUtils;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Component
public class OssClient {
    @Value("${file.oss.appId}")
    private String appId;
    @Value("${file.oss.secretKey}")
    private String secretKey;
    @Value("${file.oss.url}")
    private String url;

    public String upload(InputStream inputStream, String fileName) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("version", 1);
        map.put("timestamp", new Date().getTime());
        return HttpClientUtils.upload("http://" + url + "/file/upload?token=" + token(JSON.toJSONString(map)), inputStream, fileName);
    }

    public String fileInfo(String fileId) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("version", 1);
        map.put("timestamp", new Date().getTime());
        map.put("fileId", fileId);
        return HttpClientUtils.sendGet("http://" + url + "/file/fileInfo", "token=" + token(JSON.toJSONString(map)));
    }

    public String delete(String fileId) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("version", 1);
        map.put("timestamp", new Date().getTime());
        map.put("fileId", fileId);
        return HttpClientUtils.sentPost(Maps.newHashMap(), "http://" + url + "/file/delete?token=" + token(JSON.toJSONString(map)), "UTF-8");
    }

    public Path download(String fileId) {
        return download(fileId, null);
    }
    public Path download(String fileId, String name) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("version", 1);
        map.put("timestamp", new Date().getTime());
        map.put("fileId", fileId);
        map.put("name", name);
        return HttpClientUtils.download("http://" + url + "/file/download?name=" + name + "&token=" + token(JSON.toJSONString(map)));
    }

    private String token(String context) {
        final String entity = Base64.encodeBase64URLSafeString(context.getBytes(StandardCharsets.UTF_8));
        String sign = HmacSHA1Utils.genHmacSHA1WithEncodeBase64URLSafe(entity, secretKey);
        return appId + "." + sign + "." + entity;
    }
}
