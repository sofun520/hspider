package cn.heckman.modulecommon.utils;

import org.springframework.core.env.Environment;

/**
 * 读取properties配置参数
 * Created by heckman on 2018/2/26.
 */
public class SystemConfig {

    public static String getProperty(String key) {
        Environment environment = SpringUtil.getBean(Environment.class);
        return environment.getProperty(key);
    }

}
