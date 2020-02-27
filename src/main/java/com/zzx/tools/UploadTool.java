package com.zzx.tools;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static javax.xml.bind.JAXBIntrospector.getValue;

@Service
public class UploadTool {


    /**
     * 解析execel表
     *
     * @param path
     * @return
     */
    public static Map<Integer, List<String>> upload(String path) {
        InputStream is = null;
        HSSFWorkbook hssfWorkbook = null;
        try {
            is = new FileInputStream(new File(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if (is == null) {
                return null;
            }
            hssfWorkbook = new HSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<Integer, List<String>> maps = new ConcurrentHashMap<>();


        // 循环工作表Sheet
        if (hssfWorkbook == null) {
            return null;
        }
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                return null;
            }
            // 循环行Row
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow == null) {
                    return maps;
                }
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 100; i++) {
                    HSSFCell code = hssfRow.getCell(i);
                    if (code == null) {
                        break;
                    }
                    list.add(String.valueOf(getValue(code)));
                }
                maps.put(rowNum - 1, list);
            }
        }
        return maps;
    }
}
