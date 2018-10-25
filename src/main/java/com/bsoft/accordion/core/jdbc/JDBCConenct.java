package com.bsoft.accordion.core.jdbc;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.sql.*;

/**
 * @author sky
 *
 * JDBC 基类
 *
 */
public class JDBCConenct {

//	private static final Logger log = LoggerFactory.getLogger(JDBCConenct.class);

	protected Connection connect = null;

	private int initStats = -1;

	public JDBCConenct(){

	}

	public JDBCConenct(String className, String url) {
		try {
			Class.forName(className);
			DriverManager.setLoginTimeout(6);
			connect = DriverManager.getConnection(url);
			checkConnecction();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
			close();
		}
	}

	public JDBCConenct(String className, String url, Properties properties) {
		try {
			Class.forName(className);
			DriverManager.setLoginTimeout(6);
			connect = DriverManager.getConnection(url, properties);
			checkConnecction();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
			close();
		}
	}

	public JDBCConenct(String className, String url, String username, String password) {
		try {
			Class.forName(className);
			DriverManager.setLoginTimeout(6);
			connect = DriverManager.getConnection(url, username, password);
			checkConnecction();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
			close();
		}
	}

	private boolean checkConnecction(){
		if (connect != null){
			initStats = 0;
			return true;
		}
		initStats = -1;
		return false;
	}

	private void vaildConnection() throws Exception{
		if (this.initStats != 0 || connect.isClosed()){
			throw new Exception(" connect invalid .");
		}
	}

	public static void testConnect(String className, String url, Properties properties) throws ClassNotFoundException, SQLException {
		Connection connection = null;
		try {
			Class.forName(className);
			DriverManager.setLoginTimeout(6);
			connection = DriverManager.getConnection(url, properties);
		}
		finally {
			if (null != connection)
				connection.close();
		}

	}

	public Connection getConnect() {
		return connect;
	}

	public void setConnect(Connection connection){
		this.connect = connection;
		this.initStats = 0;
	}

	public int executeSql(String sql) throws Exception{
		int rs = 0;
		Statement statement = null;
		try {
			vaildConnection();
			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// Result set get the result of the SQL query
			rs = statement.executeUpdate(sql);

			connect.commit();

//			log.info("SQL: " + sql + " | result : modify " + rs + " rows.");

		} catch (Exception e) {

			throw e;

		} finally {
			if (statement != null) {
				statement.close();
			}
//			if (connect != null) {
//				connect.close();
//			}
		}

		return rs;
	}

	public void close() {
		try {
			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List<Map<String,Object>> executeQueryEx(String sql, ColConsumer processor) throws Exception {//流式读取接口
		List<Map<String,Object>> result = null;
		ResultSet rs = null;
		PreparedStatement statement = null;
		try {
			vaildConnection();
			statement = connect.prepareStatement(sql,ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_READ_ONLY);
			statement.setFetchSize(Integer.MIN_VALUE);
			statement.setFetchDirection(ResultSet.FETCH_FORWARD);
			// Result set get the result of the SQL query

			rs = statement.executeQuery();
			processor.process(rs);
		}
		catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		finally {
			if (rs != null){
				rs.close();
			}
			if (statement != null) {
				statement.close();
			}
		}
		return result;
	}

	public List<Map<String,Object>> executeQuery(String sql) throws Exception {
		List<Map<String,Object>> result = null;
		ResultSet rs = null;
		PreparedStatement statement = null;
		try {
			vaildConnection();
			statement = connect.prepareStatement(sql);
			// Result set get the result of the SQL query

			rs = statement.executeQuery();
			result = parseResult(rs);
//			log.info("SQL: " + sql + " | result : get " + rs.getFetchSize() + " recodes.");
		}
		catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		finally {
			if (rs != null){
				rs.close();
			}
			if (statement != null) {
				statement.close();
			}
		}
		return result;
	}

	public static List<Map<String,Object>> parseResult(ResultSet rs) {
		List<Map<String,Object>> result = new ArrayList<Map<String, Object>>();
		if (rs != null){
			try {
				List<String> cols = new ArrayList<String>();
				ResultSetMetaData metadata = rs.getMetaData();
				for (int i = 1;i <= metadata.getColumnCount();i++){
					String name = metadata.getColumnClassName(i);
					String type = metadata.getColumnTypeName(i);
					String label = metadata.getColumnLabel(i);
					cols.add(label);
				}
				Collections.sort(cols);
				while (rs.next()){
					Map<String, Object> record = new HashMap();
					for(String label : cols){
						Object obj = rs.getObject(label);
						record.put(label, obj);
					}
					result.add(record);
				}
//				if (result.size() == 0){
//					Map<String, Object> record = new HashMap();
//					for (String label : cols) {
//						record.put(label, null);
//					}
//					result.add(record);
//				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}
