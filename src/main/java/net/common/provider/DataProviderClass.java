package net.common.provider;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.DataProvider;

import net.common.provider.handler.excel.ExcelHandler;
import net.common.provider.handler.excel.ExcelHandlerImpl;
import net.common.util.FileUtil;
import net.common.util.LogUtil;
import net.common.util.PropertiesUtil;

/**
 * Data Driver
 * 
 *
 */
public class DataProviderClass {
	private static Class<?> cl = DataProviderClass.class;
	private static ExcelHandler excHandler = new ExcelHandlerImpl();
	
	private static List<String> titles = new ArrayList<String>();
	
	public static List<String> getTitles() {
		return titles;
	}

	@DataProvider
	public static Object[][] defaultMethod(Method method) {
		if(method.getParameterCount() <= 0) return new String[][]{{}};
		LogUtil.debug(cl, "====" + method.getDeclaringClass().getName() + "---" + method.getName());
		String prePath = Thread.currentThread().getContextClassLoader().getResource("").getPath(); 
		String file = prePath + method.getDeclaringClass().getName().replace(".", File.separator);
		if(FileUtil.fileIsExists(file + ".xls")){
			file = file + ".xls";
		} else if(FileUtil.fileIsExists(file + ".xlsx")){
			file = file + ".xlsx";
		} else {
			LogUtil.err(cl, file + ".xls or " + file + ".xlsx is not exist.");
			return null;
		}
		LogUtil.debug(cl, "-----" + file);
		List<String[]> paramList = excHandler.readExcel(file, Integer.parseInt(PropertiesUtil.getConfigProperty("SHEET")));
		
		for(String str : paramList.get(0)){
			titles.add(str);
		}
		String[][] params = new String[paramList.size()-1][];
		for(int i = 0; i < paramList.size()-1; i++){
			params[i] = paramList.get(i+1);
		}
		return params;
	}

}
