package example.webui;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import net.ats.webui.enums.ByTypes;
import net.ats.webui.util.PageUtil;

@Listeners(net.ats.webui.annotations.PageListener.class)
public class BaseTestCase {
	
	@BeforeSuite
	public void setUp(){
		System.setProperty("webui.page.dir", Class.class.getClass().getResource("/").getPath() + "webui/pages/");
	}
	
	@AfterSuite
	public void tearDown(){
		
	}
	
	/**
	 * 删除
	 * 
	 */
	public void delete(){
		PageUtil.click(ByTypes.XPATH, "DELETE_BTN");
		PageUtil.webWait();
		PageUtil.click(ByTypes.XPATH, "CONDELETE_BTN");
		PageUtil.webWait();
	}
	
	public void openAfterLogin(String urlName){
		login();
		PageUtil.nextPage();
		PageUtil.to(PageUtil.getProp(urlName));
	}
	
	public void login(){
		String username = PageUtil.getProp("username");
		String pwd = PageUtil.getProp("password");
		String role = PageUtil.getProp("role");
		PageUtil.open(PageUtil.getProp("url"));
		PageUtil.input("username_Input", username);
		PageUtil.input("password_Input", pwd);
		PageUtil.selectByText("role_Select", role);
		PageUtil.click("login_Btn");
		PageUtil.webWait();
	}

}
