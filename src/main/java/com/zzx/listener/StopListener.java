package com.zzx.listener;

import com.zzx.core.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


@Component
public class StopListener implements ActionListener {

    @Autowired
    UI ui;

    @Override
    public void actionPerformed(ActionEvent e) {
        System.setProperty("state", "stop");
        JOptionPane.showMessageDialog(null, "微信模板消息已经停止发送！");
    }
}
