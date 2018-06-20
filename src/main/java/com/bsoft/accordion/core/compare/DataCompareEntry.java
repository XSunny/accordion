package com.bsoft.accordion.core.compare;

import com.bsoft.accordion.core.jdbc.JDBCConenct;

/**
 * Created by sky on 2018/4/18.
 */
public class DataCompareEntry {

    private JDBCConenct conenct;

    private String sql;

    public JDBCConenct getConenct() {
        return conenct;
    }

    public void setConenct(JDBCConenct conenct) {
        this.conenct = conenct;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
