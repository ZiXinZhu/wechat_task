package com.zzx.tools;

import java.awt.Component;
import javax.swing.JFileChooser;


public class FileChooser {
    public static String getPath() {
        JFileChooser chooser = new JFileChooser();
        /*
         * 根据JFileChooser对弹出的文件夹框选择 1、只选择目录JFileChooser.DIRECTORIES_ONLY
         * 2、只选择文件JFileChooser.FILES_ONLY
         * 3、目录或者文件都可以JFileChooser.FILES_AND_DIRECTORIES
         */
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        // 保存所选目录chooser.showSaveDialog(parent);
        Component parent = null;
        int returnVal = chooser.showSaveDialog(parent);
        // 获得选中的文件对象JFileChooser.APPROVE_OPTION
        // 如果保存的目录跟获得选中的文件对象一致，成功都是返回0
        String selectPath = "";
        if (returnVal == JFileChooser.APPROVE_OPTION) {
        // 获得路径
        selectPath = chooser.getSelectedFile().getPath();
        System.out.println("你选择的目录是：" + selectPath);
        }
       return selectPath;
    }
}
