package net.common.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.common.ssh.SSHHelper;
import net.common.util.LogUtil;

public class MySQLHelper {
	final static Class<?> cl = MySQLHelper.class;
	
	private static Connection conn = null;
	private static PreparedStatement pst = null;

	/**
	 * 初始化需要通过SSH隧道进行端口转发的数据库连接
	 * @param bind_address 数据库绑定的地址
	 * @param lport 数据库绑定的端口
	 * @param rhost 远程数据库服务器地址
	 * @param rport 远程数据库服务器端口
	 * @param user 远程数据库用户名
	 * @param pwd 远程数据库密码
	 */
	public static void init(String bind_address, int lport, String rhost, int rport, String user, String pwd){
		// 连接跳板机
		SSHHelper.init();
		// 配置端口转发
		int assinged_port = SSHHelper.setPortForward(bind_address, lport, rhost, rport);
		String db_url = "jdbc:mysql://" + bind_address + ":" + assinged_port + "/?useUnicode=true&characterEncoding=UTF-8";
		init(db_url, user, pwd);
	}
	
	/**
	 * 初始化数据库
	 * @param url 数据库链接
	 * @param user 数据库用户名
	 * @param pwd 数据库密码
	 */
	public static void init(String url, String user, String pwd){
		try {
            //1、加载驱动
            Class.forName("com.mysql.jdbc.Driver");
        	//2、创建连接
            conn = DriverManager.getConnection(url, user, pwd);
        } catch (SQLException | ClassNotFoundException e) {
            LogUtil.err(cl, "未连接上数据库");
            e.printStackTrace();
        }
	}
	
	/**无需连接跳板机连接数据库
	 * 
	 * @param bind_address数据库地址
	 * @param lport数据库端口
	 * @param user数据库用户名
	 * @param pwd数据库密码
	 */
	public static void init(String bind_address, String lport, String user, String pwd){
		try {
			String url = "jdbc:mysql://" + bind_address + ":" + lport + "/?useUnicode=true&characterEncoding=UTF-8";
            //1、加载驱动
            Class.forName("com.mysql.jdbc.Driver");
        	//2、创建连接
            conn = DriverManager.getConnection(url, user, pwd);
        } catch (SQLException | ClassNotFoundException e) {
            LogUtil.err(cl, "未连接上数据库");
            e.printStackTrace();
        }
	}
	
	/**
	 * 查询mysql数据库
	 * @param url
	 * @param sql
	 * @return
	 */
	public static ResultSet queryDB(String sql){
		ResultSet rs = null;
		try {
			pst = conn.prepareStatement(sql);//准备执行语句  
			rs = pst.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	/**
	 * 更新mysql数据库记录
	 * @param url
	 * @param sql
	 * @return
	 */
	public static int updateDB(String sql){
		int rs = 0;
		try {
			pst = conn.prepareStatement(sql);//准备执行语句  
			rs = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	/**
	 * 执行sql
	 * @param sql
	 * @return
	 */
	public static boolean exeSql(String sql){
		boolean rs = false;
		try {
			pst = conn.prepareStatement(sql);//准备执行语句  
			rs = pst.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
  
	/**
	 * 关闭数据库连接
	 * @param pst
	 * @param conn
	 */
    public static void close() {  
        try {  
            pst.close(); 
            conn.close();
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
    }  
}
