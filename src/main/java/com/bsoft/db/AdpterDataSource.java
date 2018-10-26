package com.bsoft.db;

import com.bsoft.accordion.core.jdbc.DataSource;
import com.bsoft.accordion.core.jdbc.JDBCConenct;
import com.bsoft.util.ConfigUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sky on 2018/10/9.
 */
public class AdpterDataSource extends DataSource {

    static DataSource instance = new DataSource();

    static public JDBCConenct getConnection() throws Exception{
        instance.setClassName((String) ConfigUtil.getConfig().get("ex.jdbc.classname"));
        instance.setUrl( (String) ConfigUtil.getConfig().get("ex.jdbc.url"));
        instance.setProperties(getJdbcConfig(ConfigUtil.getConfig()));
        instance.configReady();
        return instance.getConnect();
    }

    private static Map<String,Object> getJdbcConfig(Map<String, Object> config) {
        Map<String,Object> jdbcConfig = new HashMap<String,Object>();
        for (String key: config.keySet()){
            if (key.startsWith("ex.jdbc.")){
                jdbcConfig.put(key, config.get(key));
            }
        }
        return jdbcConfig;
    }
}
