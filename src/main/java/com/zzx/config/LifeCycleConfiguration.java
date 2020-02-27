package com.zzx.config;


import com.zzx.core.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@ComponentScan(value = "com.zzx")
public class LifeCycleConfiguration {

    @Autowired
    private ExecutorService threadPool;
    @Autowired
    private UI ui;


    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(1500);// 设置超时
        requestFactory.setReadTimeout(1500);
        return new RestTemplate();
    }

    @Bean
    public ExecutorService getThreadPool() {
        return Executors.newFixedThreadPool(5);
    }

    @Bean
    @Order(2111111111)
    public void initial() {
        threadPool.submit(ui);
    }
}