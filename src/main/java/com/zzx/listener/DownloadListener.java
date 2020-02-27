package com.zzx.listener;

import com.zzx.core.UI;
import com.zzx.tools.DownloadTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;


@Component
public class DownloadListener implements ActionListener {

    @Autowired
    UI ui;
    @Autowired
    ExecutorService executorService;

    @Override
    public void actionPerformed(ActionEvent e) {

        //TODO 处理下载模板逻辑
        executorService.submit(new DownloadTool());
    }
}
