package com.bsoft.db;

import com.bsoft.accordion.core.date.DateUitl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sky on 2018/10/9.
 */
public class RecordWrapper {

    public RecordWrapper(){}

    public RecordWrapper(String key, Object v){
        this.data.put(key,v);
    }

    Map<String, Object> data = new HashMap<String,Object>();

    public RecordWrapper set(String k, Object v){
        this.data.put(k,v);
        return this;
    }

    public Map<String, Object> toMap(){
        return data;
    }

    public static void main(String args []){
        new RecordWrapper("id", 1)
                .set("name", "ruby")
                .set("field1", "fdas")
                .set("field2", "fdas")
                .set("field3", "fdas").toMap();

        Date data = new Date(System.currentTimeMillis());
        System.out.println(DateUitl.getStandardDateStr(data));

        try {
            GenerObjectPersistent.getInstance().drop(RecordFactory.quickCreate("logTable",
                    new RecordWrapper("id" ,1).toMap()));
            GenerObjectPersistent.getInstance().save(RecordFactory.quickCreate("logTable",
                    new RecordWrapper("id", 121)
                    .set("Jobname", "ruby")
                    .set("errorMsg", "exe")
                    .set("recordName", "sl")
                    .set("updateTime", new Date()).toMap())
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
