package com.homer.resuablecomponents;

import java.awt.AWTException;
import java.awt.Robot;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.homer.dao.CommonDataColumn;
import com.homer.enums.EnumClass.Browser;
import com.homer.enums.EnumClass.StepResult;
import com.homer.helper.DataTable;
import com.homer.logger.HomerLogger;
import com.homer.reports.Report;

public class WebDriverHelper {

	Report report;
	DataTable dataHelper;

	int noWaitTime = 1;
	int waitTime = 30;

	public boolean continueOnClickFail = false;
	public Browser currentBrowser;
	public WebDriver driver;

	public WebDriverHelper(WebDriver driver, Report report,
			DataTable dataHelper, Browser currentBrowser) {

		this.driver = driver;
		this.report = report;
		this.dataHelper = dataHelper;
		this.currentBrowser = currentBrowser;

		if (currentBrowser == Browser.IE) {

			noWaitTime = 3;
			waitTime = 50;
		}
	}

	public WebDriverHelper(WebDriver driver, Report report,
			DataTable dataTable) {
		
		this.driver = driver;
		this.report = report;
		this.dataHelper = dataTable;
	}

	/**
	 * Method to check if element is present
	 * 
	 * @param elementBy
	 * @return
	 * @throws Exception 
	 */
	public boolean isElementPresent(By elementBy) throws Exception {

		return isElementPresent(elementBy,5);
	}
	
	/**
	 * Method to check if element is present after waiting for specific time
	 * 
	 * @param elementBy
	 * @return
	 */
	public boolean isElementPresentAfterWait(final By elementBy) {

		boolean elementPresent = false;

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(120, TimeUnit.SECONDS)
				.pollingEvery(1, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);

		try {

			wait.until(ExpectedConditions.visibilityOfElementLocated(elementBy));

			if (driver.findElement(elementBy).isDisplayed()) {

				elementPresent = true;
			}

		} catch (NoSuchElementException nsee) {

			System.out.println("No Such Element present exception occured");

		} catch (Exception e) {

			System.out.println("Generic Exception occured");
		}

		return elementPresent;
	}

	/**
	 * Method to check if element is present without wait
	 * 
	 * @param elementBy
	 * @return
	 * @throws Exception 
	 */
	public boolean noWaitElementPresent(By elementBy) throws Exception {

		return isElementPresent(elementBy,1);
	}

	/**
	 * Method to check if element is not present without wait
	 * 
	 * @param elementBy
	 * @return
	 */
	public boolean isElementNotPresent(By elementBy) {

		boolean elementNotPresent = true;

		try {

			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

			if (driver.findElement(elementBy).isDisplayed()) {

				return false;
				
			}
		} catch (NoSuchElementException nsee) {

			System.out.println("Element not found, NoSuchElementException caught");

		} catch (Exception se) {
			//se.printStackTrace();
			System.out.println("Element not found, generic exception caught");
		}

		driver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);

		return elementNotPresent;
	}

	/**
	 * Method to check element is present within parent element
	 * 
	 * @param element
	 * @param elementBy
	 * @return
	 */
	public boolean isElementPresent(WebElement element, By elementBy) {

		boolean elementPresent = false;

		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

		try {

			elementPresent = element.findElement(elementBy).isDisplayed();

		} catch (Exception ex) {

			System.out
					.println("Exception occured while finding element within element");
		}

		driver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);

		return elementPresent;
	}

	/**
	 * Method to click element
	 * 
	 * @param elementBy
	 * @throws Exception
	 */
	public void clickElement(By elementBy) throws Exception {

		boolean exceptionOccured = false;
		String beforeClickUrl = driver.getCurrentUrl();

		try {

			if (isElementPresent(elementBy, 2)) {

				driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

				try {

					try {

						driver.findElement(elementBy).click();

						if (beforeClickUrl.equalsIgnoreCase(driver
								.getCurrentUrl())
								&& currentBrowser == Browser.IE) {

							jsClick(elementBy);
						}

						/*
						 * if(currentBrowser == Browser.IE) {
						 * jsClick(elementBy); }
						 */

					} catch (NoSuchElementException nsee) {

						exceptionOccured = true;
						HomerLogger.getInstance().info(nsee.toString());
					}

				} catch (Exception ex) {

					exceptionOccured = true;
					HomerLogger.getInstance().info(ex.toString());
				}

				if (exceptionOccured) {

					try {

						jsClick(elementBy);
						validateUrl(elementBy);

					} catch (Exception ex) {

						HomerLogger.getInstance().info(ex.toString());
					}

				} else {

					validateUrl(elementBy);
				}

			} else {

				if (!continueOnClickFail) {

					//throw new Exception("Custom Error");
				}

			}

			driver.manage().timeouts()
					.implicitlyWait(waitTime, TimeUnit.SECONDS);

		} catch (Exception ex) {

			if (ex.getMessage().trim() == "Custom Error") {

				report.addCustomErrorStep(
						"Terminating test case as click element was not found",
						"Click Element: " + elementBy.toString()
								+ " was not found");

				ReusableComponents rc = new ReusableComponents(driver, report,
						this, dataHelper);
				rc.throwCustomException();
			}

			handleAlert();

			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		}
	}

	/**
	 * Method to click element
	 * 
	 * @param elementBy
	 * @throws Exception
	 */
	public void clickNoUrlValidation(By elementBy) throws Exception {

		boolean exceptionOccured = false;

		if (isElementPresent(elementBy)) {

			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

			try {

				try {

					driver.findElement(elementBy).click();

					/*
					 * if(currentBrowser == Browser.IE) { jsClick(elementBy); }
					 */

				} catch (NoSuchElementException nsee) {

					exceptionOccured = true;
					HomerLogger.getInstance().info(nsee.toString());

				}

			} catch (Exception ex) {

				exceptionOccured = true;
				HomerLogger.getInstance().info(ex.toString());

			}

			if (exceptionOccured) {

				try {

					jsClick(elementBy);

				} catch (Exception ex) {

					HomerLogger.getInstance().info(ex.toString());
				}
			}

		} else {

			if (!continueOnClickFail) {

				report.addCustomErrorStep(
						"Terminating test case as click element was not found",
						"Click Element: " + elementBy.toString()
								+ " was not found");
				throw new Exception("Custom Error");
			}

		}

		driver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);
	}

	/**
	 * Method to click element
	 * 
	 * @param element
	 * @throws Exception
	 */
	public void clickElement(WebElement element) throws Exception {

		boolean exceptionOccured = false;

		if (isElementPresent(element, 5)) {

			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

			try {
				try {

					element.click();

					/*
					 * if(currentBrowser == Browser.IE) { jsClick(elementBy); }
					 */

				} catch (NoSuchElementException nsee) {

					exceptionOccured = true;
					HomerLogger.getInstance().info(nsee.toString());
				}

			} catch (Exception ex) {

				exceptionOccured = true;
				HomerLogger.getInstance().info(ex.toString());
			}

			if (exceptionOccured) {

				try {

					jsClick(element);

				} catch (Exception ex) {

					HomerLogger.getInstance().info(ex.toString());
				}
			}

		} else {

			if (!continueOnClickFail) {

				report.addCustomErrorStep(
						"Terminating test case as click element was not found",
						"Click Element: " + element.toString()
								+ " was not found");
				throw new Exception("Custom Error");
			}

		}

		driver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);
	}

	/**
	 * Method to click using Actions
	 * 
	 * @param elementBy
	 */
	public void clickUsingActions(By elementBy) {

		try {

			if (isElementPresent(elementBy)) {

				Actions actions = new Actions(driver);
				actions.click(driver.findElement(elementBy));
			}

		} catch (Exception ex) {

			System.out.println("Exceptin occured whil clicking using Actions");
		}
	}

	/**
	 * Method to double click using actions
	 * 
	 * @param elementBy
	 */
	public void doubleClickUsingAction(By elementBy) {

		try {

			if (isElementPresent(elementBy)) {

				Actions actions = new Actions(driver);
				actions.doubleClick(driver.findElement(elementBy));
			}

		} catch (Exception ex) {

			System.out.println("Exceptin occured whil clicking using Actions");
		}
	}

	/**
	 * Method to check if alert box is present
	 * 
	 * @return
	 */
	public boolean isAlertPresent() {

		try {

			driver.switchTo().alert();

			return true;

		} catch (NoAlertPresentException Ex) {

			// For FF throwing exception
			return false;
		}
	}

	/**
	 * Method to click element within parent element
	 * 
	 * @param element
	 * @param elementBy
	 */
	public void clickElement(WebElement element, By elementBy) {

		if (isElementPresent(element, elementBy)) {

			element.findElement(elementBy).click();
		}
	}

	/**
	 * Method to mouse over element using javascript
	 * 
	 * @param element
	 */
	public void mouseOver(WebElement element) {

		String code = "var fireOnThis = arguments[0];"
				+ "var evObj = document.createEvent('MouseEvents');"
				+ "evObj.initEvent( 'mouseover', true, true );"
				+ "fireOnThis.dispatchEvent(evObj);";

		JavascriptExecutor executor = (JavascriptExecutor) driver;
		((RemoteWebDriver) executor).executeScript(code, element);
	}

	/**
	 * Method to mouse over element using javascript
	 * 
	 * @param elementBy
	 * @throws InterruptedException
	 */
	public void mouseOver(By elementBy) throws Exception {

		WebElement weElement = driver.findElement(elementBy);

		String code = "var fireOnThis = arguments[0];"
				+ "var evObj = document.createEvent('MouseEvents');"
				+ "evObj.initEvent( 'mouseover', true, true );"
				+ "fireOnThis.dispatchEvent(evObj);";

		if (isElementPresent(elementBy, 5)) {

			JavascriptExecutor executor = (JavascriptExecutor) driver;
			((RemoteWebDriver) executor).executeScript(code, weElement);
		}
	}

	/**
	 * Method to click element using javascript
	 * 
	 * @param elementBy
	 * @throws InterruptedException
	 */
	public void jsClick(By elementBy) throws Exception {

		WebElement weElement = driver.findElement(elementBy);

		JavascriptExecutor executor = (JavascriptExecutor) driver;
		((RemoteWebDriver) executor).executeScript("arguments[0].click();",
				weElement);
	}

	/**
	 * Method to click element using javascript
	 * 
	 * @param weElement
	 * @throws InterruptedException
	 */
	public void jsClick(WebElement weElement) throws Exception {

		JavascriptExecutor executor = (JavascriptExecutor) driver;
		((RemoteWebDriver) executor).executeScript("arguments[0].click();",
				weElement);
	}

	/**
	 * Method to click element using javascript
	 * 
	 * @param documentId
	 */
	public void jsClick(String documentId) {

		JavascriptExecutor executor = (JavascriptExecutor) driver;
		((RemoteWebDriver) executor)
				.executeScript("document.getElementById('hlViewStoreFinder').click();");

	}

	/**
	 * Method to validate url
	 * 
	 * @param elementBy
	 */
	public void validateUrl(By elementBy) {

		String envUrl = dataHelper
				.getCommonData(CommonDataColumn.EnvironmentUrl);

		if (!envUrl.contains("homedepot.com")) {

			String afterClickUrl = driver.getCurrentUrl();
			envUrl = envUrl.substring(envUrl.indexOf("//") + 2,
					envUrl.indexOf(".com"));

			if (afterClickUrl.contains("homedepotdev.com")
					&& !afterClickUrl.contains(envUrl)) {

				report.addReportStep(
						"Click on element - " + elementBy.toString(),
						"User is redirected to environment - " + afterClickUrl,
						StepResult.FAIL);
			}
		}
	}

	/**
	 * Method to focus element
	 * 
	 * @param elementBy
	 */
	public void focusElement(By elementBy) {

		try {

			jsFocus(driver.findElement(elementBy));

		} catch (Exception ex) {

			System.out
					.println("focusElement"
							+ ex.toString()
							+ " Exception occured while clicking element using driver focus method");
			HomerLogger.getInstance().info(ex.toString());
		}
	}

	/**
	 * Method to focus element using javascript
	 * 
	 * @param element
	 */
	private void jsFocus(WebElement element) {

		JavascriptExecutor executor = (JavascriptExecutor) driver;
		((RemoteWebDriver) executor).executeScript("arguments[0].click();",
				element);
	}

	/**
	 * Method to clear text element
	 * 
	 * @param elementBy
	 */
	public void clearElement(By elementBy) {

		driver.findElement(elementBy).sendKeys(Keys.HOME);
		driver.findElement(elementBy).sendKeys("");
		driver.findElement(elementBy).clear();
	}

	/**
	 * Method to navigate back
	 */
	public void navigateBack() {

		driver.navigate().back();
	}

	/**
	 * Method to select value for dropdown list
	 * 
	 * @param elementBy
	 * @param label
	 */
	public void selectValue(By elementBy, String label) {

		new Select(driver.findElement(elementBy)).selectByVisibleText(label);
		
	}

	/**
	 * Method to send keys to text element
	 * 
	 * @param elementBy
	 * @param typeValue
	 * @throws InterruptedException
	 */
	public void sendKeys(By elementBy, String typeValue) throws Exception {
			
		try {
			
			if(isElementPresent(elementBy, 1)) {
			
				driver.findElement(elementBy).clear();
				driver.findElement(elementBy).sendKeys(typeValue);
			}			
			
		} catch(Exception ex) {
			
			System.out.println(ex.getMessage());
		}
	}
	
	/**
	 * Method to send characters to text element
	 * 
	 * @param elementBy
	 * @param typeValue
	 * @throws InterruptedException
	 */
	public void sendChars(By elementBy, String typeChar) throws Exception {

		if (isElementPresent(elementBy)) {

			driver.findElement(elementBy).sendKeys(typeChar);
		}
	}	

	/**
	 * Method to get text of an element
	 * 
	 * @param elementBy
	 * @return
	 * @throws InterruptedException
	 */
	public String getText(By elementBy) throws Exception {

		if (noWaitElementPresent(elementBy)) {

			return driver.findElement(elementBy).getText().trim();

		} else {

			return "";
		}
	}

	/**
	 * Method to get Double value of an element
	 * 
	 * @param elementBy
	 * @return
	 * @throws InterruptedException
	 */
	public Double getDouble(By elementBy) throws Exception {

		Double retValue = 0.0;

		try {

			retValue = Double.valueOf(getText(elementBy));

		} catch (Exception ex) {

		}

		return retValue;
	}

	/**
	 * Method to get Double value of an element
	 * 
	 * @param str
	 * @return
	 * @throws InterruptedException
	 */
	public Double getDouble(String str) throws Exception {

		Double retValue = 0.0;

		try {

			retValue = Double.valueOf(str);

		} catch (Exception ex) {

		}

		return retValue;
	}

	/**
	 * Method to get Double value of an element
	 * 
	 * @param elementBy
	 * @return
	 * @throws InterruptedException
	 */
	public int getInteger(By elementBy) throws Exception {

		int retValue = 0;

		try {

			retValue = Integer.valueOf(getText(elementBy));

		} catch (Exception ex) {

		}

		return retValue;
	}

	/**
	 * Method to get text of an element within parent element
	 * 
	 * @param element
	 * @param elementBy
	 * @return
	 */
	public String getText(WebElement element, By elementBy) {

		if (isElementPresent(element, elementBy)) {

			return element.findElement(elementBy).getText().trim();

		} else {

			return "";
		}
	}

	/**
	 * Method to get text of an element within parent element
	 * 
	 * @param element
	 * @return
	 * @throws InterruptedException
	 */
	public String getText(WebElement element) throws Exception {

		if (isElementPresent(element, 5)) {

			return element.getText().trim();

		} else {

			return "";

		}
	}

	/**
	 * Method to handle alert box
	 */
	public void handleAlert() {

		try {

			Alert alert = driver.switchTo().alert();
			alert.accept();

		} catch (Exception ex) {

			System.out.println("Execption occured during alert handle");

		}
	}

	/**
	 * Method to wait for specific time till element is present
	 * 
	 * @param elementBy
	 * @throws Exception 
	 */
	public void waitForElementPresent(final By elementBy) throws Exception {

		isElementPresent(elementBy,5);
	}

	/**
	 * Method to wait for specific time till element is present
	 * 
	 * @param elementBy
	 * @param waitTimeSecs
	 * @throws InterruptedException
	 */
	public void waitForElementPresent(final By elementBy, int waitTimeSecs)
			throws Exception {

		isElementPresent(elementBy,waitTimeSecs);
	}

	
	/**
	 * Method to check if element is Present within some seconds
	 * 
	 * @param elementBy
	 * @param waitForSeconds
	 * @return
	 * @throws InterruptedException
	 */
	public boolean isElementPresent(By elementBy, int waitForSeconds)
			throws Exception {     
    	
    	boolean elementPresent = true;
    	int count = 0;
    	
    	driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    	
    	while(elementPresent){
    		try{
    			if (driver.findElement(elementBy).isDisplayed()){
    				break;
    			}else{
    				if(count==waitForSeconds){
        				elementPresent = false;
            			break;
            		}
    				
        			Thread.sleep(1000);
        			count++;
    			}
    		}catch(Exception ex){
    			if(count==waitForSeconds){
    				elementPresent = false;
        			break;
        		}
    			
    			Thread.sleep(1000);
    			count++;
    		}
    	}
    	    	
    	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    	return elementPresent;     
    }

	/**
	 * Method to check if element is Present with in an element for some seconds
	 * 
	 * @param element
	 * @param elementBy
	 * @param waitForSeconds
	 * @return
	 * @throws Exception
	 */
	public boolean isElementPresent(WebElement element, By elementBy,
			int waitForSeconds) throws Exception {

		boolean elementPresent = true;
		int count = 0;

		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

		while (elementPresent) {

			try {

				if (element.findElement(elementBy).isDisplayed()) {

					break;

				} else {

					if (count == waitForSeconds) {

						elementPresent = false;
						break;

					}

					Thread.sleep(1000);
					count++;

				}

			} catch (Exception ex) {

				if (count == waitForSeconds) {

					elementPresent = false;
					break;

				}

				Thread.sleep(1000);
				count++;
			}
		}

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		return elementPresent;
	}

	/**
	 * Method to check web element is present
	 * 
	 * @param element
	 * @param waitForSeconds
	 * @return
	 * @throws Exception
	 */
	public boolean isElementPresent(WebElement element, int waitForSeconds)
			throws Exception {

		boolean elementPresent = true;
		int count = 0;

		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

		while (elementPresent) {

			try {

				if (element.isDisplayed()) {

					break;

				} else {

					if (count == waitForSeconds) {

						elementPresent = false;
						break;
					}

					Thread.sleep(1000);
					count++;
				}

			} catch (Exception ex) {

				if (count == waitForSeconds) {

					elementPresent = false;
					break;
				}

				Thread.sleep(1000);
				count++;
			}
		}

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		return elementPresent;
	}

	/**
	 * Method to wait for page to get loaded
	 */
	public void waitForPageLoaded() {

		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript(
						"return document.readyState").equals("complete");
			}
		};

		WebDriverWait wait = new WebDriverWait(driver, 30);

		try {

			wait.until(expectation);

		} catch (Exception ex) {

			ex.printStackTrace();
		}
	}

	/**
	 * Component to move actual mouse pointer to top left corner of page.
	 * 
	 * @throws AWTException
	 */
	public void moveActualMouse() throws AWTException {

		Robot robot = new Robot();
		robot.mouseMove(0, 0);
		robot = null;
	}

	/**
	 * Component to move actual mouse pointer to top left corner of page
	 * 
	 * @param x
	 * @param y
	 * @throws AWTException
	 */
	public void moveActualMouse(int x, int y) throws AWTException {

		Robot robot = new Robot();
		robot.mouseMove(x, y);
		robot = null;
	}

	/**
	 * Component to click on close button of overlay and wait to until the
	 * overlay closes itself.
	 * 
	 * @param elementToCheck
	 * @param closeButton
	 * @throws Exception
	 */
	public void clickAndWaitUntilDisappear(By elementToCheck, By closeButton)
			throws Exception {

		if (isElementPresent(elementToCheck, 0)) {

			clickElement(closeButton);

			while (true) {

				if (isElementPresent(elementToCheck, 1)) {

				} else {

					break;
				}
			}
		}
	}

	/**
	 * Component to wait until the overlay closes itself
	 * 
	 * @param elementToCheck
	 * @throws Exception
	 */
	public void waitUntilDisappear(By elementToCheck) throws Exception {

		int i = 0;

		while (true) {

			if (isElementPresent(elementToCheck, 1)) {

			} else {

				break;
			}

			if (i == 10) {

				break;
			}

			i++;
		}
	}

	/**
	 * Component to paste text to a control
	 * 
	 * @param pasteBy
	 * @throws Exception
	 */
	public void pasteText(By pasteBy) throws Exception {

		if (isElementPresent(pasteBy)) {

			clearElement(pasteBy);
			driver.findElement(pasteBy).sendKeys(Keys.CONTROL + "v");
		}
	}

	/**
	 * Component to get attribute value for a particular control
	 * 
	 * @param elementBy
	 * @param attribute
	 * @return
	 * @throws Exception
	 */
	public String getAttribute(By elementBy, String attribute) throws Exception {

		String attributeValue = "";

		if (isElementPresent(elementBy)) {

			attributeValue = driver.findElement(elementBy).getAttribute(
					attribute);
		}

		return attributeValue;
	}

	/**
	 * Component to get attribute value for a particular control
	 * 
	 * @param element
	 * @param attribute
	 * @return
	 * @throws Exception
	 */
	public String getAttribute(WebElement element, String attribute)
			throws Exception {

		String attributeValue = "";

		if (isElementPresent(element, 2)) {

			attributeValue = element.getAttribute(attribute);
		}

		return attributeValue;
	}

	/**
	 * Component to get attribute value for a particular control
	 * 
	 * @param element
	 * @param elementBy
	 * @param attribute
	 * @return
	 * @throws Exception
	 */
	public String getAttribute(WebElement element, By elementBy,
			String attribute) throws Exception {

		String attributeValue = "";

		try {			
			attributeValue = element.findElement(elementBy).getAttribute(
					attribute);
			
		} catch (Exception ex) {
			
			System.out.println(ex.getMessage());
		}	

		return attributeValue;
	}

	/**
	 * Component to get CSS value for a particular control
	 * 
	 * @param elementBy
	 * @param cssAttribute
	 * @return
	 * @throws Exception
	 */
	public String getCssValue(By elementBy, String cssAttribute)
			throws Exception {

		String cssValue = "";

		if (isElementPresent(elementBy)) {

			cssValue = driver.findElement(elementBy).getCssValue(cssAttribute);
		}

		return cssValue;
	}

	/**
	 * Component to get CSS value for a particular control
	 * 
	 * @param element
	 * @param elementBy
	 * @param cssAttribute
	 * @return
	 * @throws Exception
	 */
	public String getCssValue(WebElement element, By elementBy,
			String cssAttribute) throws Exception {

		String cssValue = "";

		if (isElementPresent(element, elementBy)) {

			cssValue = element.findElement(elementBy).getCssValue(cssAttribute);
		}

		return cssValue;
	}

	/**
	 * Method to getLocation for a particular element
	 * 
	 * @param elementBy
	 * @return
	 * @throws Exception
	 */
	public Point getLocation(By elementBy) throws Exception {

		Point locations = new Point(0, 0);

		if (isElementPresent(elementBy, 5)) {

			locations = driver.findElement(elementBy).getLocation();
		}

		return locations;
	}

	/**
	 * Method to refresh web page
	 * 
	 * @throws Exception
	 */
	public void refresh() throws Exception {

		driver.navigate().refresh();
	}

	/**
	 * Method to get no of webElements count
	 * 
	 * @param elementBy
	 * @return
	 * @throws Exception
	 */
	public int getElementsCount(By elementBy) throws Exception {

		int noOfElements = 0;

		if (noWaitElementPresent(elementBy)) {

			noOfElements = driver.findElements(elementBy).size();
		}

		return noOfElements;
	}

	/**
	 * Method to get webElements
	 * 
	 * @param elementBy
	 * @return
	 * @throws Exception
	 */
	public List<WebElement> getElements(By elementBy) throws Exception {

		List<WebElement> lstWebElements = new ArrayList<WebElement>();

		if (isElementPresent(elementBy, 2)) {

			lstWebElements = driver.findElements(elementBy);
		}

		return lstWebElements;
	}

	/**
	 * Method to get webElements
	 * 
	 * @param elementBy
	 * @return
	 * @throws Exception
	 */
	public List<WebElement> getElements(WebElement webElement, By elementBy) throws Exception {

		List<WebElement> lstWebElements = new ArrayList<WebElement>();

		if (isElementPresent(webElement,elementBy,2)) {

			lstWebElements = webElement.findElements(elementBy);
		}

		return lstWebElements;
	}
	
	/**
	 * Method to get element count
	 * @param webElement
	 * @param elementBy
	 * @return
	 * @throws Exception
	 */
	public int getElementsCount(WebElement webElement, By elementBy) throws Exception {

		int noOfElements = 0;

		if (isElementPresent(webElement,elementBy,0)) {

			noOfElements = webElement.findElements(elementBy).size();
		}

		return noOfElements;
	}
	
	
	/**
	 * Method to open new tab
	 * 
	 * @param url
	 */
	public void openTab(String url) {

		String script = "var d=document,a=d.createElement('a');a.target='_blank';a.href='%s';a.innerHTML='.';d.body.appendChild(a);return a";
		script = String.format(script, url);

		Object element = ((JavascriptExecutor) driver).executeScript(script);

		if (element instanceof WebElement) {

			WebElement anchor = (WebElement) element;
			anchor.click();
			((JavascriptExecutor) driver).executeScript(
					"var a=arguments[0];a.parentNode.removeChild(a);", anchor);

			Set<String> handles = driver.getWindowHandles();
			String current = driver.getWindowHandle();
			handles.remove(current);

			String newTab = handles.iterator().next();

			driver.switchTo().window(newTab);

			report.addReportStep("Open new tab", "New tab opened successfully",
					StepResult.PASS);

		} else {

			report.addReportStep("Open new tab", "New tab was not opened",
					StepResult.FAIL);
		}
	}

	/**
	 * Method to trigger javascript on web element
	 * 
	 * @param script
	 * @param element
	 */
	public void trigger(String script, WebElement element) {

		((JavascriptExecutor) driver).executeScript(script, element);
	}

	/**
	 * Executes a script
	 * 
	 * @note Really should only be used when the web driver is sucking at
	 *       exposing functionality natively
	 * @param script
	 *            The script to execute
	 */
	public Object trigger(String script) {

		return ((JavascriptExecutor) driver).executeScript(script);
	}

	/**
	 * Method to get width in pixels of an element
	 * 
	 * @param elementBy
	 * @return
	 * @throws Exception
	 */
	public int getWidth(By elementBy) throws Exception {

		int width = 0;

		if (noWaitElementPresent(elementBy)) {

			width = driver.findElement(elementBy).getSize().getWidth();
		}

		return width;
	}

	/**
	 * Method to get height in pixels of an element
	 * 
	 * @param elementBy
	 * @return
	 * @throws Exception
	 */
	public int getHeight(By elementBy) throws Exception {

		int height = 0;

		if (noWaitElementPresent(elementBy)) {

			height = driver.findElement(elementBy).getSize().getHeight();
		}

		return height;
	}

	/**
	 * Method to clear browser history
	 * 
	 * @throws Exception
	 */
	public void clearHistory() throws Exception {

		driver.manage().deleteAllCookies();
		refresh();

		report.addReportStep(
				"Clear the history or the browser and refresh the page",
				"History is cleared and page is refreshed", StepResult.PASS);
	}

	/**
	 * Method to set display style of element to block
	 * 
	 * @param elementBy
	 */
	public void blockElement(By elementBy) {

		WebElement weElement = driver.findElement(elementBy);

		JavascriptExecutor executor = (JavascriptExecutor) driver;
		((RemoteWebDriver) executor).executeScript(
				"arguments[0].style.display = 'block';", weElement);
	}     
    
    /**
     * Method to check if element is selected
     * @param elementBy
     * @return
     * @throws Exception
     */
    public boolean isSelected(By elementBy) throws Exception {
    	
    	return driver.findElement(elementBy).isSelected();
    }
    
    
    
    /**
     * This method checks the ascending order of list.
     * 
     */
    public boolean isAscending(List<Integer> sortOrder){
	
    	boolean ascending = true;
		
    	for (int i = 1; i < sortOrder.size() && (ascending); i++) {
		    ascending = ascending && sortOrder.get(i) >= sortOrder.get(i-1);
		}
    	
    	return ascending;    	
    }
    
    
    
    /**
     * This method checks the descending order of list.
     * 
     */
    public boolean isDescending(List<Integer> sortOrder){
	
		boolean descending = true;
		
		for (int i = 1; i < sortOrder.size() && (descending); i++) {
		    descending = descending && sortOrder.get(i) <= sortOrder.get(i-1);
		}
		
		return descending; 
    }

    /**
	 * Converts the accented characters to deaccented Ex: Décor to Decor
	 * 
	 */
	public String deAccent(String str) {
		String nfdNormalizedString = Normalizer.normalize(str,
				Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(nfdNormalizedString).replaceAll("");
	}
	
	/**
	 * Function that returns a string within quotes. Intended to be used in
	 * reports.
	 * 
	 */
	public String inQuotes(String str) {
		
		if(str.contains("$")){
			str = str.replaceAll("\\$", "");
		}
		if (str != null)
			return "\"" + str + "\"";
		else
			return "";
	}
	
	/**
	 * Method to move to element
	 * @param elementBy
	 */
	public void moveToElement(By elementBy) {
    	
    	Actions act = new Actions(driver);	
		WebElement mainMenu = driver.findElement(elementBy); 
		act.moveToElement(mainMenu).build().perform();
    }	
	
	/**
	 * Method to send keys to text element without clearing
	 * 
	 * @param elementBy
	 * @param typeValue
	 * @throws InterruptedException
	 */
	public void sendKeysWithoutClearing(By elementBy, String typeValue) throws Exception {

		if (isElementPresent(elementBy)) {
			
			driver.findElement(elementBy).sendKeys(typeValue);
		}
	}
	
	/**
	 * Method to getLocation for a particular element
	 * 
	 * @param elementBy
	 * @return
	 * @throws Exception
	 */
	public Point getLocation(WebElement element, By elementBy) throws Exception {

		Point location = new Point(0, 0);

		if (isElementPresent(element,elementBy, 5)) {

			location = element.findElement(elementBy).getLocation();
		}

		return location;
	}

	/**
	 * Method to get text of an element within parent element
	 * 
	 * @param element
	 * @param elementBy
	 * @return
	 * @throws Exception 
	 */
	public String getText(By parentElement, By elementBy) throws Exception {

		String text = "";
		
		if (isElementPresent(parentElement, 2)) {

			WebElement element = driver.findElement(parentElement);
			
			if (isElementPresent(element, elementBy)) {
			
				return element.findElement(elementBy).getText().trim();
			}		
		} 
		
		return text;
	}
	
	/**
	 * Method to click element within parent element
	 * 
	 * @param element
	 * @param elementBy
	 * @throws Exception 
	 */
	public void clickElement(By parentElementBy, By elementBy) throws Exception {

		if(isElementPresent(parentElementBy, 2)) {
			
			WebElement parentElement = driver.findElement(parentElementBy);
		
			if (isElementPresent(parentElement, elementBy)) {

				parentElement.findElement(elementBy).click();
			}
		}		
	}
}
