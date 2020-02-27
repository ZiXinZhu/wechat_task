package com.zzx.core;

import com.zzx.listener.DownloadListener;
import com.zzx.listener.LogListener;
import com.zzx.listener.StartListener;
import com.zzx.listener.StopListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.swing.*;
import java.awt.*;

@Component
public class UI implements Runnable {

    private UICore uiCore;
    private JTextField tokenField = new JTextField();
    private JTextField execelField = new JTextField();
    private JTextField jsonField = new JTextField();
    private JTextField threadField = new JTextField();
    private JButton download;
    private JButton start;
    private JButton stop;
    private JButton log;


    @Autowired
    DownloadListener downloadListener;
    @Autowired
    StartListener startListener;
    @Autowired
    StopListener stopListener;
    @Autowired
    LogListener logListener;


    @Override
    public void run() {
        uiCore = new UICore();
        uiCore.initialize();
    }

    public UICore getUiCore() {
        return uiCore;
    }

    public class UICore {
        public void initialize() {
            //TODO 窗口
            JFrame jFrame = new JFrame("微信模板消息推送辅助工具");
            jFrame.setSize(600, 400);
            jFrame.setLocationRelativeTo(null);
            jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            //TODO 数据块
            JPanel dataPanel = new JPanel();
            dataPanel.setBorder(BorderFactory.createTitledBorder(""));


            download = new JButton("模板下载");
            download.addActionListener(downloadListener);
            start = new JButton("开始发送");
            start.addActionListener(startListener);
            stop = new JButton("停止发送");
            stop.addActionListener(stopListener);
            log = new JButton("下载日志");
            log.addActionListener(logListener);

            dataPanel.add(download);
            dataPanel.add(start);
            dataPanel.add(stop);
            dataPanel.add(log);


            Box box = Box.createVerticalBox();

            Box row1 = Box.createHorizontalBox();
            row1.add(new JLabel("json模板:"));
            row1.add(Box.createHorizontalStrut(23));     //创建label和textFied之间的距离
            jsonField.setPreferredSize(new Dimension(400, 30));
            row1.add(jsonField);

            Box row2 = Box.createHorizontalBox();
            row2.add(new JLabel("execel地址:"));
            row2.add(Box.createHorizontalStrut(8));     //创建label和textFied之间的距离
            execelField.setPreferredSize(new Dimension(400, 30));
            row2.add(execelField);

            Box row3 = Box.createHorizontalBox();
            row3.add(new JLabel("token:"));
            row3.add(Box.createHorizontalStrut(40));     //创建label和textFied之间的距离
            tokenField.setPreferredSize(new Dimension(400, 30));
            tokenField.setText("该版本工具token不需要输入！");
            row3.add(tokenField);

            Box row4 = Box.createHorizontalBox();
            row4.add(new JLabel("线程数:"));
            row4.add(Box.createHorizontalStrut(34));     //创建label和textFied之间的距离
            threadField.setPreferredSize(new Dimension(400, 30));
            threadField.setText("默认线程数为10，请最多不要超过20个线程！");
            row4.add(threadField);

            Box row5 = Box.createHorizontalBox();

            row5.add(download);
            row5.add(Box.createHorizontalStrut(32));//横排间距
            row5.add(start);
            row5.add(Box.createHorizontalStrut(32));
            row5.add(stop);
            row5.add(Box.createHorizontalStrut(32));
            row5.add(log);

            Box sendBox = Box.createVerticalBox();
            sendBox.add(Box.createVerticalStrut(50)); //竖排间距
            sendBox.add(row1);
            sendBox.add(Box.createVerticalStrut(20));
            sendBox.add(row2);
            sendBox.add(Box.createVerticalStrut(20));
            sendBox.add(row3);
            sendBox.add(Box.createVerticalStrut(20));
            sendBox.add(row4);
            sendBox.add(Box.createVerticalStrut(60));
            sendBox.add(row5);


            dataPanel.add(sendBox);
            box.add(dataPanel);
            jFrame.add(box);
            jFrame.setResizable(false);
            jFrame.setVisible(true);
        }

        //获取token
        public String getToken() {
            if (tokenField == null) {
                return "";
            }
            String token = tokenField.getText();
            if (StringUtils.isEmpty(token)) {
                return "";
            }
            return token;
        }

        //获取execel路径
        public String getUrl() {
            if (execelField == null) {
                return "";
            }
            String url = execelField.getText();
            if (StringUtils.isEmpty(url)) {
                return "";
            }
            return url;
        }


        //获取json模板
        public String getJson() {
            if (jsonField == null) {
                return "";
            }
            String url = jsonField.getText();
            if (StringUtils.isEmpty(url)) {
                return "";
            }
            return url;
        }


        //获取线程数
        public String getThread() {
            if (threadField == null) {
                return "";
            }
            String url = threadField.getText();
            if (StringUtils.isEmpty(url)) {
                return "";
            }
            return url;
        }
    }
}
