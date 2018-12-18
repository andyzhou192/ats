package net.common.provider.handler.excel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.Sheet;  
import org.apache.poi.ss.usermodel.Workbook;  
import org.apache.poi.ss.usermodel.WorkbookFactory;

import net.common.util.LogUtil;  

/**
 * excel
 * @author sifuma@163.com
 *
 */
public class ExcelHandlerImpl implements ExcelHandler {

	/**
	 * read excel
	 * @param file
	 * @param targetSheet
	 * @return
	 */
	public List<String[]> readExcel(String file, Object targetSheet){
		//String prePath = Thread.currentThread().getContextClassLoader().getResource("").getPath(); 
		List<String[]> rowList = new ArrayList<String[]>();
        try {
            InputStream mInputStream = new FileInputStream(file);
            Workbook wb = WorkbookFactory.create(mInputStream);
			
            Sheet mSheet = null;
            if(null == targetSheet)
            	targetSheet = 0;
            if(targetSheet instanceof Integer){
            	mSheet = wb.getSheetAt((Integer) targetSheet);
            }else{
            	mSheet = wb.getSheet((String) targetSheet);
            }
            int rowCou = mSheet.getPhysicalNumberOfRows();
            int colCou = mSheet.getRow(0).getPhysicalNumberOfCells();
            LogUtil.debug(ExcelHandlerImpl.class, "Total Row: " + rowCou + ", Total Columns: " + colCou);
            for(int rowIndex= 0 ; rowIndex < rowCou ; rowIndex ++){
            	String[] colArray = new String[colCou];
                for(int colIndex = 0 ; colIndex < colCou ; colIndex ++){
                	Cell temp = mSheet.getRow(rowIndex).getCell(colIndex);
                    String content = null == temp ? null : temp.getStringCellValue();
                    LogUtil.debug(ExcelHandlerImpl.class, "rowIndex:" + rowIndex + ", colIndex:" + colIndex + ", content:" + content);
                    colArray[colIndex] = content;
                }
                rowList.add(colArray);
            }
            wb.close();
            mInputStream.close();
        } catch (EncryptedDocumentException e) {
        	e.printStackTrace();
        } catch (InvalidFormatException e) {
        	e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		return rowList;
    }
	
//	public static void main(String[] args) {
//		ExcelHandlerImpl handler = new ExcelHandlerImpl();
//		List<String[]> result1 = handler.readExcel("D:/workspace/RMS_webUI_Test/src/test/resources/net/andy/irms/webui/test/SecurityPolicyManageTest.xls",null);
//		System.out.println("result1:" + result1.get(0)[0] + "--->" + result1.get(0)[1]);
//		
//		List<String[]> result2 = handler.readExcel("D:/workspace/RMS_webUI_Test/src/test/resources/net/andy/irms/webui/test/SecurityPolicyManageTest.xlsx",null);
//		System.out.println("result2:" + result2.get(0)[0] + "--->" + result2.get(0)[1]);
//	}

}
