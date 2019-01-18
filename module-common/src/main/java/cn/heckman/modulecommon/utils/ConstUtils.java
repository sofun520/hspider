package cn.heckman.modulecommon.utils;

import com.xiaoleilu.hutool.convert.Convert;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

@SuppressWarnings("all")
public class ConstUtils {

    static String proFilePath = null;
    static ResourceBundle bundle = null;
    static BufferedInputStream inputStream;

    static {
        try {
            String osName = System.getProperty("os.name");
            if (StringUtils.isNotBlank(osName) && (osName.startsWith("Mac") || osName.startsWith("Windows"))) {
                //mac系统或windows系统，用于本地调试
                proFilePath = "application";
                System.out.println("proFilePath=>"+proFilePath);
                bundle = ResourceBundle.getBundle(proFilePath);
            } else {
                //其他部署系统
                proFilePath = System.getProperty("user.dir") + "/config/application.properties";
                System.out.println("proFilePath=>"+proFilePath);
                inputStream = new BufferedInputStream(new FileInputStream(proFilePath));
                bundle = new PropertyResourceBundle(inputStream);
                inputStream.close();
            }
            System.out.println("====测试properties取值====>");
            System.out.println("spring.application.name:" + bundle.getString("spring.application.name"));
            System.out.println("========================>");
        } catch (Exception ex) {
            ex.printStackTrace();
//            proFilePath = "config";
//            bundle = ResourceBundle.getBundle(proFilePath);
        }
    }

    public static String getStr(String key) {
        try {
            return bundle.getString(key);
        } catch (Exception ex) {
            return "";
        }
    }

    public static String getStr(String key, String defaultValue) {
        try {
            return Convert.toStr(bundle.getString(key), defaultValue);
        } catch (Exception ex) {
            return defaultValue;
        }
//        if (StringUtils.isNotBlank(bundle.getString(key))) {
//            return bundle.getString(key);
//        } else {
//            return defaultValue;
//        }
    }

    public static int getInt(String key) {
        return Integer.parseInt(bundle.getString(key));
    }

    public static int getInt(String key, int defaultValue) {
        try {
            return Convert.toInt(bundle.getString(key), defaultValue);
        } catch (Exception ex) {
            return defaultValue;
        }
//        if (StringUtils.isNotBlank(bundle.getString(key))) {
//            return Integer.parseInt(bundle.getString(key));
//        } else {
//            return defaultValue;
//        }
    }

    public static boolean getBool(String key, boolean defaultValue) {
        try {
            return Convert.toBool(bundle.getString(key), defaultValue);
        } catch (Exception ex) {
            return defaultValue;
        }
//        if (StringUtils.isNotBlank(bundle.getString(key))) {
//            return Boolean.getBoolean(bundle.getString(key));
//        } else {
//            return defaultValue;
//        }
    }

    public static long getLong(String key, long defaultValue) {
//        return Convert.toLong(bundle.getString(key), defaultValue);
        try {
            if (StringUtils.isNotBlank(bundle.getString(key))) {
                return Long.parseLong(bundle.getString(key));
            } else {
                return defaultValue;
            }
        } catch (Exception ex) {
            return defaultValue;
        }
    }

}
