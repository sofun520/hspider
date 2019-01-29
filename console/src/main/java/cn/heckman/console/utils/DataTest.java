package cn.heckman.console.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class DataTest {

    public static void main(String[] args) {
        BDBOperatorUtil bdbOperatorUtil = new BDBOperatorUtil("D:/bdb/bdb", "phone");
        try {
            FileReader reader = new FileReader("E:/中转/20190112/YD1aa.txt");
            // 建立一个对象，它把文件内容转成计算机能读懂的语言
            BufferedReader br = new BufferedReader(reader);
            String line;
            //网友推荐更加简洁的写法
            while ((line = br.readLine()) != null) {
                // 一次读入一行数据
                System.out.println(line);
                bdbOperatorUtil.put(line,line,true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//        System.out.println(bdbOperatorUtil.getValue("19899293231"));

    }

}
