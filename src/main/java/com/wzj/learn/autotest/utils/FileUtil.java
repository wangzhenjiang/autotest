package com.wzj.learn.autotest.utils;

import java.io.*;

/**
 * 请填写类的描述
 *
 * @author wangzhenjiang
 * @date 2018-01-15 16:26
 */
public class FileUtil {

    public static void main(String[] args) {
//        String fileName = "D:\\Work\\Docs\\互联网医院-自建\\银川互联网医院监管平台\\京东互联网医院-病案管理制度.pdf";
        String fileName = "D:\\Work\\Docs\\2017打卡记录\\2017004.txt";
        String content = read(new File(fileName));
        System.err.println(content);
    }

    public static String read(File file) {
        StringBuilder sb = new StringBuilder();
        BufferedReader fileReader = null;
        try {
            fileReader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = fileReader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                }
            }
        }
        return sb.toString();
    }
}
