package com.homer.resuablecomponents;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.firefox.MarionetteDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.homer.enums.EnumClass.Browser;
import com.homer.helper.HelperClass;
import com.homer.logger.HomerLogger;

public class DriverFactory {
	
	public WebDriver getLocalWebDriver(Browser browser) throws Exception {
		
		WebDriver driver = null;
		
		switch (browser) {		
		
		case FireFox:
			driver = getFireFoxDriver();
			break;
		case Chrome:
			driver = getChromeDriver();
			break;
		case IE:
			driver = getIEDriver();
			break;			
		case Edge:
			driver = getEdgeDriver();
			break;			
		case Safari:
			driver = getSafariDriver();
			break;
		case HtmlUnit:
			driver = getHTMLUnitDriver();
			break;	
		case Emulator:
			driver = getChromeEmulator();
			break;
		case Appium:
			driver = getAppiumDriver();
			break;
		default:
			break;
		}
		return driver;
	}

	public WebDriver getSauceLabDriver(String testCaseName) throws Exception {
		
		WebDriver driver = null;		
		
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		
		desiredCapabilities.setCapability("name",testCaseName);
		
		setCapabilitiesUsingSaucelabCap(desiredCapabilities);
		
	    URL hubUrl = null;  			
  		hubUrl = new URL(HelperClass.baseModel.distributedUrl);
  		
  		driver = new RemoteWebDriver(hubUrl,desiredCapabilities);  
       
       return (WebDriver)driver;
	}
	
	public WebDriver getSauceLabDriverRunManager(String testCaseName, String browserName, String version, String platform) throws Exception {
		
		WebDriver driver = null;		
		
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		
		desiredCapabilities.setCapability("name",testCaseName);
		desiredCapabilities.setCapability("browserName",browserName);
		desiredCapabilities.setCapability("version",version);
		desiredCapabilities.setCapability("platform",platform);
		
	    URL hubUrl = null;  			
  		hubUrl = new URL(HelperClass.baseModel.distributedUrl);
  		
        driver = new RemoteWebDriver(hubUrl,desiredCapabilities);
	
       
        return (WebDriver)driver;
	}
	
	
	
    public WebDriver getGridDriver(String testCaseName) throws Exception {
		
		WebDriver driver = null;	
		
		
		String browserName=HelperClass.readRunConfigProperties("capabilities.browserName");		
		
		
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		
		desiredCapabilities.setCapability("name",testCaseName);
		
		
		
		if(browserName.toLowerCase().contains("chrome")){
			
			desiredCapabilities.setBrowserName("chrome");
			
		} else if(browserName.equalsIgnoreCase("FF") || browserName.equalsIgnoreCase("Firefox")){
			
			desiredCapabilities.setBrowserName("firefox");
			
		} else if(browserName.equalsIgnoreCase("IE") || browserName.equalsIgnoreCase("Internet Explorer")) {
			
			desiredCapabilities.setBrowserName("internet explorer");
			desiredCapabilities.setCapability("ensureCleanSession", true);
			desiredCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			
		} 
		
		System.out.println("getBrowserName: "+desiredCapabilities.getBrowserName());
	    URL hubUrl = null;  			
  		hubUrl = new URL(HelperClass.baseModel.distributedUrl);
  		System.out.println("hubUrl "+hubUrl);  		
  		driver = new RemoteWebDriver(hubUrl,desiredCapabilities);  
  		System.out.println("driver"+driver.getCurrentUrl());
       
       return (WebDriver)driver;
	}
	
	public WebDriver getGridDriverRunManager(String testCaseName, String browserName, String version, String platform) throws Exception {
		
		WebDriver driver = null;		
		
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		
		desiredCapabilities.setCapability("name",testCaseName);
		
        if(browserName.toLowerCase().contains("chrome")){
			
			desiredCapabilities.setBrowserName("chrome");
			
		}else if(browserName.equalsIgnoreCase("FF") || browserName.equalsIgnoreCase("Firefox")){
			
			desiredCapabilities.setBrowserName("firefox");		
			driver = new FirefoxDriver();
			
		}else if(browserName.equalsIgnoreCase("IE") || browserName.equalsIgnoreCase("Internet Explorer")) {
			
			desiredCapabilities.setBrowserName("internet explorer");
			desiredCapabilities.setCapability("ensureCleanSession", true);
			desiredCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		}	
		
		
		/*desiredCapabilities.setCapability("version",version);
		desiredCapabilities.setCapability("platform",platform);*/
		
	    URL hubUrl = null;  			
  		hubUrl = new URL(HelperClass.baseModel.distributedUrl);
  		
        driver = new RemoteWebDriver(hubUrl,desiredCapabilities);
	
       
        return (WebDriver)driver;
	}
	
	
	private void setCapabilitiesUsingSaucelabCap(
			DesiredCapabilities desiredCapabilities) {
		
for (Entry<String, String> entry : HelperClass.baseModel.sauceLabCapabilities.entrySet()) {
			
			desiredCapabilities.setCapability(entry.getKey(), entry.getValue());
        }
		
		/**** If device name is Emulation********/
		
		if(!HelperClass.readRunConfigProperties("Channel").equalsIgnoreCase("Desktop") && Boolean.valueOf(HelperClass.baseModel.chromeEmulation)){
			
			Map<String, String> mobileEmulation = new HashMap<String, String>();
			//mobileEmulation.put("deviceName", "Apple iPad");
			mobileEmulation.put("deviceName", HelperClass.baseModel.chromeEmulationDevice);
			//mobileEmulation.put("deviceName", "Apple iPhone 4");
			Map<String, Object> chromeOptions = new HashMap<String, Object>();
			chromeOptions.put("mobileEmulation", mobileEmulation);
			desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);		
			
		}
		
	}	
	
/*	private WebDriver getFireFoxDriver() throws Exception{

		WebDriver driver = null;
		
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);

		return driver;
	}*/
	
	private WebDriver getFireFoxDriver() throws Exception{

		//DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

		String geckoDriverPath = HelperClass.baseModel.driversPath + "/geckodriver.exe"; //New
		System.out.println("geckoDriverPath: "+geckoDriverPath);
		//System.setProperty(GeckoDriverService.GECKO_DRIVER_EXE_PROPERTY, geckoDriverPath); //New		
		//desiredCapabilities.setCapability(GeckoDriverService.GECKO_DRIVER_EXE_PROPERTY, geckoDriverPath);//New	
		WebDriver driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);

		return driver;
	}
	
	//Use below incase of issues.
	/*private WebDriver getFireFoxDriver() {

		WebDriver driver = null;
        
        try {
               
         String marionetteDriverLocation = HelperClass.baseModel.driversPath + "/geckodriver.exe";
         System.setProperty("webdriver.gecko.driver", marionetteDriverLocation);
         
         DesiredCapabilities desiredCapabilities=DesiredCapabilities.firefox();
         desiredCapabilities.setCapability("marionette", true);
         
         driver = new FirefoxDriver(desiredCapabilities);
         driver.manage().window().maximize();
         driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
               
        } catch(Exception ex) {
               System.out.println(ex.getMessage());
        }
        
        return driver;

	}*/
	
	private WebDriver getChromeEmulator() {
		
		WebDriver driver = null;
		
		String chromeDriverPath =HelperClass.baseModel.driversPath + "/chromedriver.exe";
		System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY,chromeDriverPath);
		
		DesiredCapabilities capability = DesiredCapabilities.chrome();
		Map<String, String> mobileEmulation = new HashMap<String, String>(1);
		mobileEmulation.put("deviceName",HelperClass.baseModel.chromeEmulationDevice);

		Map<String, Object> chromeOptions = new HashMap<String, Object>();
		chromeOptions.put("mobileEmulation", mobileEmulation);
		
		capability.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
		try {
		
			driver  = new ChromeDriver(capability);
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
		}		
		
		return driver;
	}
	
	private WebDriver getAppiumDriver() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private WebDriver getChromeDriver() throws Exception {

		WebDriver driver=null;
		String chromeDriverPath = null;
		if(System.getProperty("os.name").equals("Mac OS X")){
			chromeDriverPath = HelperClass.baseModel.driversPath + "/chromedriver";
		}else{
			chromeDriverPath = HelperClass.baseModel.driversPath + "/chromedriver.exe";
		}

		System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY,chromeDriverPath);
		/*
		if (HelperClass.baseModel.chromeEmulation){

			Map<String, String> mobileEmulation = new HashMap<String, String>();
			mobileEmulation.put("deviceName", HelperClass.baseModel.chromeEmulationDevice);

			Map<String, Object> chromeOptions = new HashMap<String, Object>();
			chromeOptions.put("mobileEmulation", mobileEmulation);
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability("platform", HelperClass.baseModel.platForm);
			capabilities.setCapability("autoAcceptAlerts", "ACCEPT");
		
			capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
			 driver = new ChromeDriver(capabilities);
			 driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		} else {			 
			
			driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}*/
		
		/*ChromeOptions options= new ChromeOptions();		
		options.addArguments("test-type");
		options.addArguments("disable-popup-blocking");
		DesiredCapabilities capabilities= DesiredCapabilities.chrome();		
		capabilities.setCapability(ChromeOptions.CAPABILITY,options);*/
		
		
		

		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		return driver;
	}
	
	private WebDriver getEdgeDriver() throws Exception {

		WebDriver driver=null;
		String edgeDriverPath = null;
		
		edgeDriverPath = HelperClass.baseModel.driversPath + "/MicrosoftWebDriver.exe";
		System.setProperty(EdgeDriverService.EDGE_DRIVER_EXE_PROPERTY,edgeDriverPath);	
		
		driver = new EdgeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		return driver;
	}
	
	
	private WebDriver getIEDriver() throws Exception {

		WebDriver driver = null;			
		String ieDriverPath = HelperClass.baseModel.driversPath
				+ "/IEDriverServer.exe";
		System.setProperty(
				InternetExplorerDriverService.IE_DRIVER_EXE_PROPERTY,
				ieDriverPath);

		DesiredCapabilities capabilities = DesiredCapabilities
				.internetExplorer();
		capabilities
				.setCapability(
						InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
						true);
		capabilities.setCapability(
				CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION,
				true);

		driver = new InternetExplorerDriver(capabilities);
		driver.manage().deleteAllCookies();
		Runtime.getRuntime().exec(
				"RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 2");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		
		return driver;
	}
	
	private WebDriver getSafariDriver() throws Exception{

		WebDriver driver = null;			
		driver = new SafariDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		return driver;
	}

	private WebDriver getHTMLUnitDriver() throws Exception {

        HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.CHROME, true);
        //driver.setJavascriptEnabled(true);          
        Logger.getLogger("").setLevel(Level.OFF); 
        return driver;             
		
	}	
	
	public void quitDriver(WebDriver driver) throws Exception {
		driver.quit();
	}
	
}
