package com.bsoft.db;

import com.bsoft.accordion.core.jdbc.DataSource;
import com.bsoft.accordion.core.jdbc.JDBCConenct;
import com.bsoft.util.ConfigUtil;

import java.util.HashMap;
import java.util.*;

/**
 * Created by sky on 2018/10/9.
 */
public class AdpterDataSource extends DataSource {

    private static final int POOL_MAX_SIZE = 10;
    static DataSource instance = new DataSource();

    static List<JDBCConenct> pool = new ArrayList<>();

    public synchronized static void reset(){
        pool.clear();
    }

    static public JDBCConenct getConnection() throws Exception{
        if (pool.size() < POOL_MAX_SIZE){
            synchronized (AdpterDataSource.class){
                if (pool.size() < POOL_MAX_SIZE){
                    instance.setClassName((String) ConfigUtil.getConfig().get("ex.jdbc.classname"));
                    instance.setUrl( (String) ConfigUtil.getConfig().get("ex.jdbc.url"));
                    instance.setProperties(getJdbcConfig(ConfigUtil.getConfig()));
                    instance.configReady();
                    JDBCConenct conn = instance.getConnect();
                    pool.add(conn);
                    return conn;
                }
            }
        }
        return pool.get((int) (Math.random()*POOL_MAX_SIZE));
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
