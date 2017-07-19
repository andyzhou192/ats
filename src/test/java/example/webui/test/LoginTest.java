package example.webui.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import example.webui.BaseTestCase;
import net.ats.webui.annotations.Page;
import net.ats.webui.enums.Browser;
import net.ats.webui.util.PageUtil;
import net.common.provider.DataProviderClass;

public class LoginTest extends BaseTestCase {

	@Test(dataProvider="defaultMethod",dataProviderClass=DataProviderClass.class, groups = "add")
    @Page(pages="LoginPage", browser=Browser.Chrome, timeOut=30)
	public void testLogin(String caseId, String desc, String userName, String password, String role, String expected){
		try{
			login();
			Assert.assertEquals(PageUtil.getCurrentUrl(), expected, "login is failed.");
		} catch(Exception e){
			Assert.fail(e.toString());
		}finally {
			PageUtil.close();
		}
	}

}
