package com.bsoft.db;


import com.bsoft.accordion.core.date.DateUitl;
import java.util.*;
import java.util.Date;

/**
 * Created by sky on 2018/10/9.
 */
public class SQLGenerator {

    public static String getCreateSQL(SavedRecord record) {
        StringBuilder sql = new StringBuilder();
        sql.append("create table ");
        sql.append(record.getTableName());
        sql.append(" ( ");
        for (Map.Entry entry : record.getTypeMap().entrySet()){
            String type = (String) entry.getValue();
            String key = (String) entry.getKey();
            if (key.equals(record.getPrimaryKey())){
                if (type.equals(RecordFactory.STRING)){
                    type = "VARCHAR(64)";
                }
                sql.append(key + " " + type+ " primary key,");
            }else {
                sql.append(key + " " + type+ ",");
            }
        }
        sql.deleteCharAt(sql.length()-1);
        sql.append(" );");
        return sql.toString();
    }

    public static String getDropSQL(SavedRecord record) {
        StringBuilder sql = new StringBuilder();
        sql.append("drop table ");
        sql.append(record.getTableName());
        sql.append(";");
        return sql.toString();
    }

    public static String getInsertSQL(SavedRecord record) {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into ");
        sql.append(record.getTableName());
        sql.append(" ( ");
        //keys
        for (String key :record.getDataMap().keySet()) {
            sql.append(key + ",");
        }
        sql.deleteCharAt(sql.length()-1);
        sql.append(" ) values (");
        //values
        for (String key :record.getDataMap().keySet()) {
            sql.append(getValue(record.getDataMap().get(key), record.getTypeMap().get(key)) + ",");
        }
        sql.deleteCharAt(sql.length()-1);
        sql.append(" )");
        return sql.toString();
    }

    public static String getGetSQL(SavedRecord record) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ");
        sql.append(record.getTableName());
        sql.append(" where ");
        sql.append(record.getPrimaryKey() +" = "+getValue(record.getDataMap().get(record.getPrimaryKey()),
                record.getTypeMap().get(record.getPrimaryKey())));
        return sql.toString();
    }

    public static String getDeleteSQL(SavedRecord record) {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from ");
        sql.append(record.getTableName());
        sql.append(" where ");
        sql.append(record.getPrimaryKey() +" = "+getValue(record.getDataMap().get(record.getPrimaryKey()),
                record.getTypeMap().get(record.getPrimaryKey())));
        return sql.toString();
    }

    public static String getUpdateSQL(SavedRecord record) {
        StringBuilder sql = new StringBuilder();
        sql.append("update ");
        sql.append(record.getTableName());
        sql.append(" set ");
        //keys
        for (String key :record.getDataMap().keySet()) {
            sql.append(key + " = "+getValue(record.getDataMap().get(key), record.getTypeMap().get(key))+",");
        }
        sql.deleteCharAt(sql.length()-1);

        sql.append(" where ");
        sql.append(record.getPrimaryKey() +" = "+getValue(record.getDataMap().get(record.getPrimaryKey()),
                record.getTypeMap().get(record.getPrimaryKey())));
        return sql.toString();
    }


    private static String getValue(Object o, String type) {
        if (type.equals(RecordFactory.TEXT) || type.equals(RecordFactory.STRING)){
            return "'" +o.toString().replace("'", " ")+ "'";
        }
        else if (type.equals(RecordFactory.DATE)){
            return "'" + DateUitl.getStandardDateStr((Date) o) + "'";
        }
        return o.toString();
    }

    @Deprecated
    public static String getFindSQL(SavedRecord record) {
        StringBuilder sql = new StringBuilder();
        return sql.toString();
    }
}
