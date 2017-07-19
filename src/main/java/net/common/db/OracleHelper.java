package net.common.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.common.util.LogUtil;
import net.common.util.PropertiesUtil;

public class OracleHelper {
	private static Class<?> cl = OracleHelper.class;
	
	// String dbUrl = "jdbc:oracle:thin:@223.93.185.193:1523/rms";
	// String userName = "rms";
	// String pwd = "rms";
	private static Connection conn = null;
	private static Statement stat;
	private static ResultSet rs = null;

	public OracleHelper(String url) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
			String dbUrl = url.split("\\?")[0];
			String user = url.split("\\?")[1].split("&")[0].split("=")[1];
			String password = url.split("\\?")[1].split("&")[1].split("=")[1];
			conn = DriverManager.getConnection(dbUrl, user, password);
			stat = conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void init(String propName) {
		String dbProp = OracleHelper.class.getClassLoader().getResource("db.properties").getFile();
		LogUtil.debug(cl, "------>" + dbProp);
		String dbUrl = PropertiesUtil.loadProperties(dbProp).getProperty(propName);
		new OracleHelper(dbUrl);
	}

	/**
	 * 执行更新
	 * 
	 * @param sql
	 * @return
	 */
	public static boolean executeUpdate(String sql) {
		try {
			stat.executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 执行查询
	 * 
	 * @param sql
	 * @return
	 */
	public static ResultSet executeQuery(String sql) {
		rs = null;
		try {
			rs = stat.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * 关闭连接
	 */
	public static void close() {
		try {
			if(null != stat)
				stat.close();
			if(null != conn)
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	public static void main(String[] args) {
//		OracleHelper conn = OracleHelper.getInstance("RMS");
//		ResultSet rs = conn.executeQuery("select * from user_password_security");
//		try {
//			while (rs.next()) {
//				System.out.println(rs.getString("security_name") + "--" + rs.getString("password_avilable_time"));
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
