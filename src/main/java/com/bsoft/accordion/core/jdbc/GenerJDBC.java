package com.bsoft.accordion.core.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author sky
 *
 * 通用 jdbc
 *
 *  当前封装的有些粗糙， 缺乏对于链接生命周期的管理，并且，接口使用耦合度过高，不利于外界使用
 */
public class GenerJDBC extends JDBCConenct {

	public GenerJDBC(String url, String className){
		super(url, className);
	}

}
