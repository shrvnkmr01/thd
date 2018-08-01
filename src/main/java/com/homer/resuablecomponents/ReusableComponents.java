package com.homer.resuablecomponents;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.DOMException;

import us.monoid.json.JSONException;
import us.monoid.json.JSONObject;
import us.monoid.json.XML;

import com.homer.dao.CommonDataColumn;
import com.homer.dao.DataClass;
import com.homer.dao.DataColumn;
import com.homer.enums.EnumClass.StepResult;
import com.homer.exception.TCTerminationException;
import com.homer.helper.DataTable;
import com.homer.helper.HelperClass;
import com.homer.reports.Report;
import com.homer.util.DateUtil;

public class ReusableComponents {

	Report report;
	WebDriver driver;
	WebDriverHelper wh;
	DataTable dataHelper;
	public boolean terminateTestOnElementNotPresent = true;
	public HashMap<String, Object> dataStore = new HashMap<String, Object>();

	public DataClass data;

	public ReusableComponents(WebDriver driver, Report report,
			WebDriverHelper wh, DataTable dataHelper, DataClass data) {

		this.report = report;
		this.driver = driver;
		this.dataHelper = dataHelper;
		this.wh = wh;
		this.data = data;
	}

	public static String Latestfile() throws Exception {

		// TODO Auto-generated method stub

		String filepath = System.getProperty("user.home");

		File dir = new File(filepath + "\\Downloads");

		File[] files = dir.listFiles();

		File lastModifiedFile = files[0];

		for (int i = 1; i < files.length; i++) {

			if (lastModifiedFile.lastModified() < files[i].lastModified()) {

				lastModifiedFile = files[i];

			}

		}

		String fileName = lastModifiedFile.getName();

		return fileName;

	}

	public ReusableComponents(WebDriver driver, Report report,
			WebDriverHelper wh, DataTable dataHelper) {

		this.report = report;
		this.driver = driver;
		this.dataHelper = dataHelper;
		this.wh = wh;
	}

	public void fnHighlightMe(WebDriver driver, By by)
			throws InterruptedException {
		// Creating JavaScriptExecuter Interface
		JavascriptExecutor js = (JavascriptExecutor) driver;
		for (int iCnt = 0; iCnt < 4; iCnt++) {
			// Execute javascript
			js.executeScript("arguments[0].style.border='4px groove green'",
					driver.findElement(by));
			Thread.sleep(2000);
			js.executeScript("arguments[0].style.border=''",
					driver.findElement(by));
		}
	}

	public boolean buttonPresence(By btn) throws Exception {

		if (wh.isElementPresent(btn)) {

			return true;
		} else {

			return false;
		}
	}

	public boolean dropDown(By by, String Value) {
		WebElement element = driver.findElement(by);
		List<WebElement> lists = element.findElements(By.tagName("option"));

		ArrayList<String> Options = new ArrayList<String>();

		for (WebElement element1 : lists) {
			Options.add(element1.getText());

		}

		System.out.println("POGOptions: " + Options);

		if (Options.contains(Value)) {
			System.out.println("element value: " + Value + " is present");
			return true;

		} else {
			// SubCategory is not present
			System.out.println("element value: " + Value + " is not present");
			return false;
		}

	}

	/**
	 * Method to give Explicit wait
	 * 
	 * @throws Exception
	 */
	static final By userNameBox = By.name("j_username");
	static final By passwordBox = By.name("j_password");
	static final By manageAccess = By.id("manageAccessTabContainer");//
	static final By manageAccessTabChk = By
			.xpath("//*[@id='manageProjectFiltersAddPDA']");

	public void POGLogIn(String Username, String pwd, By sbmt) throws Exception {

		driver.manage().deleteAllCookies();
		// rc.openURL
		driver.get("https://webapps-qa.homedepot.com/Planogram/");
		Thread.sleep(2000);
		System.out.println("Data: " + Username);
		wh.sendKeys(userNameBox, Username);
		wh.sendKeys(passwordBox, pwd);
		wh.clickElement(sbmt);

	}

	/**
	 * Method to throw custom exception to terminate test case
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */

	public void preSetUp() throws Exception {

		driver.manage().deleteAllCookies();
		String cookieURL = "http://webapps-qa.homedepot.com/Planogram/mmszPlanogramCookieSetter.html";
		driver.get(cookieURL);
		driver.manage().window().maximize();
		// wh.driver.manage().timeouts().implicitlyWait(70, TimeUnit.SECONDS);
		wh.clickElement(By.xpath("/html/body/button[1]"));
		// wh.driver.manage().timeouts().implicitlyWait(70, TimeUnit.SECONDS);

	}

	public void waitExplicitly(By element) throws Exception {

		WebDriverWait wait = new WebDriverWait(driver, 90);
		wait.until(ExpectedConditions.visibilityOfElementLocated(element));
	}
	
	/**
	 * Method to throw custom exception to terminate test case
	 * 
	 * @throws Exception
	 */
	public void throwCustomException() throws Exception {

		throw new Exception("Custom Error");
	}

	/**
	 * Method to terminate test case.
	 * 
	 * @param pageName
	 * @throws Exception
	 */
	public void terminateTestCase(String pageName) throws Exception {

		if (terminateTestOnElementNotPresent) {

			report.addCustomErrorStep("Terminating test case",
					"Terminating test case as " + pageName
							+ " is not displayed");

			throwCustomException();
		}
	}

	/**
	 * Method to get analytic value
	 * 
	 * @param analyticsProperty
	 * @return
	 */
	public void openURL(String URL) throws Exception {

		try {
			driver.get(URL);

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			wh.handleAlert();
		}

		// driver.manage().window().maximize();
	}

	public String getAnalyticsValue(String analyticsProperty) {

		String analyticsObject = "_hddata";
		Object propertyValue;
		String jsProperty = analyticsObject + "." + analyticsProperty + ";";

		JavascriptExecutor js = (JavascriptExecutor) driver;
		propertyValue = js.executeScript("return " + jsProperty);

		// System.out.println("Property returned is : " + propertyValue);
		if (propertyValue != null)
			return propertyValue.toString().trim();
		else
			return "null";
	}

	/**
	 * Method to check if environment is Prod
	 * 
	 * @return true if Prod Environment else false
	 */
	public boolean isProdEnvironment() {

		if (driver.getCurrentUrl().contains("www.homedepot.com")
				|| dataHelper.getCommonData(CommonDataColumn.EnvironmentUrl)
						.contains("www.homedepot.com")
				|| HelperClass.baseModel.runEnvironment.equals("Prod")) {

			return true;

		} else {

			return false;
		}
	}

	public String getXMLResponse(String getEndPointURL) {

		String strResponse = null;
		try {

			URL obj = new URL(getEndPointURL);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();

			System.out.println("RESPONSE CODE : " + responseCode);
			BufferedReader in;
			if (responseCode != 200)
				in = new BufferedReader(new InputStreamReader(
						con.getErrorStream()));
			else
				in = new BufferedReader(new InputStreamReader(
						con.getInputStream()));

			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			System.out.println("RESPONSE: " + response);
			strResponse = response.toString();
			System.out.println("Json Response:" + strResponse);

			String xml = null;
			try {
				JSONObject json = new JSONObject(strResponse);
				xml = XML.toString(json);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("XML Response: " + xml);

		} catch (FileNotFoundException e) {
			System.out.println(e.getCause());
			System.out.println("EXCEPTION EXPLANATION:");
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (MalformedURLException e) {
			System.out.println(e.getCause());
			System.out.println("EXCEPTION EXPLANATION:");
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (ProtocolException e) {
			System.out.println(e.getCause());
			System.out.println("EXCEPTION EXPLANATION:");
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (DOMException e) {
			System.out.println(e.getCause());
			System.out.println("EXCEPTION EXPLANATION:");
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e.getCause());
			System.out.println("EXCEPTION EXPLANATION:");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return strResponse;
	}

	public void addReportStep(String description, String actualResult,
			StepResult stepResult) {

		boolean captureScreenShot = false;
		String fileName = "";

		if (stepResult == StepResult.PASS
				&& HelperClass.baseModel.runPassScreenshots) {

			captureScreenShot = true;

		} else if (HelperClass.baseModel.runFailScreenshots
				&& (stepResult == StepResult.FAIL
						|| stepResult == StepResult.FAIL_ENV
						|| stepResult == StepResult.FAIL_FEATURE || stepResult == StepResult.FAIL_TESTDATA)) {

			captureScreenShot = true;
		}

		if (captureScreenShot) {

			fileName = takeScreenShot();
			report.addReportLinkScreenshot(description, actualResult,
					stepResult, fileName);

		} else {

			report.addReportStepNoScreenshots(description, actualResult,
					stepResult);
		}
	}

	private String takeScreenShot() {

		String formattedDate = DateUtil.getFormattedDateTime();
		String screenShotFilePath = "";
		String testCaseName = data.testCaseName.replace(" ", "_");

		String stepDateForScreenShot;
		int maxTCNameLength;
		int maxScreenShotNameLength = 50;

		stepDateForScreenShot = "_Step-No_" + data.stepNo + "_" + formattedDate
				+ ".png";

		maxTCNameLength = maxScreenShotNameLength
				- stepDateForScreenShot.length();

		if (testCaseName.length() > maxTCNameLength) {

			testCaseName = testCaseName.substring(0, maxTCNameLength);
		}

		screenShotFilePath = "./ScreenShots/" + testCaseName
				+ stepDateForScreenShot;

		String screnShotSavePath = "";

		screnShotSavePath = data.screenShotFolder + "/" + testCaseName
				+ stepDateForScreenShot;

		saveScreenShot(screnShotSavePath);

		return screenShotFilePath;
	}

	private void saveScreenShot(String screnShotSavePath) {

		try {

			BufferedImage image = new Robot()
					.createScreenCapture(new Rectangle(Toolkit
							.getDefaultToolkit().getScreenSize()));
			ImageIO.write(image, "png", new File(screnShotSavePath));
		} catch (Exception e) {
			System.out.println("Unable to capture Screenshot--" + e);
		}
	}
	public static String file() throws Exception {
		// TODO Auto-generated method stub

		String filepath = System.getProperty("user.home");
		File dir = new File(filepath + "\\Downloads");

		File[] files = dir.listFiles();

		File lastModifiedFile = files[0];
		for (int i = 1; i < files.length; i++) {
			if (lastModifiedFile.lastModified() < files[i].lastModified()) {
				lastModifiedFile = files[i];
			}
		}

		String fileName = lastModifiedFile.getName();
		return fileName;

	}

	
	


	/**
	 * Method to Check whether button is enable or disable
	 * 
	 * @throws Exception
	 */

	

	public boolean buttonStatus(By btn) throws Exception {

		if (driver.findElement(btn).isEnabled()) {

			return true;
		} else {

			return false;
		}
	}


	public void LogIn(String Username, String pwd, By sbmt) throws Exception {

		System.out.println("Data: " + Username);
		wh.sendKeys(userNameBox, Username);
		wh.sendKeys(passwordBox, pwd);
		wh.clickElement(sbmt);

	}



	/**
	 * Method to throw custom exception to terminate test case
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */

	public void manageAccess() throws Exception {

		// wh.driver.manage().timeouts().implicitlyWait(70, TimeUnit.SECONDS);
		wh.waitForElementPresent(manageAccess);
		wh.clickElement(manageAccess);
		Thread.sleep(3000);

	}

	
	
	

	
	
	


	/**
	 * Method to Inject AJAX Script in WebDriver.
	 * 
	 * @param endPointURL
	 * @param JSONRequest
	 * @param requestType
	 *            (POST/GET,etc.,)
	 * @param destinationPageURL
	 * @return status
	 */
	public String executeAJAXScript(String endPointURL, String JSONRequest,
			String requestType, String destinationPageURL) {

		Object status = null;

		// Setting java script executor time out
		driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);

		// Building AJAX Script based on endPointURL,JSONRequest and RequestType
		// - can be customized based on needs
		String script = "var callback = arguments[arguments.length - 1];"
				+ "$.ajax({" + "url:\'" + endPointURL + "\'," + "type: \'"
				+ requestType + "\'," + "dataType:'json',"
				+ "contentType:'application/json; charset=UTF-8'," + "data:"
				+ JSONRequest + "," + "xhrFields: {" + "withCredentials: true"
				+ "}," + "success: function(data){" + "callback(data);" + "},"
				+ "error: function(data) {" + "callback(data);" + "},"
				+ "dataType: 'json'" + "});";

		try {
			// Execute asynchronous JavaScript
			status = ((JavascriptExecutor) driver).executeAsyncScript(script);

			Thread.sleep(3000);

			// Get the Destination Page URL
			driver.get(destinationPageURL);

			wh.waitForPageLoaded();
		} catch (Exception ex) {
			report.addReportStep("Execute Ajax script",
					"Exeception occured while executing script",
					StepResult.FAIL);
		}

		// Return the Response from the API. null will be returned if any
		// exceptions.
		return status.toString();

	}

	public void terminateTestCase(String pageName, StepResult stepResult)
			throws Exception, TCTerminationException {

		if (terminateTestOnElementNotPresent) {

			report.addCustomErrorStep("Terminating test case",
					"Terminating test case as " + pageName
							+ " is not displayed", stepResult);

			throwTCTerminationException();

		}
	}

	/**
	 * Method to throw custom exception to terminate test case
	 * 
	 * @throws Exception
	 * @throws TCTerminationException
	 */
	public void throwTCTerminationException() throws TCTerminationException {

		throw new TCTerminationException("TC Termination Exception");
	}

	
}
