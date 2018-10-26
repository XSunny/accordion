package com.bsoft.net;

import com.bsoft.db.GenerObjectPersistent;
import com.bsoft.db.RecordFactory;

import java.util.*;
/**
 * Created by sky on 2018/10/26.
 */
public class MsgLogger {

    public static void save(Map<String,Object> data){
        try {
            GenerObjectPersistent.getInstance().save(RecordFactory.quickCreate("targetTable", data));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
