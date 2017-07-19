package net.common.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 
 * @author zhouyelin@chinamobile.com
 *
 */
public class PropertiesUtil {

	private static Class<?> cl = PropertiesUtil.class;

	/**
	 * 根据key获取conf.properties配置文件中的值
	 * 
	 * @param key
	 * @return
	 */
	public static String getConfigProperty(String key) {
		Properties confProperties = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = cl.getClassLoader().getResourceAsStream("conf.properties");
			confProperties.load(inputStream);
		} catch (Exception ex) {
			LogUtil.err(cl, "File conf.properties is not exist or File setting.properties is empty!");
		} finally{
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String value = confProperties.getProperty(key);
		if (null == value)
			LogUtil.warn(cl, "conf.properties文件中没有配置" + key);
		return value;
	}

	/**
	 * 加载properties文件
	 * @param file
	 * @return
	 */
	public static Properties loadProperties(String file){
		Properties prop = new Properties();
		InputStream in = null;
		InputStreamReader inr = null;
		try {
			in = new BufferedInputStream(new FileInputStream(file));
			inr = new InputStreamReader(in, "UTF-8");// 解决读取的内容乱码问题
			prop.load(inr);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					inr.close();
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop;
	}
}
