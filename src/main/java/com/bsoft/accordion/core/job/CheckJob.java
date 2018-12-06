package com.bsoft.accordion.core.job;

import com.bsoft.accordion.core.compare.*;
import com.bsoft.accordion.core.jdbc.DataSource;
import com.bsoft.accordion.core.jdbc.JDBCConenct;
import com.bsoft.compare.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sky on 2018/4/18.
 */
public class CheckJob implements Runnable{

    private static final Logger log = LoggerFactory.getLogger(CheckJob.class);

    public CheckJob() {}

    public CheckJob(DataSource dataSourceS, DataSource dataSourceT, String sqlS, String sqlT) {
        this.dataSourceS = dataSourceS;
        this.dataSourceT = dataSourceT;
        this.sqlT = sqlT;
        this.sqlS = sqlS;
    }

    public CheckJob(DataSource dataSourceS, DataSource dataSourceT, String sqlS, String sqlT, CheckFunction function) {
        this.dataSourceS = dataSourceS;
        this.dataSourceT = dataSourceT;
        this.sqlS = sqlS;
        this.sqlT = sqlT;
        this.function = function;
    }

    public CheckJob(DataSource dataSourceS, DataSource dataSourceT, String sqlS, String sqlT, ResultProcessor processor) {
        this(dataSourceS, dataSourceT, sqlT, sqlS, null, processor);
    }

    public CheckJob(DataSource dataSourceS, DataSource dataSourceT, String sqlS, String sqlT, CheckFunction function, ResultProcessor processor) {
        this.dataSourceS = dataSourceS;
        this.dataSourceT = dataSourceT;
        this.sqlS = sqlS;
        this.sqlT = sqlT;
        this.function = function;
        this.processor = processor;
    }

    private DataSource dataSourceS;//from DB
    private DataSource dataSourceT;//from DB

    private String sqlS;//
    private String sqlT;//

    private CheckFunction function = null;

    private ResultProcessor processor = new ResultProcessor() {
        @Override
        public void process(CheckResult result) {
        }
    };

    public void setFunction(CheckFunction function) {
        this.function = function;
    }



    @Override
    public void run() {

        /*
        *
        * datasourceS
        * datasourceT
        *
        * source table
        * target table
        * execute timestamp
        *
        * (compare date)
        * start
        * end
        *
        * select count(1) from $table_name where $timeCondition
        *
        * (compare result)
        *
        * s Count
        * t Count
        *
        * */



//        Date sDate = new Date();
//        Date eDate = new Date();

//        String tableNameT = getTableName(); // from params
//        String tableNameS = getTableName(); // from params

//        String sqlS = createCompareSql(tableNameS, sDate, eDate);
//        String sqlT = createCompareSql(tableNameT, sDate, eDate);

        DataCompareEntry entryS = createDataCompareEntry(dataSourceS, sqlS);
        DataCompareEntry entryT = createDataCompareEntry(dataSourceT, sqlT);

        try {
            CheckResult result = DataChecker.check(entryS, entryT, function);
            log.info(" check result : "+result);
            this.processor.process(result);
        } catch (Exception e) {
            e.printStackTrace();
            this.processor.process(new CheckResult(false, e.getMessage()));
        }


    }

//    private String createCompareSql(String tableName, Date sDate, Date eDate, String datefield) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("SELECT count(1) FROM ");
//        sb.append(tableName);
//        sb.append(" WHERE ");
//        sb.append(datefield + " > " + sDate);
//        sb.append(datefield + " < " + eDate);
//        return sb.toString();
//    }

    private DataCompareEntry createDataCompareEntry(DataSource dataSource, String sql) {
        DataCompareEntry entry = new DataCompareEntry();
        entry.setConenct(new JDBCConenct(dataSource.getClassName(), dataSource.getUrl(), CastUtil.cast2Properties(dataSource.getProperties())));
        entry.setSql(sql);
        return entry;
    }
}
