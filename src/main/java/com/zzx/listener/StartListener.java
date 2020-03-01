package com.zzx.listener;

import com.zzx.core.UI;
import com.zzx.service.BeforeSend;
import com.zzx.service.PublicModelWechatSender;
import com.zzx.tools.ServiceLoginTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Component
public class StartListener implements ActionListener {

    @Autowired
    UI ui;
    @Autowired
    ServiceLoginTool serviceLoginTool;

    @Override
    public void actionPerformed(ActionEvent e) {
        System.setProperty("state","start");
        String token = ui.getUiCore().getToken();
        String url = ui.getUiCore().getUrl();
        String thread = ui.getUiCore().getThread();
        String template = ui.getUiCore().getJson();
        int threadNum = 10;

        //vpn环境
//        boolean result = serviceLoginTool.login();
//        if (!result) {
//            JOptionPane.showMessageDialog(null, "请在vpn环境下使用该工具！");
//            return;
//        }


        if (StringUtils.isEmpty(template)) {
            JOptionPane.showMessageDialog(null, "json模板表路径不能为空！");
            return;
        }
        if (StringUtils.isEmpty(url)) {
            JOptionPane.showMessageDialog(null, "execel表路径不能为空！");
            return;
        }
        if (StringUtils.isEmpty(token)) {
            JOptionPane.showMessageDialog(null, "token不能为空！");
            return;
        }
        try{
            int input=Integer.parseInt(thread);
            if(input>0||input<=20){
                threadNum=input;
            }
        }catch (Exception ignored){

        }

        JOptionPane.showMessageDialog(null, "点击确定开始推送！");
        ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
        executorService.submit(new BeforeSend(token, template, url, threadNum));

    }
}
