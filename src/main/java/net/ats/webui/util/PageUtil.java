package net.ats.webui.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import net.ats.webui.enums.ByTypes;
import net.common.util.LogUtil;
import net.common.util.PropertiesUtil;
import net.common.util.StringUtil;

public class PageUtil {
	private static Class<?> cl = PageUtil.class;

	private static WebDriver Driver;
	private static String CurrentPage;
	private static List<String> PageNames = new ArrayList<String>();

	private static int TimeOut;
	
	private static int pageIndex = 0;
	
	private static Map<String, Properties> PagePropsMap = new HashMap<String, Properties>();

	/**
	 * 
	 */
	public static void nextPage(){
		if(PageNames.size() > 0){
			if(pageIndex < PageNames.size() - 1){
				pageIndex++;
			} else {
				pageIndex = 0;
			}
			CurrentPage = PageNames.get(pageIndex);
		} else {
			LogUtil.err(cl, "page is null.");
		}
	}
	
	/**
	 * 
	 */
	public static void prePage(){
		if(PageNames.size() > 0){
			if(pageIndex > 0 && pageIndex < PageNames.size()){
				pageIndex--;
			} else {
				pageIndex = 0;
			}
			CurrentPage = PageNames.get(pageIndex);
		} else {
			LogUtil.err(cl, "page is null.");
		}
	}
	
	/**
	 * 
	 * @param proName
	 * @return
	 */
	public static String getProp(String proName) {
		String proValue = "";
		if(!PagePropsMap.isEmpty() && PagePropsMap.containsKey(PageUtil.getCurrentPage())){
			proValue = PagePropsMap.get(PageUtil.getCurrentPage()).getProperty(proName);
		} else {
			String pagePath = Object.class.getResource(PageUtil.getCurrentPage()).getFile();
			LogUtil.debug(cl, "pagePath:" + pagePath);
			Properties prop = PropertiesUtil.loadProperties(pagePath);
			PagePropsMap.put(PageUtil.getCurrentPage(), prop);
			proValue = prop.getProperty(proName);
		}
		return StringUtil.isNull(proValue) ? proValue : proValue.trim();
	}
	
	/************************************页面操作***********************************/
	/**
	 * open url
	 * @param url
	 */
	public static void open(String url){
		PageUtil.getDriver().get(url);
		PageUtil.getDriver().manage().window().maximize();
	}
	
	public static void to(String url){
		PageUtil.getDriver().navigate().to(url);
		PageUtil.getDriver().manage().window().maximize();
		PageUtil.webWait();
	}

	/**
	 * 获取当前浏览器打开页面的URL
	 * @return
	 */
	public static String getCurrentUrl(){
		return PageUtil.getDriver().getCurrentUrl();
	}
	
	/**
	 * close window
	 */
	public static void close(){
		PageUtil.getDriver().close();
	}
	
	/**
	 * 获取页面源码
	 * @return
	 */
	public static String getSource(){
		return PageUtil.getDriver().getPageSource();
	}
	
	/**
	 * 刷新后获取页面源码
	 * @return
	 */
	public static String getSourceAfterRefresh(){
		PageUtil.refresh();
		return PageUtil.getSource();
	}
	
	/**
	 * 判断页面中是否包含内容为txt的节点
	 * @param txt
	 * @return
	 */
	public static boolean containTextInPage(String txt){
		//PageUtil.refresh();
		try{
			PageUtil.findElement("//*[contains(string(),'" + txt + "')]");
			return true;
		} catch (NoSuchElementException e){
			return false;
		}
	}
	
	/**
	 * 刷新页面
	 */
	public static void refresh(){
		PageUtil.getDriver().navigate().refresh();
	}
	
	/**
	 * 后退
	 */
	public static void back(){
		PageUtil.getDriver().navigate().back();;
	}
	
	/**
	 * 前进
	 */
	public static void forward(){
		PageUtil.getDriver().navigate().forward();;
	}
	
	/**
	 * 
	 * @param selector
	 * @return
	 */
	public static WebElement findElement(String selector){
		WebElement ele = null;
		if(selector.startsWith(ByTypes.ID.getTag())){
			String[] locators = selector.replaceFirst(ByTypes.ID.getTag(), "").split(">>");
			for(int i = 0; i < locators.length; i++){
				ele = PageUtil.getDriver().findElement(By.id(locators[i]));
			}
		} else if(selector.startsWith(ByTypes.NAME.getTag())){
			String[] locators = selector.replaceFirst(ByTypes.NAME.getTag(), "").split(">>");
			for(int i = 0; i < locators.length; i++){
				ele = PageUtil.getDriver().findElement(By.name(locators[i]));
			}
		} else if(selector.startsWith(ByTypes.TAGNAME.getTag())){
			String[] locators = selector.replaceFirst(ByTypes.TAGNAME.getTag(), "").split(">>");
			for(int i = 0; i < locators.length; i++){
				ele = PageUtil.getDriver().findElement(By.tagName(locators[i]));
			}
		} else if(selector.startsWith(ByTypes.CLASSNAME.getTag())){
			String[] locators = selector.replaceFirst(ByTypes.CLASSNAME.getTag(), "").split(">>");
			for(int i = 0; i < locators.length; i++){
				ele = PageUtil.getDriver().findElement(By.className(locators[i]));
			}
		} else if(selector.startsWith(ByTypes.LINKTEXT.getTag())){
			String[] locators = selector.replaceFirst(ByTypes.LINKTEXT.getTag(), "").split(">>");
			for(int i = 0; i < locators.length; i++){
				ele = PageUtil.getDriver().findElement(By.linkText(locators[i]));
			}
		} else if(selector.startsWith(ByTypes.PARTIALLINKTEXT.getTag())){
			String[] locators = selector.replaceFirst(ByTypes.PARTIALLINKTEXT.getTag(), "").split(">>");
			for(int i = 0; i < locators.length; i++){
				ele = PageUtil.getDriver().findElement(By.partialLinkText(locators[i]));
			}
		} else if(selector.startsWith(ByTypes.CSSSELECTOR.getTag())){
			String[] locators = selector.replaceFirst(ByTypes.CSSSELECTOR.getTag(), "").split(">>");
			for(int i = 0; i < locators.length; i++){
				ele = PageUtil.getDriver().findElement(By.cssSelector(locators[i]));
			}
		} else if(selector.startsWith(ByTypes.XPATH.getTag())){
			String[] locators = selector.replaceFirst(ByTypes.XPATH.getTag(), "").split(">>");
			for(int i = 0; i < locators.length; i++){
				ele = PageUtil.getDriver().findElement(By.xpath(locators[i]));
			}
		} else {
			return PageUtil.getDriver().findElement(By.xpath(selector));
		}
		return ele;
	}
	
	/**
	 * 页面点击
	 * @param byType
	 * @param proName
	 * @param page
	 */
	public static void click(ByTypes byType, String proName){
		PageUtil.getDriver().findElement(getBy(byType, proName)).click();
		PageUtil.webWait();
	}
	
	/**
	 * 页面点击
	 * @param proName
	 */
	public static void click(String proName){
		PageUtil.findElement(PageUtil.getProp(proName)).click();
		PageUtil.webWait();
	}
	
	/**
	 * 页面输入
	 * @param by
	 * @param proName
	 * @param value
	 */
	public static void input(ByTypes byType, String proName, String value){
		PageUtil.getDriver().findElement(getBy(byType, proName)).clear();
		PageUtil.getDriver().findElement(getBy(byType, proName)).sendKeys(value);
		PageUtil.webWait();
	}
	
	/**
	 * 页面输入
	 * @param proName
	 * @param value
	 */
	public static void input(String proName, String value){
		String selector = PageUtil.getProp(proName);
		PageUtil.findElement(selector).clear();
		PageUtil.findElement(selector).sendKeys(value);
		PageUtil.webWait();
	}
	
	public static void inputWithEnter(String proName, String value){
		String selector = PageUtil.getProp(proName);
		PageUtil.findElement(selector).clear();
		PageUtil.findElement(selector).sendKeys(value);
		PageUtil.findElement(selector).sendKeys(Keys.RETURN);
		PageUtil.webWait();
	}
	
	/**
	 * 选择框根据页面文档选择
	 * @param by
	 * @param proName
	 * @param text
	 */
	public static void selectByText(ByTypes byType, String proName, String text){
		Select select = new Select(PageUtil.getDriver().findElement(getBy(byType, proName)));
		select.selectByVisibleText(text);
		//webWait();
	}
	
	/**
	 * 选择框根据页面文档选择
	 * @param proName
	 * @param text
	 */
	public static void selectByText(String proName, String text){
		Select select = new Select(PageUtil.findElement(PageUtil.getProp(proName)));
		select.selectByVisibleText(text);
		//webWait();
	}
	
	/**
	 * 页面获取文本
	 * @param byType
	 * @param proName
	 * @return
	 */
	public static String getText(ByTypes byType, String proName){
		try{
			return PageUtil.getDriver().findElement(getBy(byType, proName)).getText();
		}catch(Exception e){
			return "not find text:" + proName;
		}
	}
	
	/**
	 * 页面获取文本
	 * @param byType
	 * @param proName
	 * @return
	 */
	public static String getText(String proName){
		try{
			return PageUtil.findElement(PageUtil.getProp(proName)).getText();
		}catch(Exception e){
			return "not find text:" + proName;
		}
	}
	
	/**
	 * 页面元素清空
	 * @param byType
	 * @param proName
	 */
	public static void clear(ByTypes byType, String proName){
		PageUtil.getDriver().findElement(getBy(byType, proName)).clear();
		PageUtil.webWait();
	}
	
	/**
	 * 页面元素清空
	 * @param proName
	 */
	public static void clear(String proName){
		PageUtil.findElement(PageUtil.getProp(proName)).clear();
		PageUtil.webWait();
	}
	
	/**
	 * 判断是否可见1
	 * 
	 * @param byType
	 * @param proName
	 * @return 
	 */
	public static boolean isExist(ByTypes byType, String proName){
		try{
			PageUtil.getDriver().findElement(getBy(byType, proName));
			return true;
		}catch(org.openqa.selenium.NoSuchElementException ex){
			return false;
		}
	}	
	
	/**
	 * 判断是否可见1
	 * 
	 * @param proName
	 * @return 
	 */
	public static boolean isExist(String proName){
		try{
			PageUtil.findElement(PageUtil.getProp(proName));
			return true;
		}catch(org.openqa.selenium.NoSuchElementException ex){
			return false;
		}
	}	
	
	/**
	 * 页面等待
	 * 
	 */
	public static void webWait(){
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 类型By
	 * @param byType
	 * @param proName
	 * @param page
	 * @return
	 */
	public static By getBy(ByTypes byType, String proName){
		String selector = PageUtil.getProp(proName);
		By by = null;
		switch(byType.getType()){
		case 1:
			by = By.xpath(selector);
			break;
		case 2:
			by = By.id(selector);
			break;
		case 3:
			by = By.linkText(selector);
			break;
		case 4:
			by = By.partialLinkText(selector);
			break;
		case 5:
			by = By.name(selector);
			break;
		case 6:
			by = By.tagName(selector);
			break;
		case 7:
			by = By.className(selector);
			break;
		case 8:
			by = By.cssSelector(selector);
			break;
		default:
			by = By.xpath(selector);
			break;
		}
		return by;
	}
	
	/**
	 * 
	 * @return
	 */
	public static WebDriver getDriver() {
		Driver.manage().timeouts().implicitlyWait(PageUtil.getTimeOut(), TimeUnit.SECONDS);
		return Driver;
	}

	/**
	 * 
	 * @param driver
	 */
	public static void setDriver(WebDriver driver) {
		Driver = driver;
	}

	/**
	 * 
	 * @return
	 */
	public static String getCurrentPage() {
		if(null == CurrentPage && PageNames.size() > 0)
			CurrentPage = PageNames.get(0);
		return CurrentPage;
	}

	/**
	 * 
	 * @param currentPage
	 */
	public static void setCurrentPage(String currentPage) {
		CurrentPage = currentPage;
	}
	
	/**
	 * 
	 * @param pageIndex
	 */
	public static void setCurrentPage(int pageIndex) {
		if(PageNames.size() > 0 && -1 < pageIndex && pageIndex < PageNames.size()){
			CurrentPage = PageNames.get(pageIndex);
		} else {
			LogUtil.err(cl, "page is null.");
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public static List<String> getPageNames() {
		return PageNames;
	}

	/**
	 * 
	 * @param pageNames
	 */
	public static void setPageNames(String[] pageNames) {
		for(String pageName : pageNames)
			PageNames.add(pageName);
	}

	/**
	 * 
	 * @return
	 */
	public static int getTimeOut() {
		return TimeOut;
	}

	/**
	 * 
	 * @param timeOut
	 */
	public static void setTimeOut(int timeOut) {
		TimeOut = timeOut;
	}
	
	/**
	 * 
	 * @return
	 */
	public static Map<String, Properties> getPagePropsMap() {
		return PagePropsMap;
	}

	/**
	 * 
	 * @param pagePropsMap
	 */
	public static void setPagePropsMap(Map<String, Properties> pagePropsMap) {
		PagePropsMap = pagePropsMap;
	}
	
//	public static void main(String[] args) {
//		//System.out.println(PageUtil.getProperty("url", "LoginPage"));
//		System.out.println(Thread.currentThread().getContextClassLoader().getResource("").getPath());
//		System.out.println(Thread.currentThread().getContextClassLoader().getSystemResource("").getPath());
//		System.out.println(Class.class.getClass().getResource("/").getPath());
//	}

}
