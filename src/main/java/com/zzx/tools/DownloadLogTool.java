package com.zzx.tools;

import com.zzx.bean.LogEntity;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class DownloadLogTool implements Runnable {

    List<LogEntity> list;

    public DownloadLogTool(List<LogEntity> list) {
        this.list = list;
    }

    @Override
    public void run() {
        download();
    }

    /**
     * 下载execel表模板
     */
    public void download() {
        String filePath = FileChooser.getPath() + "微信消息推送日志.xls";//文件路径
        //TODO 删除之前的表
        if (filePath.equals("微信消息推送日志.xls")) {
            return;
        }
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        HSSFWorkbook workbook = new HSSFWorkbook();//创建Excel文件(Workbook)
        HSSFSheet sheet = workbook.createSheet("sheet1");//创建工作表(Sheet)
        HSSFRow row = sheet.createRow(0);// 创建行,从0开始
        row.createCell(0).setCellValue("OpenID");// 设置单元格内容,重载
        row.createCell(1).setCellValue("发送结果");
        row.createCell(2).setCellValue("发送时间");


        for (int i = 0; i < list.size(); i++) {
            HSSFRow row_one = sheet.createRow(i + 1);
            row_one.createCell(0).setCellValue(list.get(i).getOpenID());
            row_one.createCell(1).setCellValue(list.get(i).getResult());
            row_one.createCell(2).setCellValue(list.get(i).getDate());
        }


        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            workbook.write(outputStream);//保存Excel文件
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (outputStream != null) {
                outputStream.close();//关闭文件流
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("OK!");
        JOptionPane.showMessageDialog(null, "文件已下载至:" + filePath + "！");
    }


}
