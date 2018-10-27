package com.bsoft.db;


import java.util.List;
import java.util.Map;

/**
 * Created by sky on 2018/10/9.
 */
public class GenerObjectPersistent {

    private GenerObjectPersistent(){}

    static GenerObjectPersistent instance = new GenerObjectPersistent();

    public static GenerObjectPersistent getInstance(){
        return instance;
    }

    public void excute(String sql) throws Exception {
        System.out.println("excute sql : " + sql);
        AdpterDataSource.getConnection().executeSql(sql);
    }

    public void create(SavedRecord record) throws Exception {
        String sql = SQLGenerator.getCreateSQL(record);
        excute(sql);
    }

    public void drop(SavedRecord record) throws Exception {
        String sql = SQLGenerator.getDropSQL(record);
        excute(sql);
    }

    public void save(SavedRecord record) throws Exception {
        if (!tableExists(record)){
            create(record);
        }
        String sql = SQLGenerator.getInsertSQL(record);
        excute(sql);
    }

    private boolean tableExists(SavedRecord record){
        String sql = "select count(1) from "+ record.getTableName();
        List<Map<String, Object>> rs = null;
        try {
            rs =  excuteQuery(sql);
        }catch (Exception e){
            //e.printStackTrace();
        }
        return rs == null?  false : true;
    }

    public Map<String, Object> get(SavedRecord record) throws Exception {
        String sql = SQLGenerator.getGetSQL(record);
        return excuteQuery(sql).get(0);
    }
    public void delete(SavedRecord record) throws Exception {
        String sql = SQLGenerator.getDeleteSQL(record);
        excute(sql);
    }
    public void update(SavedRecord record) throws Exception {
        String sql = SQLGenerator.getUpdateSQL(record);
        excute(sql);
    }

    @Deprecated
    public void seek(SavedRecord record) throws Exception {
        String sql = SQLGenerator.getFindSQL(record);
        excute(sql);
    }

    private List<Map<String, Object>> excuteQuery(String sql) {
        List<Map<String, Object>> rs = null;
        try {
            rs = AdpterDataSource.getConnection().executeQuery(sql);
        } catch (Exception e) {
            System.err.println("excuteQuery exception:" + e.getMessage());
        }
        return rs;
    }


    @Deprecated
    public void sql(String sql) throws Exception {
        excute(sql);
    }
}
