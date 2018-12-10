package com.neuedu.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

    private static Properties properties = new Properties();

    static {
        //通过当前线程获取流文件
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties");
        try {
            //把流文件加载到properties文件中
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取配置文件内容
     */
    public static String readByKey(String key){
        return properties.getProperty(key);
    }

}
