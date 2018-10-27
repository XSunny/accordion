package com.bsoft.db;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by sky on 2018/10/9.
 */
public class RecordFactory {

    public static String INT = "INT";
    public static String DOUBLE = "DOUBLE(20,10)";
    public static String STRING = "VARCHAR(200)";
    public static String DATE = "TIMESTAMP";
    public static String TEXT = "TEXT";
    /*
      快速创建一个保存实体，按照给定的目标表与字段快速创建
      约定：如果主键不指定，那么如果有key为 id的，则id 为主键；
      如果没有，那么以keyset的第一个key为主键（不推荐）
     */
    public static SavedRecord quickCreate(String targetTable, Map<String, Object> record){//PrimaryKeyMissException
        return quickCreate(targetTable, record.keySet().contains("id")? "id":record.keySet().iterator().next(), record);
    }
    public static SavedRecord quickCreate(String targetTable, String primaryKey, Map<String, Object> record){
        SavedRecord savedRecord = new SavedRecord();
        savedRecord.setDataMap(record);
        savedRecord.setPrimaryKey(primaryKey);
        savedRecord.setTableName(targetTable);
        savedRecord.setTypeMap(getTypeMap(record));
        return savedRecord;
    }

    private static Map<String,String> getTypeMap(Map<String, Object> record) {
        Map<String,String> types = new LinkedHashMap<>();
        for (Map.Entry entry: record.entrySet()) {
            String key = (String) entry.getKey();
            Object o = entry.getValue();
            if (o instanceof Integer){
                types.put(key, INT);
            }
            else if (o instanceof Double){
                types.put(key, DOUBLE);
            }
            else if (o instanceof String){
                types.put(key, STRING);
                if (((String) o).length() > 200){
                    types.put(key, TEXT);
                }
            }
            else if (o instanceof Date){
                types.put(key, DATE);
            }else{// object
                types.put(key, TEXT);
            }
        }
        return types;
    }
}
