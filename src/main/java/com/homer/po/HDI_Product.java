package com.homer.po;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.homer.dao.CommonDataColumn;
import com.homer.dao.InstanceContainer;
import com.homer.enums.EnumClass.StepResult;

public class HDI_Product extends PageBase {

	public HDI_Product(InstanceContainer ic) {
		super(ic);
	}

	public void openApplicationUrl() throws Exception {
		wh.clearHistory();
		driver.get(dataTable.getCommonData(CommonDataColumn.EnvironmentUrl));
		driver.manage().window().maximize();
	}

	public void loginToApplication() throws Exception {

		wh.sendKeys(userName, dataTable.getCommonData(CommonDataColumn.SignInUser));
		wh.sendKeys(password, dataTable.getCommonData(CommonDataColumn.SignInPassword));
		wh.clickElement(loginButton);
		if (driver.getTitle().equalsIgnoreCase("PacMan Dashboard UI")) {
			report.addReportStepWithScreenshots("Should login to the application ",
					"Login Successful with user id : " + dataTable.getCommonData(CommonDataColumn.SignInUser),
					StepResult.PASS);
		} else {
			report.addReportStepWithScreenshots("Should login to the application ", "Login Failed", StepResult.FAIL);
		}
	}

	public void select_HdiProduct_Request() throws Exception {

		wh.clickElement(newRequest);
		wh.clickElement(productRequest);
		for (String winHandle : wh.driver.getWindowHandles()) {
			wh.driver.switchTo().window(winHandle);
			wh.driver.manage().window().maximize();
		}
		wh.waitForElementPresent(requestDesc, 4);

		if (wh.getText(pageTitle).equalsIgnoreCase("Home Services Pricing")) {
			report.addReportStepWithScreenshots("HDI Product Request should be selected ", "HDI Product selected Successfully",
					StepResult.PASS);
		} else {
			report.addReportStepWithScreenshots("HDI Product Request should be selected", "HDI Product request selection Failed",
					StepResult.FAIL);
		}
	}

	public void requesDetails() throws Exception {
		
		if (wh.isElementPresent(requestDesc)) {
			wh.sendKeys(requestDesc, "HDI Submission Request");
		} else {
			report.addReportStepWithScreenshots("Request Description should be present",
					"Failed to load the page fully request description not present", StepResult.FAIL);
		}
	}
	
	public void ExcelWrite() throws IOException {

		FileOutputStream fos = null;
		FileInputStream excelFile = null;

		try {
			excelFile = new FileInputStream(
					new File(System.getProperty("user.dir") + "\\Input_Excel\\HDI_Product_SKU.xlsx"));
			
			wb = new XSSFWorkbook(excelFile);
			Sheet sheet1 = wb.getSheetAt(0);
			Row row = sheet1.getRow(1);
			
			double y = 20 - (Math.random()*10);
			double cost_price = Math.round(y * 100.0) / 100.0;
			row.getCell(10).setCellValue(cost_price);
			
			double x = 30 - (Math.random()*10);
			double retail_price = Math.round(x * 100.0) / 100.0;
			row.getCell(12).setCellValue(retail_price);
			
			excelFile.close();
			fos = new FileOutputStream(new File(System.getProperty("user.dir") + "\\Input_Excel\\HDI_Product_SKU.xlsx"));
			wb.write(fos);
			fos.close();
			
			report.addReportStep("Excel should be updated with the input data", "Excel is updated with input data",
					StepResult.PASS);
		}

		catch (Exception e) {
			e.printStackTrace();
			report.addReportStepWithScreenshots("Excel should be updated with the input data", "Excel is not updated with input data",
					StepResult.FAIL);	
		}
	}
	
	public void Excel_updatedvalues() throws Exception {
		
		FileInputStream excelFile = new FileInputStream(
				new File(System.getProperty("user.dir") + "\\Input_Excel\\HDI_Product_SKU.xlsx"));
		wb = new XSSFWorkbook(excelFile);
		try{
			String sku= wb.getSheetAt(0).getRow(1).getCell(3).toString();
			//SKU = Integer.parseInt(sku);
			String retail =  wb.getSheetAt(0).getRow(1).getCell(12).toString();
			retail_price = Double.parseDouble(retail);
			String cost =  wb.getSheetAt(0).getRow(1).getCell(10).toString();
			cost_price = Double.parseDouble(cost);
			vendor= wb.getSheetAt(0).getRow(1).getCell(7).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void ExcelUpload() throws Exception {

		try {
			if (wh.isElementPresent(browse_button)) {
				
				wh.driver.findElement(upload_button)
				.sendKeys(System.getProperty("user.dir") + "\\Input_Excel\\HDI_Product_SKU.xlsx");
				Thread.sleep(1000);
				wh.clickElement(uploadClick);
				WebDriverWait wait = new WebDriverWait(driver, 45);
				wait.until(ExpectedConditions.invisibilityOfElementLocated(upload_progressbar));
				wh.clickElement(dialog_close);
				Thread.sleep(1000);
				request_id = wh.getText(req_id);
				System.out.println("request id:"+request_id);
				datesubmitted = wh.getText(submitted_date);
				
				if (wh.getText(req_status).equalsIgnoreCase("Upload Complete")
						|| wh.getText(req_status).equalsIgnoreCase("Upload Complete with errors/warnings")) {
					report.addReportStepWithScreenshots("Should upload the input excel sheet successfully",
							"Excel Sheet uploaded successfully", StepResult.PASS);
				} else {
					report.addReportStepWithScreenshots("Should upload the input excel sheet successfully",
							"Uploaded Failed due to errors", StepResult.FAIL);
				}
			} else {
				report.addReportStep("Upload button should be present",
						"Failed to load the page fully upload button not present", StepResult.FAIL);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void SubmitRequest() throws Exception {

		boolean statusflag = false;
		if(!(wh.getText(req_status).equals("Upload Complete with All Errors"))) {
			wh.driver.navigate().refresh();
			Thread.sleep(2000);
			if (wh.isElementPresent(submit)) {
				wh.clickElement(submit);
				for (int i = 0; i < 5; i++) {
					wh.driver.navigate().refresh();
					Thread.sleep(4000);
					if (wh.getText(req_status).equalsIgnoreCase("Transmitted")) {
						statusflag = true;
						break;
					}
				}
				if (statusflag) {
					report.addReportStepWithScreenshots("Request should move to transmitted status", "Request moved to Transmitted status",
							StepResult.PASS);
				} else {
					report.addReportStepWithScreenshots("Request should move to transmitted status",
							"Request is in " + wh.getText(req_status), StepResult.FAIL);
				}
			} else {
				report.addReportStep("Submit the request",
						"Unable to submit the request at this moment", StepResult.FAIL);
			}
		}
	}
	
}
