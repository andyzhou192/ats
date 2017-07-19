package net.ats.webui.annotations;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
//import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import net.ats.webui.enums.Browser;
import net.ats.webui.util.PageUtil;
import net.common.util.FileUtil;
import net.common.util.LogUtil;

public class PageListener implements IInvokedMethodListener, ITestListener {
	private Class<?> cl = PageListener.class;

	@Override
	public void onTestStart(ITestResult result) {
		//System.out.println("onTestStart"); 
		Page pageAnnotation = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Page.class);
        if(null != pageAnnotation){
        	String pages = pageAnnotation.pages();
        	Browser browser = pageAnnotation.browser();
        	int timeOut = pageAnnotation.timeOut();
        	LogUtil.debug(cl, "page---->" + pages);
        	LogUtil.debug(cl, "browser---->" + browser.getName());
        	LogUtil.debug(cl, "timeout---->" + timeOut);
        	PageUtil.setPageNames(pages.split(","));
        	PageUtil.setDriver(browser.getDriver());
        	PageUtil.setTimeOut(timeOut);
        } else {
        	LogUtil.err(cl, "The test method is missing the Page annotation.");
        }
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		//PageUtil.Driver = Browser.getDefaultDriver();
	}

	@Override
	public void onTestFailure(ITestResult result) {
		//PageUtil.Driver = Browser.getDefaultDriver();
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		//PageUtil.Driver = Browser.getDefaultDriver();
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		//PageUtil.Driver = Browser.getDefaultDriver();
	}

	@Override
	public void onStart(ITestContext context) {
		//PageUtil.setPagePropsMap(FileUtil.traverseFolder(Class.class.getClass().getResource("/").getPath() + "pages/"));
		PageUtil.setPagePropsMap(FileUtil.traverseFolder(System.getProperty("webui.page.dir")));
//		System.out.println("onStart"); 
//        for(ITestNGMethod m1 : context.getAllTestMethods()) { 
//            if(m1.getConstructorOrMethod().getMethod().isAnnotationPresent(Page.class)) { 
//                String page = m1.getConstructorOrMethod().getMethod().getAnnotation(Page.class).page();
//                LogUtil.debug(getClass(), "onStart_page:"+page);
//            } 
//        } 
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		// TODO Auto-generated method stub
		
	}

}
