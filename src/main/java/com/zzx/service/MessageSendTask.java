package com.zzx.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zzx.bean.Cache;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MessageSendTask implements Runnable {
    Map<Integer,List<String>> list;
    String template;
    String token;
    PublicModelWechatSender sender = new PublicModelWechatSender();
    Map<String, Object> map = new ConcurrentHashMap<>();

    public MessageSendTask(Map<Integer,List<String>> list, String template, String token) {
        this.list = list;
        this.template = template;
        this.token = token;
    }

    @Override
    public void run() {
        sendTask(list);
    }

    public void sendTask(Map<Integer,List<String>> list) {
        for (Map.Entry<Integer,List<String>> entry : list.entrySet()) {

            //TODO 对应参数
            String templates = template;
            for (int i = 0; i < entry.getValue().size(); i++) {
                templates = templates.replace("【" + (i + 1) + "】", entry.getValue().get(i));
                //TODO 一出缓存的这个值
            }
            templates = templates.replaceAll("【", "");
            templates = templates.replaceAll("】", "");
            map.put("json", templates);
            map.put("uid",JSON.parseObject(templates).getString("touser"));
            sender.send(entry.getKey(),map, getToken());

        }
    }

    public String getToken(){
        RestTemplate restTemplate=new RestTemplate();
        JSONObject jsonObject= JSON.parseObject(restTemplate.getForObject("https://root.irunnar.com/test/getRedisByKey?key=WECHAT_H5_ACCESSTOKEN",String.class));
        return jsonObject.getString("AccessToken");
    }

}
