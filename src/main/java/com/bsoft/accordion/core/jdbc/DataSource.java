package com.bsoft.accordion.core.jdbc;

import com.bsoft.accordion.core.job.CastUtil;

import java.util.Map;

/**
 * Created by sky on 2018/4/19.
 */
public class DataSource {

    private boolean initStats = false;

    private String url;

    private String className;

    private Map<String, Object> properties;

    public DataSource() {
    }

    public DataSource(String url, String className, Map<String, Object> properties) {
        this.url = url;
        this.className = className;
        this.properties = properties;
        initStats = true;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }


    public JDBCConenct getConnect() throws Exception{
        if (!initStats){
            throw new IllegalStateException("datasource should be init before use.");
        }
        return  new JDBCConenct(this.getClassName(), this.getUrl(), CastUtil.cast2Properties(this.getProperties()));
    }

    public void configReady(){
        this.initStats = true;
    }

    @Override
    public String toString() {
        return "DataSource{" +
                "url='" + url + '\'' +
                ", className='" + className + '\'' +
                ", properties=" + properties +
                '}';
    }
}
