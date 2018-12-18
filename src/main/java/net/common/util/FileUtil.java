package net.common.util;



import java.io.BufferedReader;
import java.io.File;

import java.io.FileNotFoundException;

import java.io.FileReader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * 
 * @author sifuma@163.com
 *
 */

public class FileUtil {

	private static Class<?> cl = FileUtil.class;

	/**
	 * 判断文件是否存在
	 * @param path为绝对路径，如：/storage/sdcard0/Manual/test.pdf
	 * @return
	 */
	public static boolean fileIsExists(String path) {
		try {
			File f = new File(path);
			if (!f.exists()) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}


	/**
	 * 判断文件中是否存在指定的内容
	 * @param filePath  文件路径
	 * @param contents
	 * @return
	 */
	@SuppressWarnings("resource")
	public static boolean isContainSpecialText(String filePath, String...contents) {
		boolean isContain = false;
		FileReader fr;
		try {
			fr = new FileReader(filePath);
			BufferedReader bf = new BufferedReader(fr);
			String temp = "";
			while(temp != null){
				temp = bf.readLine();
				if(temp != null){
					for(String content:contents){
						isContain = temp.contains(content);
						if(!isContain) break;
					}
				}
				if(isContain) break;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return isContain;
	}
	
	/**
	 * 递归方式遍历文件夹下的所有文件
	 * @param path
	 */
	public static Map<String, Properties> traverseFolder(String path) {
		Map<String, Properties> resultMap = new HashMap<String, Properties>();
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
                LogUtil.debug(cl, "Folder is empty!");
                return resultMap;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                    	LogUtil.debug(cl, "Folder:" + file2.getAbsolutePath());
                        traverseFolder(file2.getAbsolutePath());
                    } else {
                    	LogUtil.debug(cl, "File:" + file2.getAbsolutePath());
                    	String fileName = file2.getName();
                    	String suffix = ".properties";
                    	if(null != fileName && fileName.trim().endsWith(suffix)){
                    		String key = fileName.substring(0, fileName.length() - suffix.length());
                    		Properties value = PropertiesUtil.loadProperties(file2.getAbsolutePath());
                    		resultMap.put(key, value);
                    	} else {
                    		continue;
                    	}
                    }
                }
            }
        } else {
            System.out.println("File is not exist!");
        }
		return resultMap;
    }

//	public static void main(String[] args) {
//		FileTools.traverseFolder(Class.class.getClass().getResource("/").getPath() + "pages/");
//	}
}
