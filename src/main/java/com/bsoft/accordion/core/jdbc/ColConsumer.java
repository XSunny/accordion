package com.bsoft.accordion.core.jdbc;

import java.sql.ResultSet;

/**
 * Created by sky on 2018/4/17.
 */
public interface ColConsumer {

    public void process(ResultSet result);// hold the Exception
}
