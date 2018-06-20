package com.bsoft.accordion.core.job;

import java.util.Map;
import java.util.Properties;

/**
 * Created by sky on 2018/4/19.
 */
public class CastUtil {


    public static Properties cast2Properties(Map<String, Object> properties) {
        Properties prop = new Properties();
        for (String key: properties.keySet()){
            prop.setProperty(key, (String) properties.get(key));
        }
        return prop;
    }
}
