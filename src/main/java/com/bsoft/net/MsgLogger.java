package com.bsoft.net;

import com.bsoft.db.GenerObjectPersistent;
import com.bsoft.db.RecordFactory;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by sky on 2018/10/26.
 */
public class MsgLogger {

    private static String  TARGET_TABLE = "targetTable";

    private static ExecutorService threads = Executors.newFixedThreadPool(10);

    public static void save(Map<String,Object> data){

        threads.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    GenerObjectPersistent.getInstance().save(RecordFactory.quickCreate(TARGET_TABLE, data));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
