package com.homer.uistore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.homer.logger.HomerLogger;

public class DB_Config {
	public static final String DB_URL="jdbc:sqlserver://172.20.233.85:1433;databaseName=IKB_QA;"; //"jdbc:sqlserver://172.20.165.97:1433;databaseName=IKB_QA;";   //LSQA_SSC_USPNGM integratedSecurity=true;
	public static String USER="JDA_JAVA_QA";
	public static String PASS="JDA_JAVA_QA!";
	public static String SQl_DRIVER="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static Connection con = null;
	private static Logger LOG = HomerLogger.getInstance();
	
	public static Connection getConnection() {
		initConnection();
		return con;
	}

	private static void initConnection() {
		if (con == null) {
			try {
				Class.forName(DB_Config.SQl_DRIVER);
				con = DriverManager
						.getConnection(DB_URL, USER, PASS);
				LOG.info("Connection made sucessfully to DB");
			} catch (Exception e) {
				LOG.info("Connection to DB failed due to exception -->"
						+ e.getMessage());
			}
		}

	}

	public static List<String> getQryResultAsList(String Query)
			throws Exception {
		initConnection();
		Statement smt = con.createStatement();
		ResultSet rslt = smt.executeQuery(Query);
		List<String> columnVal = new ArrayList<String>();
		while (rslt.next()) {
			for (int i = 1; i <= rslt.getMetaData().getColumnCount(); i++) {
				columnVal.add(rslt.getString(i).trim());
			}
		}
		smt.close();
		return columnVal;
	}

	public static ResultSet getQryResult(String SQL) throws SQLException {
		initConnection();
		Statement smt = con.createStatement();
		return smt.executeQuery(SQL);
	}

}
