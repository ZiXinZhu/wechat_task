package com.zzx.listener;

import com.zzx.bean.Cache;
import com.zzx.bean.LogEntity;
import com.zzx.core.UI;
import com.zzx.service.BeforeSend;
import com.zzx.service.PublicModelWechatSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


@Component
public class StopListener implements ActionListener {

    @Autowired
    UI ui;

    @Override
    public void actionPerformed(ActionEvent e) {
        System.setProperty("state","stop");
        JOptionPane.showMessageDialog(null, "微信模板消息已经停止发送！");
    }
}
