package com.zzx.tools;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class ServiceLoginTool {
    final static String USERNAME = "admin";
    final static String PASSWORD = "admin";
    final static String SALT = "1cb23387-d51a-42d8-6e3915ede276";
    final static String URL = "http://localhost:8086/runnar_web_user/wechat/template/login";


    public boolean login() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(1000);// 设置超时
        requestFactory.setReadTimeout(1000);
        //利用复杂构造器可以实现超时设置，内部实际实现为 HttpClient
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", USERNAME);
        map.add("password", PASSWORD);
        map.add("salt", SALT);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
        ResponseEntity<String> response;
        try {
            response = restTemplate.exchange(URL, HttpMethod.POST, entity, String.class);
        } catch (Exception e) {
            return false;
        }
        String s = response.getBody();
        return Boolean.parseBoolean(s);
    }
}
