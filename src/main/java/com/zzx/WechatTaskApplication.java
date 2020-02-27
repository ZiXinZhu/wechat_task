package com.zzx;

import com.zzx.config.LifeCycleConfiguration;
import com.zzx.tools.ServiceLoginTool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class WechatTaskApplication {

    public static void main(String[] args) {
        System.setProperty("state","start");
        new AnnotationConfigApplicationContext(LifeCycleConfiguration.class);
        try{
            Thread.currentThread().join();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
