package com.bsoft.accordion.core.jdbc;

import com.bsoft.accordion.core.compare.*;
import com.bsoft.accordion.core.job.CheckJob;
import com.bsoft.accordion.core.proxy.ProxyCenter;
import com.bsoft.map.xml.XMLParser;
import com.bsoft.util.ConfigUtil;
import org.junit.Before;
import org.junit.Test;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by sky on 2018/4/16.
 */
public class JDBCTest {

    JDBCConenct conenct;// 使用之后必须关闭或者管理起来

    @Before
    public void inti(){
        conenct = new JDBCConenct("com.mysql.jdbc.Driver", "jdbc:mysql://10.8.0.54:3306/test?user=root&password=123&useUnicode=true&characterEncoding=UTF-8");
    }


//    @Test
//    public void testSql(){
//        String sql = "select * from ly_dic WHERE ly_dic.key='1'";
//        try {
//            conenct.executeQueryEx(sql, new ColConsumer() {
//                @Override
//                public void process(ResultSet result) {
//                    try {
//                        while (result.next()){
//                            for(int i = 1 ; i <= result.getMetaData().getColumnCount(); i++){
//                                System.out.print(result.getObject(i)+"\t");
//                            }
//                            System.out.println();
//                        }
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            conenct.close();
//        }
//    }
//
    @Test
    public void testChecker(){
        /*
        * source table
        * target table
        * execute timestamp
        *
        * compare date
        * start
        * end
        *
        * compare result
        *
        * total
        *
        * */

        final Map<String,Object> prop = new HashMap<String, Object>();
        prop.put("user", "pkzyy");
        prop.put("password", "bsoft1234");
//        prop.put("useUnicode", "true");
//        prop.put("characterEncoding", "UTF-8");

        final DataSource dataSource1 = new DataSource("jdbc:oracle:thin:@10.10.0.30:1521/orcl", "oracle.jdbc.driver.OracleDriver", prop);//一定要使用不同的两个datasource
        DataSource dataSource2 = new DataSource("jdbc:mysql://10.8.0.54:3306/metadata", "com.mysql.jdbc.Driver", prop);//一定要使用不同的两个datasource

        try {
            List<Map<String,Object>>  re2s = dataSource1.getConnect().executeQuery(" select * from Tem_Basic_Exam_Info");
//            List<Map<String,Object>> results = dataSource1.getConnect().executeQuery((String) ConfigUtil.getConfig().get("ex.sql"));//从 xml 读取 sql
            re2s.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String sql =  "select count(1) as total_count, " +
                " sum( case tbl_name when 'testTable' then 1 else 0  end ) as day_count " +
                " from meta_source_tbl_col ";
        String sql2 = "select count(1) as total_count, " +
                " sum( case tbl_name when 'testTable' then 1 else 0 end ) as day_count " +
                " from meta_target_tbl_col ";

        ResultProcessor processor = (ResultProcessor) ProxyCenter.getProxy(ResultProcessor.class, new ResultProcessor() {
            @Override
            public void process(CheckResult result) {
                System.out.println("get result :"+result);
            }
        });

        CheckJob job = new CheckJob(dataSource1, dataSource2, sql, sql2, processor);

        Thread t = new Thread(job);
        t.start();

        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("main thread exit .");

//        JDBCConenct conenct1 = new JDBCConenct("com.mysql.jdbc.Driver", "jdbc:mysql://10.8.0.54:3306/metadata?user=root&password=123&useUnicode=true&characterEncoding=UTF-8");
//
//        DataCompareEntry entry = new DataCompareEntry();
//        entry.setConenct(this.conenct);
//        entry.setSql(sql);
//        DataCompareEntry entry2 = new DataCompareEntry();
//        entry2.setConenct(conenct1);
//        entry2.setSql(sql2);

//        try {
//            System.out.print(" check result : "+DataChecker.check(entry, entry2, null));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

}
