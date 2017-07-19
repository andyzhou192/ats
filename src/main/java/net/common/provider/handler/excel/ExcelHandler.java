package net.common.provider.handler.excel;

import java.util.List;

public interface ExcelHandler {
	
	/**
	 * read excel row by row into list
	 * @param file
	 * @param targetSheet
	 * @return
	 */
	public List<String[]> readExcel(String file, Object targetSheet);
	
	
}
