package net.common.ssh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import net.common.util.PropertiesUtil;

/**
 * SSH工具类
 * @author zhouyelin
 * 2016-4-7
 */
public class SSHHelper {
	private static Session session = null;
	private static ChannelExec openChannel = null;
	
	/**
	 * 通过ssh连接远程服务器
	 * @param host  主机名
	 * @param port	端口
	 * @param user	用户名
	 * @param pwd	密码
	 * @return
	 */
	public static void init(String host, int port, String user, String pwd){
		JSch jsch=new JSch();
		try {
			session = jsch.getSession(user, host, port);
			session.setPassword(pwd);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
		} catch (JSchException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 默认初始化，需要在conf.properties中配置好跳板机
	 */
	public static void init(){
		String host = PropertiesUtil.getConfigProperty("stepping_stones_host"); 
		int port = Integer.valueOf(PropertiesUtil.getConfigProperty("stepping_stones_port")); 
		String user = PropertiesUtil.getConfigProperty("stepping_stones_user"); 
		String pwd = PropertiesUtil.getConfigProperty("stepping_stones_pwd");
		init(host, port, user, pwd);
	}
	
	/**
	 * 配置端口转发
	 * @param bind_address 需要绑定的客户端地址
	 * @param lport 需要绑定的客户端端口
	 * @param rhost 目标服务器地址
	 * @param rport 目标服务器端口
	 * @return
	 */
	public static int setPortForward(String bind_address, int lport, String rhost, int rport){
		try {
			return session.setPortForwardingL(bind_address, lport, rhost, rport); //端口映射 转发
		} catch (JSchException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * 远程 执行命令并返回结果调用过程 是同步的（执行完才会返回）
	 * @param command	命令
	 * @return
	 */
	public static String exec(String command){
		String result = "";
		try {
			openChannel = (ChannelExec) session.openChannel("exec");
			openChannel.setCommand(command);
			int exitStatus = openChannel.getExitStatus();
			System.out.println(exitStatus);
			openChannel.connect();  
            InputStream in = openChannel.getInputStream();  
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));  
            String buf = null;
            while ((buf = reader.readLine()) != null) {
            	result+= new String(buf.getBytes("gbk"),"UTF-8") + "\r\n";  
            }  
		} catch (JSchException | IOException e) {
			result += e.getMessage();
		}
		return result;
	}
	
	/**
	 * 异步执行shell命令
	 * @param command
	 */
	public static void asynExec(final String command){
		Thread t=new Thread(){
		    public void run(){
		    	// 执行命令
		    	exec(command);
		   }
		};
		t.start();
	}
	
	/**
	 * 关闭连接
	 * @param session
	 * @param host
	 * @param port
	 */
	public static void close(){
		if(openChannel != null && !openChannel.isClosed()){
			openChannel.disconnect();
		}
		if(session != null && session.isConnected()){
			session.disconnect();
		}
	}
	
	
//	public static void main(String args[]){
//		String host = "10.10.10.10";
//		int port = 22;
//		String user = "abc";
//		String pwd = "123";
//		init(host, port, user, pwd);
//		
//		String command1 = "pwd;ssh abc@10.10.10.10";
//		String command2 = "abcpwd;";
//		String exec = exec(command1);
//		System.out.println("exec:" + exec);	
//		String exec2 = exec(command2);
//		System.out.println("exec2:" + exec2);
//		
//		close();
//	}
}
