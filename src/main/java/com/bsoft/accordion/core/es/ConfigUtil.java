package com.bsoft.accordion.core.es;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by sky on 2017/7/21.
 */
public class ConfigUtil {

    static Map<String, Object> config = new HashMap<String, Object>();

    public static Map<String,Object> getConfig(){
        if (config.size() == 0) {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("esConfig.properties");
            Properties prop = new Properties();
            try {
                prop.load(is);
                for (Object key : prop.keySet()) {
                    config.put(key.toString(), prop.getProperty(key.toString()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return config;
    }
}
