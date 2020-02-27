package com.zzx.listener;

import com.zzx.bean.Cache;
import com.zzx.bean.LogEntity;
import com.zzx.core.UI;
import com.zzx.tools.DownloadLogTool;
import com.zzx.tools.DownloadTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.ExecutorService;


@Component
public class LogListener implements ActionListener {

    @Autowired
    UI ui;
    @Autowired
    ExecutorService executorService;

    @Override
    public void actionPerformed(ActionEvent e) {
        List<LogEntity> list= Cache.list;
        System.out.println(list);
        executorService.submit(new DownloadLogTool(list));
    }
}
