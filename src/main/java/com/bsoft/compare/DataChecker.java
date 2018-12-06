package com.bsoft.compare;

import com.bsoft.accordion.core.jdbc.JDBCConenct;
import com.bsoft.accordion.core.job.SQL_Const;

import java.util.*;

/**
 * Created by sky on 2018/4/17.
 */
public class DataChecker {

    public static CheckResult check(DataCompareEntry entry1, DataCompareEntry entry2, CheckFunction function) throws Exception{
        //1
        List<Map<String,Object>> rs1 = getResults(entry1.getConenct(), entry1.getSql());;
        List<Map<String,Object>> rs2 = getResults(entry2.getConenct(), entry2.getSql());;

        //check rs1 and rs2
        if (function == null){
            return DefaultcheckResult(rs1, rs2);
        }
        return function.checkResult(rs1, rs2);
    }

    private static CheckResult DefaultcheckResult(List<Map<String, Object>> rs1, List<Map<String, Object>> rs2) throws Exception{
        if (rs1 == null || rs2 == null){
            return new CheckResult(false);
        }
        if (rs1.size() != rs2.size())
            return new CheckResult(false);
        if (rs1.size() != 1 || rs2.size() != 1){
            throw new UnsupportedResultSetException();
        }
        Map<String,Object> record1 = rs1.get(0);
        Map<String,Object> record2 = rs2.get(0);
        if (record1.size()!= record2.size()){
            return new CheckResult(false);
        }
        CheckResult result = new CheckResult();
        for (String key : record1.keySet()){
            result.set(SQL_Const.SOURCE_PREFIX+key.toLowerCase(), record1.get(key));
            result.set(SQL_Const.TARGET_PREFIX+key.toLowerCase(), record2.get(key));
            if (!equals(record1.get(key), record2.get(key))){
                result.setResult(false);
                return result;
            }
        }
        result.setResult(true);
        return result;
    }

    private static boolean equals(Object o, Object o1) {
        return o.equals(o1);
    }

    public static List<Map<String, Object>> getResults( JDBCConenct conenct1, String sql) {
        List<Map<String, Object>> rs = null;
        try {
            rs = conenct1.executeQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            conenct1.close();
        }
        return rs;
    }
}
