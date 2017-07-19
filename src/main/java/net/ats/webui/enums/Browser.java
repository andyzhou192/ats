package net.ats.webui.enums;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import net.common.util.LogUtil;
import net.common.util.PropertiesUtil;

public enum Browser {
	IE("IE"),
	FireFox("FireFox"),
	Chrome("Chrome");
	
	private String name;
	
	Browser(String name){
		this.setName(name);
	}

	public WebDriver getDriver() {
		WebDriver driver = null;
		String driverDir = System.getProperty("user.dir") + File.separator + "ext" + File.separator + "drivers" + File.separator;
		switch (this.getName().toLowerCase()) {
		case "ie":
			System.setProperty("webdriver.ie.driver", driverDir + "IEDriverServer.exe");
			// 创建一个WebDriver实例
			DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
			ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
			driver = new InternetExplorerDriver(ieCapabilities);
			//driver = new InternetExplorerDriver();
			break;
		case "firefox":
			System.setProperty("webdriver.gecko.driver", driverDir + "geckodriver.exe");
			driver = new FirefoxDriver();
			break;
		case "chrome":
		default:
			System.setProperty ("webdriver.chrome.driver", driverDir + "chromedriver.exe");
			driver = new ChromeDriver();
			break;
		}
		//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		LogUtil.debug(Browser.class, "driver:" + driver);
		return driver;
	}
	
	public static WebDriver getDefaultDriver(){
		WebDriver defaultDriver;
		switch(PropertiesUtil.getConfigProperty("browser")){
		case "ie":
			defaultDriver = Browser.IE.getDriver();
			break;
		case "firefox":
			defaultDriver = Browser.FireFox.getDriver();
			break;
		case "chrome":
		default:
			defaultDriver = Browser.Chrome.getDriver();
			break;
		}
		return defaultDriver;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
//	public static void main(String[] args) {
//		System.out.println(System.getProperty("user.dir"));
//	}
}
