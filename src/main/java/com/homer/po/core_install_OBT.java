package com.homer.po;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.homer.dao.CommonDataColumn;
import com.homer.dao.DataColumn;
import com.homer.dao.InstanceContainer;
import com.homer.enums.EnumClass.StepResult;

public class core_install_OBT extends PageBase {

	public core_install_OBT(InstanceContainer ic) {
		super(ic);
		// TODO Auto-generated constructor stub
	}

	public void select_CoreInstall_Request() throws Exception {

		wh.clickElement(newRequest);
		wh.clickElement(InstallRequest);
		for (String winHandle : wh.driver.getWindowHandles()) {
			wh.driver.switchTo().window(winHandle);
			wh.driver.manage().window().maximize();
		}
		wh.waitForElementPresent(requestDesc, 4);

		if (wh.getText(pageTitle).equalsIgnoreCase("Install Pricing")) {
			report.addReportStepWithScreenshots("Core install Request should be selected ", "Core install selected Successfully",
					StepResult.PASS);
		} else {
			report.addReportStepWithScreenshots("Core install Request should be selected", "Core install request selection Failed",
					StepResult.FAIL);
		}
	}
 public void requesDetails() throws Exception {
		
		if (wh.isElementPresent(requestTitle)) {
			wh.sendKeys(requestTitle, "Core install Request");
		} else {
			report.addReportStepWithScreenshots("Request Description should be present",
					"Failed to load the page fully request description not present", StepResult.FAIL);
		}
		if (wh.isElementPresent(OrangeID)) {
			wh.sendKeys(OrangeID, "012345678");
		} else {
			report.addReportStepWithScreenshots("Orange Id should be present",
					"Failed to load the page fully Orange Id not present", StepResult.FAIL);
		}
		
	}
 public void Cost_ExcelWrite() throws IOException {

		FileOutputStream fos = null;
		FileInputStream excelFile = null;

		try {
			excelFile = new FileInputStream(
					new File(System.getProperty("user.dir") + "\\Input_Excel\\core_install.xlsx"));
			
			wb = new XSSFWorkbook(excelFile);
			Sheet sheet1 = wb.getSheetAt(1);
			Row row = sheet1.createRow(2);
			row = sheet1.getRow(2);
			Cell cell = null;
			
			//cost update			
			double y = 20 - (Math.random()*10);
			cost_price = Math.round(y * 100.0) / 100.0;
			System.out.println("cost_price :"+cost_price);	
			cell = row.createCell(14);
		    row.getCell(14).setCellValue(cost_price);
		    
		    cell = row.createCell(1);
		    System.out.println(dataTable.getData(DataColumn.mkt));
		    row.getCell(1).setCellValue(dataTable.getData(DataColumn.mkt));
		    
		    cell = row.createCell(2);
		    row.getCell(2).setCellValue(dataTable.getData(DataColumn.sku_input));
		    
		    cell = row.createCell(4);
		    row.getCell(4).setCellValue(dataTable.getData(DataColumn.mvndr_input));
		    
		    cell = row.createCell(8);
		    row.getCell(8).setCellValue(dataTable.getData(DataColumn.Option_ID));
			
		    
			
			excelFile.close();
			fos = new FileOutputStream(new File(System.getProperty("user.dir") + "\\Input_Excel\\core_install.xlsx"));
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
 
 public void baseoption_Cost_ExcelWrite() throws IOException {

		FileOutputStream fos = null;
		FileInputStream excelFile = null;

		try {
			excelFile = new FileInputStream(
					new File(System.getProperty("user.dir") + "\\Input_Excel\\core_install.xlsx"));
			
			wb = new XSSFWorkbook(excelFile);
			Sheet sheet1 = wb.getSheetAt(1);
			Row row = sheet1.createRow(2);
			row = sheet1.getRow(2);
			Cell cell = null;
			
			//cost update			
			double y = 20 - (Math.random()*10);
			cost_price = Math.round(y * 100.0) / 100.0;
			System.out.println("cost_price :"+cost_price);	
			cell = row.createCell(14);
		    row.getCell(14).setCellValue(cost_price);
		    
		    cell = row.createCell(1);
		    System.out.println(dataTable.getData(DataColumn.mkt));
		    row.getCell(1).setCellValue(dataTable.getData(DataColumn.mkt));
		    
		    cell = row.createCell(2);
		    row.getCell(2).setCellValue(dataTable.getData(DataColumn.sku_input));
		    
		    cell = row.createCell(4);
		    row.getCell(4).setCellValue(dataTable.getData(DataColumn.mvndr_input));
		    
		    cell = row.createCell(6);
		    row.getCell(6).setCellValue(dataTable.getData(DataColumn.Option_ID));
			
		    
			
			excelFile.close();
			fos = new FileOutputStream(new File(System.getProperty("user.dir") + "\\Input_Excel\\core_install.xlsx"));
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
 
 public void Cost_ExcelWrite_SkuHdr() throws IOException {

		FileOutputStream fos = null;
		FileInputStream excelFile = null;

		try {
			excelFile = new FileInputStream(
					new File(System.getProperty("user.dir") + "\\Input_Excel\\core_install.xlsx"));
			
			wb = new XSSFWorkbook(excelFile);
			Sheet sheet1 = wb.getSheetAt(0);
			Row row = sheet1.createRow(2);
			row = sheet1.getRow(2);
			Cell cell = null;
			
			
			cell = row.createCell(11);
	        row.getCell(11).setCellValue("");
			
			//cost update			
			double y = 20 - (Math.random()*10);
			cost_price = Math.round(y * 100.0) / 100.0;
			System.out.println("cost_price :"+cost_price);	
			cell = row.createCell(9);
		    row.getCell(9).setCellValue(cost_price);
		    
		    
		    cell = row.createCell(1);
		    System.out.println(dataTable.getData(DataColumn.mkt));
		    row.getCell(1).setCellValue(dataTable.getData(DataColumn.mkt));
		    
		    cell = row.createCell(2);
		    row.getCell(2).setCellValue(dataTable.getData(DataColumn.sku_input));
		    
		    cell = row.createCell(4);
		    row.getCell(4).setCellValue(dataTable.getData(DataColumn.mvndr_input));
		    
		 
			excelFile.close();
			fos = new FileOutputStream(new File(System.getProperty("user.dir") + "\\Input_Excel\\core_install.xlsx"));
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
 
	public void Retail_ExcelWrite() throws IOException {

		FileOutputStream fos = null;
		FileInputStream excelFile = null;

		try {
			excelFile = new FileInputStream(
					new File(System.getProperty("user.dir") + "\\Input_Excel\\core_install.xlsx"));
			
			wb = new XSSFWorkbook(excelFile);
			Sheet sheet1 = wb.getSheetAt(1);
			Row row = sheet1.createRow(2);
			row = sheet1.getRow(2);
			Cell cell = null;
			
			/*//cost update			
			double y = 20 - (Math.random()*10);
			cost_price = Math.round(y * 100.0) / 100.0;
			System.out.println("cost_price :"+cost_price);	
			 cell = row.createCell(14);
		    //row.getCell(14).setCellValue(cost_price);
			*/
		    //retail update
			double x = 30 - (Math.random()*10);
			retail_price = Math.round(x * 100.0) / 100.0;
			System.out.println("retail_price :"+retail_price);
			cell = row.createCell(16);
		    row.getCell(16).setCellValue(retail_price);
		    
		    cell = row.createCell(1);
		    System.out.println(dataTable.getData(DataColumn.mkt));
		    row.getCell(1).setCellValue(dataTable.getData(DataColumn.mkt));
		    
		    cell = row.createCell(2);
		    row.getCell(2).setCellValue(dataTable.getData(DataColumn.sku_input));
		    
		    cell = row.createCell(4);
		    row.getCell(4).setCellValue(dataTable.getData(DataColumn.mvndr_input));
		    
		    cell = row.createCell(8);
		    row.getCell(8).setCellValue(dataTable.getData(DataColumn.Option_ID));
			
		    
			
			excelFile.close();
			fos = new FileOutputStream(new File(System.getProperty("user.dir") + "\\Input_Excel\\core_install.xlsx"));
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
	
	public void Baseoption_Retail_ExcelWrite() throws IOException {

		FileOutputStream fos = null;
		FileInputStream excelFile = null;

		try {
			excelFile = new FileInputStream(
					new File(System.getProperty("user.dir") + "\\Input_Excel\\core_install.xlsx"));
			
			wb = new XSSFWorkbook(excelFile);
			Sheet sheet1 = wb.getSheetAt(1);
			Row row = sheet1.createRow(2);
			row = sheet1.getRow(2);
			Cell cell = null;
			
			
		    //retail update
			double x = 30 - (Math.random()*10);
			 retail_price = Math.round(x * 100.0) / 100.0;
			System.out.println("retail_price :"+retail_price);
			cell = row.createCell(16);
		    row.getCell(16).setCellValue(retail_price);
		    
		    cell = row.createCell(1);
		    System.out.println(dataTable.getData(DataColumn.mkt));
		    row.getCell(1).setCellValue(dataTable.getData(DataColumn.mkt));
		    
		    cell = row.createCell(2);
		    row.getCell(2).setCellValue(dataTable.getData(DataColumn.sku_input));
		    
		    cell = row.createCell(4);
		    row.getCell(4).setCellValue(dataTable.getData(DataColumn.mvndr_input));
		    
		    cell = row.createCell(6);
		    row.getCell(6).setCellValue(dataTable.getData(DataColumn.Option_ID));
			
		    
			
			excelFile.close();
			fos = new FileOutputStream(new File(System.getProperty("user.dir") + "\\Input_Excel\\core_install.xlsx"));
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
	public void Retail_ExcelWrite_SkuHdr() throws IOException {

		FileOutputStream fos = null;
		FileInputStream excelFile = null;

		try {
			excelFile = new FileInputStream(
					new File(System.getProperty("user.dir") + "\\Input_Excel\\core_install.xlsx"));
			
			wb = new XSSFWorkbook(excelFile);
			Sheet sheet1 = wb.getSheetAt(0);
			Row row = sheet1.createRow(2);
			row = sheet1.getRow(2);
			Cell cell = null;
			
			
			//retail update
			double x = 30 - (Math.random()*10);
			 retail_price = Math.round(x * 100.0) / 100.0;
			System.out.println("retail_price :"+retail_price);
			cell = row.createCell(11);
		    row.getCell(11).setCellValue(retail_price);
		    
		    cell = row.createCell(1);
		    System.out.println(dataTable.getData(DataColumn.mkt));
		    row.getCell(1).setCellValue(dataTable.getData(DataColumn.mkt));
		    
		    cell = row.createCell(2);
		    row.getCell(2).setCellValue(dataTable.getData(DataColumn.sku_input));
		    
		    cell = row.createCell(4);
		    row.getCell(4).setCellValue(dataTable.getData(DataColumn.mvndr_input));
		    
		
			
			excelFile.close();
			fos = new FileOutputStream(new File(System.getProperty("user.dir") + "\\Input_Excel\\core_install.xlsx"));
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
	
	/*public void Excel_updatedvalues() throws Exception {
		
		FileInputStream excelFile = new FileInputStream(
				new File(System.getProperty("user.dir") + "\\Input_Excel\\core_install.xlsx"));
		wb = new XSSFWorkbook(excelFile);
		try{
			String sku= wb.getSheetAt(0).getRow(1).getCell(3).toString();
			SKU = Integer.parseInt(sku);
			String retail =  wb.getSheetAt(0).getRow(1).getCell(12).toString();
			retail_price = Double.parseDouble(retail);
			String cost =  wb.getSheetAt(0).getRow(1).getCell(10).toString();
			cost_price = Double.parseDouble(cost);
			vendor= wb.getSheetAt(0).getRow(1).getCell(7).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	public void ExcelClear() throws Exception{
		
		FileOutputStream fos = null;
		FileInputStream excelFile = null;
		
		try{
			
		    excelFile = new FileInputStream(
					new File(System.getProperty("user.dir") + "\\Input_Excel\\core_install.xlsx"));
	
			wb = new XSSFWorkbook(excelFile);
			Sheet sheet1 = wb.getSheetAt(0);
			Row row = sheet1.createRow(2);
			row = sheet1.getRow(2);
			Cell cell = null;
			
			
			cell = row.createCell(1);
	        row.getCell(1).setCellValue("");
	        cell = row.createCell(2);
	        row.getCell(2).setCellValue("");
	        cell = row.createCell(4);
	        row.getCell(4).setCellValue("");
	        cell = row.createCell(9);
	        row.getCell(9).setCellValue("");
	        cell = row.createCell(11);
	        row.getCell(11).setCellValue("");
	       
	    //clearing 2nd sheet    
	        
	        Sheet sheet2 = wb.getSheetAt(1);
	        Row row1 = sheet2.createRow(2);
			row1 = sheet2.getRow(2);
			Cell cell1 = null;
			
			
			cell1 = row1.createCell(1);
	        row1.getCell(1).setCellValue("");
	        cell1 = row1.createCell(2);
	        row1.getCell(2).setCellValue("");
	        cell1 = row1.createCell(4);
	        row1.getCell(4).setCellValue("");
	        cell1 = row1.createCell(6);
	        row1.getCell(6).setCellValue("");
	        cell1 = row1.createCell(8);
	        row1.getCell(8).setCellValue("");
	        cell1 = row1.createCell(14);
	        row1.getCell(14).setCellValue("");
	        cell1 = row1.createCell(16);
	        row1.getCell(16).setCellValue("");
	        
	        
	        excelFile.close();
	        fos = new FileOutputStream(new File(System.getProperty("user.dir") + "\\Input_Excel\\core_install.xlsx"));
			wb.write(fos);
			fos.close();
	        
	        
	       } catch (Exception e) {
			e.printStackTrace();
			report.addReportStepWithScreenshots("Excel should be cleared with the input data", "Excel is not cleared with input data",
					StepResult.FAIL);	
		}
	}

	public void ExcelUpload() throws Exception {

		try {
			if (wh.isElementPresent(browse_button)) {
				
				wh.driver.findElement(upload_button)
				.sendKeys(System.getProperty("user.dir") + "\\Input_Excel\\core_install.xlsx");
				Thread.sleep(1000);
				wh.clickElement(uploadClick);
				WebDriverWait wait = new WebDriverWait(driver,8);
				wait.until(ExpectedConditions.invisibilityOfElementLocated(upload_progressbar));
				wh.clickElement(dialog_close);
				Thread.sleep(1000);
				request_id = wh.getText(req_id);
				System.out.println("request di:"+request_id);
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
	
public void OBT_table_validation_BISTVSKU() throws Exception {
		
		List<String> store_numbers = new ArrayList<String>();
		
	//Getting Store assorted to the market
	String query = "SELECT STR_NBR FROM STR WHERE MKT_NBR =" +dataTable.getData(DataColumn.mkt)+" WITH UR;";
	DB2_tables_connect(query);
	try{
	while(DB2RS.next()){
		store_numbers.add(DB2RS.getString("STR_NBR"));		
	}
	System.out.println("Store number: "+store_numbers);	
	DB2RS.close();
	stmt.close();
	con.close();
	}
	catch(Exception e){
		
		report.addReportStep("Should fetch STORE number from STR table",
				"Error Fetching record", StepResult.FAIL);
	}
	
	String DEST_ORG_KEY_VAL,Table_str_nbr=null,Table_mkt_nbr=null,Input_mkt_nbr;
	Input_mkt_nbr = dataTable.getData(DataColumn.mkt);
	if(Input_mkt_nbr.length()==1){Input_mkt_nbr = "000"+Input_mkt_nbr;}
	else if(Input_mkt_nbr.length()==2){Input_mkt_nbr = "00"+Input_mkt_nbr;}
	else if(Input_mkt_nbr.length()==3){Input_mkt_nbr = "0"+Input_mkt_nbr;}
	System.out.println("Final market : "+Input_mkt_nbr);	
	int count=0,sub_string_count=0;
	boolean str_flag=true,mkt_flag=true,tran_typ_flag=true, tran_hdr_flag=true,tran_hdr_flag1=true,user_id_flag=true,
			job_step_flag=true,req_id_flag=true,obt_stat_flag=true;
	if(!(store_numbers.isEmpty())){
	//Fetching OBT SUSB Table
	//Forming Market Number from input
	
	query = "select * from obt_susp order by Trans_crt_ts desc FETCH FIRST 100 ROWS ONLY with ur ;";
	DB2_tables_connect(query);
	while(DB2RS.next()){
		count++;
		
		//validating DEST_ORG_KEY_VAL column - Store and market number
		DEST_ORG_KEY_VAL=DB2RS.getString("DEST_ORG_KEY_VAL");
		Table_str_nbr = DEST_ORG_KEY_VAL.substring(DEST_ORG_KEY_VAL.length()-4);
		for(int i=0;i>=3;i++)
		{
			if(DEST_ORG_KEY_VAL.substring(DEST_ORG_KEY_VAL.length()-4).charAt(i)=='0')
			{sub_string_count++;}
			else{break;}
		}
		
		//validating Store number
		//seperating preceeding Zero's for Store number from table value
		Table_str_nbr = Table_str_nbr.substring(sub_string_count);		
		if(!(store_numbers.contains(Table_str_nbr))){str_flag=false;}
		
		//validating market number
		Table_mkt_nbr = DEST_ORG_KEY_VAL.substring(DEST_ORG_KEY_VAL.length()-8,DEST_ORG_KEY_VAL.length()-4);
		if(!(Table_mkt_nbr.equalsIgnoreCase(Input_mkt_nbr))){mkt_flag=false;}
		
		//Validating TRANS_TYP_ID column - trigger type		
		if(!(DB2RS.getString("TRANS_TYP_ID").trim().equalsIgnoreCase("BISTVSKU"))){tran_typ_flag=false;}
		
		
		//validating TRANS_HDR_KEY_VAL column - SKU number		
		String TRANS_HDR_KEY_VAL=DB2RS.getString("TRANS_HDR_KEY_VAL");
		TRANS_HDR_KEY_VAL=TRANS_HDR_KEY_VAL.trim();
		if(!(TRANS_HDR_KEY_VAL.contains(dataTable.getData(DataColumn.sku_input)))){tran_hdr_flag=false;}
		
		//validating TRANS_HDR_KEY_VAL column - MVNDR number		
	    if(!(TRANS_HDR_KEY_VAL.contains(dataTable.getData(DataColumn.mvndr_input)))){tran_hdr_flag1=false;}
		
		//validating USER_ID column 		
		String USER_ID=DB2RS.getString("USER_ID");
		USER_ID=USER_ID.trim();
		if(!(USER_ID.equalsIgnoreCase(dataTable.getCommonData(CommonDataColumn.SignInUser)))){user_id_flag=false;}
		
		
		// validating DLOG_JOB_STEP_ID column  - Request status
		String DLOG_JOB_STEP_ID=DB2RS.getString("DLOG_JOB_STEP_ID");
		DLOG_JOB_STEP_ID=DLOG_JOB_STEP_ID.trim();
		if(!(DLOG_JOB_STEP_ID.equalsIgnoreCase("TRANSMIT"))){job_step_flag=false;}
		
		// validating DLOG_JOB_ID column - request_id
		String DLOG_JOB_ID=DB2RS.getString("DLOG_JOB_ID");
		DLOG_JOB_ID=DLOG_JOB_ID.trim();
		if(!(DLOG_JOB_ID.equalsIgnoreCase(request_id))){req_id_flag=false;}
				
		// validating OBT_STAT_IND column - request_id
		String OBT_STAT_IND=DB2RS.getString("OBT_STAT_IND");
		OBT_STAT_IND=OBT_STAT_IND.trim();
		if(!(OBT_STAT_IND.equalsIgnoreCase("WT"))){obt_stat_flag=false;}
				
		//Breaking the loop
		if(count==store_numbers.size()){break;}
		
		
	}
	}
	else{
		report.addReportStep("Store number should be present in STR tables",
				"No store number found", StepResult.FAIL);
		
	}
	DB2RS.close();
	stmt.close();
	con.close();
	
	//Store Validation adding report
	if(str_flag){
		report.addReportStep("Entry for all the STORE NUMBER should be present in OBT_SUSP table",
				"All assorted STORE NUMBER are present in OBT_SUSP table", StepResult.PASS);
	}
	else{
		report.addReportStep("Entry for all the STORE NUMBER should be present in OBT_SUSP table",
				"Invalid STORE NUMBER found in few records.", StepResult.FAIL);
	}
	//Market Validation adding report
	if(mkt_flag){
		report.addReportStep("MARKET NUMBER should be updated in OBT_SUSB tables",
				"MARKET NUMBER updated successfully for all the records in OBT_SUSP table", StepResult.PASS);
	}
	else{
		report.addReportStep("MARKET NUMBER should be updated in OBT_SUSB tables",
				"Invalid MARKET NUMBER found in few records", StepResult.FAIL);
	}
	//TRANS_TYP_ID - Trigger type Validation -  adding report
		if(tran_typ_flag){
			report.addReportStep("Valid TRIGGER TYPE should be updated in OBT_SUSB tables",
					"Valid TRIGGER TYPE is present for all the records in OBT_SUSP table", StepResult.PASS);
		}
		else{
			report.addReportStep("Valid TRIGGER TYPE should be updated in OBT_SUSB tables",
					"Invalid TRIGGER TYPE found in few records", StepResult.FAIL);
		}	
	
	//TRANS_HDR_EY_ID - SKU number validation - adding report
		if(tran_hdr_flag){
		
			report.addReportStep("Valid SKU NUMBER should be updated in OBT_SUSB tables",
					"Valid  SKU Invalid TRIGGER TYPE found in few records is present for all the records in OBT_SUSP table", StepResult.PASS);
		}
		else{
			report.addReportStep("Valid SKU NUMBER should be updated in OBT_SUSB tables",
					"Invalid SKU NUMBER found in few records", StepResult.FAIL);
		}
		
	//TRANS_HDR_EY_ID -MVNDR number validation - adding report
		if(tran_hdr_flag1){
				
			report.addReportStep("Valid MVNDR NUMBER should be updated in OBT_SUSB tables",
							"Valid  MVNDR Invalid TRIGGER TYPE found in few records is present for all the records in OBT_SUSP table", StepResult.PASS);
		}
	   else{
					report.addReportStep("Valid MVNDR NUMBER should be updated in OBT_SUSB tables",
							"Invalid MVNDR NUMBER found in few records", StepResult.FAIL);
		}
		
		
		
	//USER_ID column validating - adding report
		if(user_id_flag){
			report.addReportStep("Valid USER ID should be updated in OBT_SUSB tables",
					"Valid USER ID is present for all the records in OBT_SUSP table", StepResult.PASS);
		}
		else{
			report.addReportStep("Valid USER ID should be updated in OBT_SUSB tables",
					"Invalid USER ID found in few records", StepResult.FAIL);
		}
	
	// DLOG_JOB_STEP_ID validation - adding report 
	if(job_step_flag)
	{
		report.addReportStep("Valid REQUEST STATUS should be updated in OBT_SUSB tables",
				"Valid REQUEST STATUS is present for all the records in OBT_SUSP table", StepResult.PASS);
	}
	else{
		report.addReportStep("Valid REQUEST STATUS should be updated in OBT_SUSB tables",
				"Invalid REQUEST STATUS found in few records", StepResult.FAIL);
	}
	
	// DLOG_JOB_ID validation - adding report
	if(req_id_flag)
	{
		report.addReportStep("Valid REQUEST ID should be updated in OBT_SUSB tables",
				"Valid REQUEST ID is present for all the records in OBT_SUSP table", StepResult.PASS);
	}
	else{
		report.addReportStep("Valid REQUEST ID should be updated in OBT_SUSB tables",
				"Invalid REQUEST ID found in few records", StepResult.FAIL);
	}

	// OBT_STAT_IND validation - adding report
		if(obt_stat_flag)
		{
			report.addReportStep("Valid STATUS CODE should be updated in OBT_SUSB tables",
					"Valid STATUS CODE is present for all the records in OBT_SUSP table", StepResult.PASS);
		}
		else{
			report.addReportStep("Valid STATUS CODE should be updated in OBT_SUSB tables",
					"Invalid STATUS CODE found in few records", StepResult.FAIL);
		}
	}
	


	public void OBT_table_validation_BISTSKU() throws Exception {
		
		List<String> store_numbers = new ArrayList<String>();
		
	//Getting Store assorted to the market
	String query = "SELECT STR_NBR FROM STR WHERE MKT_NBR =" +dataTable.getData(DataColumn.mkt)+" WITH UR;";
	DB2_tables_connect(query);
	try{
	while(DB2RS.next()){
		store_numbers.add(DB2RS.getString("STR_NBR"));		
	}
	System.out.println("Store number: "+store_numbers);	
	DB2RS.close();
	stmt.close();
	con.close();
	}
	catch(Exception e){
		
		report.addReportStep("Should fetch STORE number from STR table",
				"Error Fetching record", StepResult.FAIL);
	}
	
	String DEST_ORG_KEY_VAL,Table_str_nbr=null,Table_mkt_nbr=null,Input_mkt_nbr;
	Input_mkt_nbr = dataTable.getData(DataColumn.mkt);
	if(Input_mkt_nbr.length()==1){Input_mkt_nbr = "000"+Input_mkt_nbr;}
	else if(Input_mkt_nbr.length()==2){Input_mkt_nbr = "00"+Input_mkt_nbr;}
	else if(Input_mkt_nbr.length()==3){Input_mkt_nbr = "0"+Input_mkt_nbr;}
	System.out.println("Final market : "+Input_mkt_nbr);	
	int count=0,sub_string_count=0;
	boolean str_flag=true,mkt_flag=true,tran_typ_flag=true, tran_hdr_flag=true,user_id_flag=true,
			job_step_flag=true,req_id_flag=true,obt_stat_flag=true;
	if(!(store_numbers.isEmpty())){
	//Fetching OBT SUSB Table
	//Forming Market Number from input
	
	query = "select * from obt_susp order by Trans_crt_ts desc FETCH FIRST 100 ROWS ONLY with ur ;";
	DB2_tables_connect(query);
	while(DB2RS.next()){
		count++;
		
		//validating DEST_ORG_KEY_VAL column - Store and market number
		DEST_ORG_KEY_VAL=DB2RS.getString("DEST_ORG_KEY_VAL");
		Table_str_nbr = DEST_ORG_KEY_VAL.substring(DEST_ORG_KEY_VAL.length()-4);
		for(int i=0;i>=3;i++)
		{
			if(DEST_ORG_KEY_VAL.substring(DEST_ORG_KEY_VAL.length()-4).charAt(i)=='0')
			{sub_string_count++;}
			else{break;}
		}
		
		//validating Store number
		//seperating preceeding Zero's for Store number from table value
		Table_str_nbr = Table_str_nbr.substring(sub_string_count);		
		if(!(store_numbers.contains(Table_str_nbr))){str_flag=false;}
		
		//validating market number
		Table_mkt_nbr = DEST_ORG_KEY_VAL.substring(DEST_ORG_KEY_VAL.length()-8,DEST_ORG_KEY_VAL.length()-4);
		if(!(Table_mkt_nbr.equalsIgnoreCase(Input_mkt_nbr))){mkt_flag=false;}
		
		//Validating TRANS_TYP_ID column - trigger type		
		if(!(DB2RS.getString("TRANS_TYP_ID").trim().equalsIgnoreCase("BISTSKU"))){tran_typ_flag=false;}
		
		
		//validating TRANS_HDR_KEY_VAL column - SKU number		
		String TRANS_HDR_KEY_VAL=DB2RS.getString("TRANS_HDR_KEY_VAL");
		TRANS_HDR_KEY_VAL=TRANS_HDR_KEY_VAL.trim();
		if(!(TRANS_HDR_KEY_VAL.contains(dataTable.getData(DataColumn.sku_input)))){tran_hdr_flag=false;}
		
		
		//validating USER_ID column 		
		String USER_ID=DB2RS.getString("USER_ID");
		USER_ID=USER_ID.trim();
		if(!(USER_ID.equalsIgnoreCase(dataTable.getCommonData(CommonDataColumn.SignInUser)))){user_id_flag=false;}
		
		
		// validating DLOG_JOB_STEP_ID column  - Request status
		String DLOG_JOB_STEP_ID=DB2RS.getString("DLOG_JOB_STEP_ID");
		DLOG_JOB_STEP_ID=DLOG_JOB_STEP_ID.trim();
		if(!(DLOG_JOB_STEP_ID.equalsIgnoreCase("TRANSMIT"))){job_step_flag=false;}
		
		// validating DLOG_JOB_ID column - request_id
		String DLOG_JOB_ID=DB2RS.getString("DLOG_JOB_ID");
		DLOG_JOB_ID=DLOG_JOB_ID.trim();
		if(!(DLOG_JOB_ID.equalsIgnoreCase(request_id))){req_id_flag=false;}
				
		// validating OBT_STAT_IND column - request_id
		String OBT_STAT_IND=DB2RS.getString("OBT_STAT_IND");
		OBT_STAT_IND=OBT_STAT_IND.trim();
		if(!(OBT_STAT_IND.equalsIgnoreCase("WT"))){obt_stat_flag=false;}
				
		//Breaking the loop
		if(count==store_numbers.size()){break;}
		
		
	}
	}
	else{
		report.addReportStep("Store number should be present in STR tables",
				"No store number found", StepResult.FAIL);
		
	}
	DB2RS.close();
	stmt.close();
	con.close();
	
	//Store Validation adding report
	if(str_flag){
		report.addReportStep("Entry for all the STORE NUMBER should be present in OBT_SUSP table",
				"All assorted STORE NUMBER are present in OBT_SUSP table", StepResult.PASS);
	}
	else{
		report.addReportStep("Entry for all the STORE NUMBER should be present in OBT_SUSP table",
				"Invalid STORE NUMBER found in few records.", StepResult.FAIL);
	}
	//Market Validation adding report
	if(mkt_flag){
		report.addReportStep("MARKET NUMBER should be updated in OBT_SUSB tables",
				"MARKET NUMBER updated successfully for all the records in OBT_SUSP table", StepResult.PASS);
	}
	else{
		report.addReportStep("MARKET NUMBER should be updated in OBT_SUSB tables",
				"Invalid MARKET NUMBER found in few records", StepResult.FAIL);
	}
	//TRANS_TYP_ID - Trigger type Validation -  adding report
		if(tran_typ_flag){
			report.addReportStep("Valid TRIGGER TYPE should be updated in OBT_SUSB tables",
					"Valid TRIGGER TYPE is present for all the records in OBT_SUSP table", StepResult.PASS);
		}
		else{
			report.addReportStep("Valid TRIGGER TYPE should be updated in OBT_SUSB tables",
					"Invalid TRIGGER TYPE found in few records", StepResult.FAIL);
		}	
	
	//TRANS_HDR_EY_ID - SKU number validation - adding report
		if(tran_hdr_flag){
		
			report.addReportStep("Valid SKU NUMBER should be updated in OBT_SUSB tables",
					"Valid  SKU Invalid TRIGGER TYPE found in few records is present for all the records in OBT_SUSP table", StepResult.PASS);
		}
		else{
			report.addReportStep("Valid SKU NUMBER should be updated in OBT_SUSB tables",
					"Invalid SKU NUMBER found in few records", StepResult.FAIL);
		}
		
	//USER_ID column validating - adding report
		if(user_id_flag){
			report.addReportStep("Valid USER ID should be updated in OBT_SUSB tables",
					"Valid USER ID is present for all the records in OBT_SUSP table", StepResult.PASS);
		}
		else{
			report.addReportStep("Valid USER ID should be updated in OBT_SUSB tables",
					"Invalid USER ID found in few records", StepResult.FAIL);
		}
	
	// DLOG_JOB_STEP_ID validation - adding report 
	if(job_step_flag)
	{
		report.addReportStep("Valid REQUEST STATUS should be updated in OBT_SUSB tables",
				"Valid REQUEST STATUS is present for all the records in OBT_SUSP table", StepResult.PASS);
	}
	else{
		report.addReportStep("Valid REQUEST STATUS should be updated in OBT_SUSB tables",
				"Invalid REQUEST STATUS found in few records", StepResult.FAIL);
	}
	
	// DLOG_JOB_ID validation - adding report
	if(req_id_flag)
	{
		report.addReportStep("Valid REQUEST ID should be updated in OBT_SUSB tables",
				"Valid REQUEST ID is present for all the records in OBT_SUSP table", StepResult.PASS);
	}
	else{
		report.addReportStep("Valid REQUEST ID should be updated in OBT_SUSB tables",
				"Invalid REQUEST ID found in few records", StepResult.FAIL);
	}

	// OBT_STAT_IND validation - adding report
		if(obt_stat_flag)
		{
			report.addReportStep("Valid STATUS CODE should be updated in OBT_SUSB tables",
					"Valid STATUS CODE is present for all the records in OBT_SUSP table", StepResult.PASS);
		}
		else{
			report.addReportStep("Valid STATUS CODE should be updated in OBT_SUSB tables",
					"Invalid STATUS CODE found in few records", StepResult.FAIL);
		}
	}
	
public void OBT_table_validation_BISTIBPL() throws Exception {
		
		List<String> store_numbers = new ArrayList<String>();
		
	//Getting Store assorted to the market
	String query = "SELECT STR_NBR FROM STR WHERE MKT_NBR =" +dataTable.getData(DataColumn.mkt)+" WITH UR;";
	DB2_tables_connect(query);
	try{
	while(DB2RS.next()){
		store_numbers.add(DB2RS.getString("STR_NBR"));		
	}
	System.out.println("Store number: "+store_numbers);	
	DB2RS.close();
	stmt.close();
	con.close();
	}
	catch(Exception e){
		
		report.addReportStep("Should fetch STORE number from STR table",
				"Error Fetching record", StepResult.FAIL);
	}
	
	String DEST_ORG_KEY_VAL,Table_str_nbr=null,Table_mkt_nbr=null,Input_mkt_nbr;
	Input_mkt_nbr = dataTable.getData(DataColumn.mkt);
	if(Input_mkt_nbr.length()==1){Input_mkt_nbr = "000"+Input_mkt_nbr;}
	else if(Input_mkt_nbr.length()==2){Input_mkt_nbr = "00"+Input_mkt_nbr;}
	else if(Input_mkt_nbr.length()==3){Input_mkt_nbr = "0"+Input_mkt_nbr;}
	System.out.println("Final market : "+Input_mkt_nbr);	
	int count=0,sub_string_count=0;
	boolean str_flag=true,mkt_flag=true,tran_typ_flag=true, tran_hdr_flag=true,user_id_flag=true,
			job_step_flag=true,req_id_flag=true,obt_stat_flag=true;
	if(!(store_numbers.isEmpty())){
	//Fetching OBT SUSB Table
	//Forming Market Number from input
	
	query = "select * from obt_susp order by Trans_crt_ts desc FETCH FIRST 100 ROWS ONLY with ur ;";
	DB2_tables_connect(query);
	while(DB2RS.next()){
		count++;
		
		//validating DEST_ORG_KEY_VAL column - Store and market number
		DEST_ORG_KEY_VAL=DB2RS.getString("DEST_ORG_KEY_VAL");
		Table_str_nbr = DEST_ORG_KEY_VAL.substring(DEST_ORG_KEY_VAL.length()-4);
		for(int i=0;i>=3;i++)
		{
			if(DEST_ORG_KEY_VAL.substring(DEST_ORG_KEY_VAL.length()-4).charAt(i)=='0')
			{sub_string_count++;}
			else{break;}
		}
		
		//validating Store number
		//seperating preceeding Zero's for Store number from table value
		Table_str_nbr = Table_str_nbr.substring(sub_string_count);		
		if(!(store_numbers.contains(Table_str_nbr))){str_flag=false;}
		
		//validating market number
		Table_mkt_nbr = DEST_ORG_KEY_VAL.substring(DEST_ORG_KEY_VAL.length()-8,DEST_ORG_KEY_VAL.length()-4);
		if(!(Table_mkt_nbr.equalsIgnoreCase(Input_mkt_nbr))){mkt_flag=false;}
		
		//Validating TRANS_TYP_ID column - trigger type		
		if(!(DB2RS.getString("TRANS_TYP_ID").trim().equalsIgnoreCase("BISTIBPL"))){tran_typ_flag=false;}
		
		
		//validating TRANS_HDR_KEY_VAL column - SKU number		
		String TRANS_HDR_KEY_VAL=DB2RS.getString("TRANS_HDR_KEY_VAL");
		TRANS_HDR_KEY_VAL=TRANS_HDR_KEY_VAL.trim();
		if(!(TRANS_HDR_KEY_VAL.contains(dataTable.getData(DataColumn.sku_input)))){tran_hdr_flag=false;}
		
		
		//validating USER_ID column 		
		String USER_ID=DB2RS.getString("USER_ID");
		USER_ID=USER_ID.trim();
		if(!(USER_ID.equalsIgnoreCase(dataTable.getCommonData(CommonDataColumn.SignInUser)))){user_id_flag=false;}
		
		
		// validating DLOG_JOB_STEP_ID column  - Request status
		String DLOG_JOB_STEP_ID=DB2RS.getString("DLOG_JOB_STEP_ID");
		DLOG_JOB_STEP_ID=DLOG_JOB_STEP_ID.trim();
		if(!(DLOG_JOB_STEP_ID.equalsIgnoreCase("TRANSMIT"))){job_step_flag=false;}
		
		// validating DLOG_JOB_ID column - request_id
		String DLOG_JOB_ID=DB2RS.getString("DLOG_JOB_ID");
		DLOG_JOB_ID=DLOG_JOB_ID.trim();
		if(!(DLOG_JOB_ID.equalsIgnoreCase(request_id))){req_id_flag=false;}
				
		// validating OBT_STAT_IND column - request_id
		String OBT_STAT_IND=DB2RS.getString("OBT_STAT_IND");
		OBT_STAT_IND=OBT_STAT_IND.trim();
		if(!(OBT_STAT_IND.equalsIgnoreCase("WT"))){obt_stat_flag=false;}
				
		//Breaking the loop
		if(count==store_numbers.size()){break;}
		
		
	}
	}
	else{
		report.addReportStep("Store number should be present in STR tables",
				"No store number found", StepResult.FAIL);
		
	}
	DB2RS.close();
	stmt.close();
	con.close();
	
	//Store Validation adding report
	if(str_flag){
		report.addReportStep("Entry for all the STORE NUMBER should be present in OBT_SUSP table",
				"All assorted STORE NUMBER are present in OBT_SUSP table", StepResult.PASS);
	}
	else{
		report.addReportStep("Entry for all the STORE NUMBER should be present in OBT_SUSP table",
				"Invalid STORE NUMBER found in few records.", StepResult.FAIL);
	}
	//Market Validation adding report
	if(mkt_flag){
		report.addReportStep("MARKET NUMBER should be updated in OBT_SUSB tables",
				"MARKET NUMBER updated successfully for all the records in OBT_SUSP table", StepResult.PASS);
	}
	else{
		report.addReportStep("MARKET NUMBER should be updated in OBT_SUSB tables",
				"Invalid MARKET NUMBER found in few records", StepResult.FAIL);
	}
	//TRANS_TYP_ID - Trigger type Validation -  adding report
		if(tran_typ_flag){
			report.addReportStep("Valid TRIGGER TYPE should be updated in OBT_SUSB tables",
					"Valid TRIGGER TYPE is present for all the records in OBT_SUSP table", StepResult.PASS);
		}
		else{
			report.addReportStep("Valid TRIGGER TYPE should be updated in OBT_SUSB tables",
					"Invalid TRIGGER TYPE found in few records", StepResult.FAIL);
		}	
	
	//TRANS_HDR_EY_ID - SKU number validation - adding report
		if(tran_hdr_flag){
		
			report.addReportStep("Valid SKU NUMBER should be updated in OBT_SUSB tables",
					"Valid  SKU Invalid TRIGGER TYPE found in few records is present for all the records in OBT_SUSP table", StepResult.PASS);
		}
		else{
			report.addReportStep("Valid SKU NUMBER should be updated in OBT_SUSB tables",
					"Invalid SKU NUMBER found in few records", StepResult.FAIL);
		}
		
	//USER_ID column validating - adding report
		if(user_id_flag){
			report.addReportStep("Valid USER ID should be updated in OBT_SUSB tables",
					"Valid USER ID is present for all the records in OBT_SUSP table", StepResult.PASS);
		}
		else{
			report.addReportStep("Valid USER ID should be updated in OBT_SUSB tables",
					"Invalid USER ID found in few records", StepResult.FAIL);
		}
	
	// DLOG_JOB_STEP_ID validation - adding report 
	if(job_step_flag)
	{
		report.addReportStep("Valid REQUEST STATUS should be updated in OBT_SUSB tables",
				"Valid REQUEST STATUS is present for all the records in OBT_SUSP table", StepResult.PASS);
	}
	else{
		report.addReportStep("Valid REQUEST STATUS should be updated in OBT_SUSB tables",
				"Invalid REQUEST STATUS found in few records", StepResult.FAIL);
	}
	
	// DLOG_JOB_ID validation - adding report
	if(req_id_flag)
	{
		report.addReportStep("Valid REQUEST ID should be updated in OBT_SUSB tables",
				"Valid REQUEST ID is present for all the records in OBT_SUSP table", StepResult.PASS);
	}
	else{
		report.addReportStep("Valid REQUEST ID should be updated in OBT_SUSB tables",
				"Invalid REQUEST ID found in few records", StepResult.FAIL);
	}

	// OBT_STAT_IND validation - adding report
		if(obt_stat_flag)
		{
			report.addReportStep("Valid STATUS CODE should be updated in OBT_SUSB tables",
					"Valid STATUS CODE is present for all the records in OBT_SUSP table", StepResult.PASS);
		}
		else{
			report.addReportStep("Valid STATUS CODE should be updated in OBT_SUSB tables",
					"Invalid STATUS CODE found in few records", StepResult.FAIL);
		}
	}
	
public void OBT_table_validation_BISTIOC() throws Exception {
		
		List<String> store_numbers = new ArrayList<String>();
		
	//Getting Store assorted to the market
	String query = "SELECT STR_NBR FROM STR WHERE MKT_NBR =" +dataTable.getData(DataColumn.mkt)+" WITH UR;";
	DB2_tables_connect(query);
	try{
	while(DB2RS.next()){
		store_numbers.add(DB2RS.getString("STR_NBR"));		
	}
	System.out.println("Store number: "+store_numbers);	
	DB2RS.close();
	stmt.close();
	con.close();
	}
	catch(Exception e){
		
		report.addReportStep("Should fetch STORE number from STR table",
				"Error Fetching record", StepResult.FAIL);
	}
	
	String DEST_ORG_KEY_VAL,Table_str_nbr=null,Table_mkt_nbr=null,Input_mkt_nbr;
	Input_mkt_nbr = dataTable.getData(DataColumn.mkt);
	if(Input_mkt_nbr.length()==1){Input_mkt_nbr = "000"+Input_mkt_nbr;}
	else if(Input_mkt_nbr.length()==2){Input_mkt_nbr = "00"+Input_mkt_nbr;}
	else if(Input_mkt_nbr.length()==3){Input_mkt_nbr = "0"+Input_mkt_nbr;}
	System.out.println("Final market : "+Input_mkt_nbr);	
	int count=0,sub_string_count=0;
	boolean str_flag=true,mkt_flag=true,tran_typ_flag=true, tran_hdr_flag=true,tran_hdr_flag1=true,user_id_flag=true,
			job_step_flag=true,req_id_flag=true,obt_stat_flag=true;
	if(!(store_numbers.isEmpty())){
	//Fetching OBT SUSB Table
	//Forming Market Number from input
	
	query = "select * from obt_susp order by Trans_crt_ts desc FETCH FIRST 100 ROWS ONLY with ur ;";
	DB2_tables_connect(query);
	while(DB2RS.next()){
		count++;
		
		//validating DEST_ORG_KEY_VAL column - Store and market number
		DEST_ORG_KEY_VAL=DB2RS.getString("DEST_ORG_KEY_VAL");
		Table_str_nbr = DEST_ORG_KEY_VAL.substring(DEST_ORG_KEY_VAL.length()-4);
		for(int i=0;i>=3;i++)
		{
			if(DEST_ORG_KEY_VAL.substring(DEST_ORG_KEY_VAL.length()-4).charAt(i)=='0')
			{sub_string_count++;}
			else{break;}
		}
		
		//validating Store number
		//seperating preceeding Zero's for Store number from table value
		Table_str_nbr = Table_str_nbr.substring(sub_string_count);		
		if(!(store_numbers.contains(Table_str_nbr))){str_flag=false;}
		
		//validating market number
		Table_mkt_nbr = DEST_ORG_KEY_VAL.substring(DEST_ORG_KEY_VAL.length()-8,DEST_ORG_KEY_VAL.length()-4);
		if(!(Table_mkt_nbr.equalsIgnoreCase(Input_mkt_nbr))){mkt_flag=false;}
		
		//Validating TRANS_TYP_ID column - trigger type		
		if(!(DB2RS.getString("TRANS_TYP_ID").trim().equalsIgnoreCase("BISTIOC"))){tran_typ_flag=false;}
		
		
		//validating TRANS_HDR_KEY_VAL column - SKU number		
		String TRANS_HDR_KEY_VAL=DB2RS.getString("TRANS_HDR_KEY_VAL");
		TRANS_HDR_KEY_VAL=TRANS_HDR_KEY_VAL.trim();
		if(!(TRANS_HDR_KEY_VAL.contains(dataTable.getData(DataColumn.sku_input)))){tran_hdr_flag=false;}
		
		//validating TRANS_HDR_KEY_VAL column - MVNDR number		
	    if(!(TRANS_HDR_KEY_VAL.contains(dataTable.getData(DataColumn.mvndr_input)))){tran_hdr_flag1=false;}
		
		
		//validating USER_ID column 		
		String USER_ID=DB2RS.getString("USER_ID");
		USER_ID=USER_ID.trim();
		if(!(USER_ID.equalsIgnoreCase(dataTable.getCommonData(CommonDataColumn.SignInUser)))){user_id_flag=false;}
		
		
		// validating DLOG_JOB_STEP_ID column  - Request status
		String DLOG_JOB_STEP_ID=DB2RS.getString("DLOG_JOB_STEP_ID");
		DLOG_JOB_STEP_ID=DLOG_JOB_STEP_ID.trim();
		if(!(DLOG_JOB_STEP_ID.equalsIgnoreCase("TRANSMIT"))){job_step_flag=false;}
		
		// validating DLOG_JOB_ID column - request_id
		String DLOG_JOB_ID=DB2RS.getString("DLOG_JOB_ID");
		DLOG_JOB_ID=DLOG_JOB_ID.trim();
		if(!(DLOG_JOB_ID.equalsIgnoreCase(request_id))){req_id_flag=false;}
				
		// validating OBT_STAT_IND column - request_id
		String OBT_STAT_IND=DB2RS.getString("OBT_STAT_IND");
		OBT_STAT_IND=OBT_STAT_IND.trim();
		if(!(OBT_STAT_IND.equalsIgnoreCase("WT"))){obt_stat_flag=false;}
				
		//Breaking the loop
		if(count==store_numbers.size()){break;}
		
		
	}
	}
	else{
		report.addReportStep("Store number should be present in STR tables",
				"No store number found", StepResult.FAIL);
		
	}
	DB2RS.close();
	stmt.close();
	con.close();
	
	//Store Validation adding report
	if(str_flag){
		report.addReportStep("Entry for all the STORE NUMBER should be present in OBT_SUSP table",
				"All assorted STORE NUMBER are present in OBT_SUSP table", StepResult.PASS);
	}
	else{
		report.addReportStep("Entry for all the STORE NUMBER should be present in OBT_SUSP table",
				"Invalid STORE NUMBER found in few records.", StepResult.FAIL);
	}
	//Market Validation adding report
	if(mkt_flag){
		report.addReportStep("MARKET NUMBER should be updated in OBT_SUSB tables",
				"MARKET NUMBER updated successfully for all the records in OBT_SUSP table", StepResult.PASS);
	}
	else{
		report.addReportStep("MARKET NUMBER should be updated in OBT_SUSB tables",
				"Invalid MARKET NUMBER found in few records", StepResult.FAIL);
	}
	//TRANS_TYP_ID - Trigger type Validation -  adding report
		if(tran_typ_flag){
			report.addReportStep("Valid TRIGGER TYPE should be updated in OBT_SUSB tables",
					"Valid TRIGGER TYPE is present for all the records in OBT_SUSP table", StepResult.PASS);
		}
		else{
			report.addReportStep("Valid TRIGGER TYPE should be updated in OBT_SUSB tables",
					"Invalid TRIGGER TYPE found in few records", StepResult.FAIL);
		}	
	
	//TRANS_HDR_EY_ID - SKU number validation - adding report
		if(tran_hdr_flag){
		
			report.addReportStep("Valid SKU NUMBER should be updated in OBT_SUSB tables",
					"Valid  SKU Invalid TRIGGER TYPE found in few records is present for all the records in OBT_SUSP table", StepResult.PASS);
		}
		else{
			report.addReportStep("Valid SKU NUMBER should be updated in OBT_SUSB tables",
					"Invalid SKU NUMBER found in few records", StepResult.FAIL);
		}
		
	//TRANS_HDR_EY_ID -MVNDR number validation - adding report
			if(tran_hdr_flag1){
						
				report.addReportStep("Valid MVNDR NUMBER should be updated in OBT_SUSB tables",
									"Valid  MVNDR Invalid TRIGGER TYPE found in few records is present for all the records in OBT_SUSP table", StepResult.PASS);
			}
		     else{
				report.addReportStep("Valid MVNDR NUMBER should be updated in OBT_SUSB tables",
								"Invalid MVNDR NUMBER found in few records", StepResult.FAIL);
			}
		
	//USER_ID column validating - adding report
		if(user_id_flag){
			report.addReportStep("Valid USER ID should be updated in OBT_SUSB tables",
					"Valid USER ID is present for all the records in OBT_SUSP table", StepResult.PASS);
		}
		else{
			report.addReportStep("Valid USER ID should be updated in OBT_SUSB tables",
					"Invalid USER ID found in few records", StepResult.FAIL);
		}
	
	// DLOG_JOB_STEP_ID validation - adding report 
	if(job_step_flag)
	{
		report.addReportStep("Valid REQUEST STATUS should be updated in OBT_SUSB tables",
				"Valid REQUEST STATUS is present for all the records in OBT_SUSP table", StepResult.PASS);
	}
	else{
		report.addReportStep("Valid REQUEST STATUS should be updated in OBT_SUSB tables",
				"Invalid REQUEST STATUS found in few records", StepResult.FAIL);
	}
	
	// DLOG_JOB_ID validation - adding report
	if(req_id_flag)
	{
		report.addReportStep("Valid REQUEST ID should be updated in OBT_SUSB tables",
				"Valid REQUEST ID is present for all the records in OBT_SUSP table", StepResult.PASS);
	}
	else{
		report.addReportStep("Valid REQUEST ID should be updated in OBT_SUSB tables",
				"Invalid REQUEST ID found in few records", StepResult.FAIL);
	}

	// OBT_STAT_IND validation - adding report
		if(obt_stat_flag)
		{
			report.addReportStep("Valid STATUS CODE should be updated in OBT_SUSB tables",
					"Valid STATUS CODE is present for all the records in OBT_SUSP table", StepResult.PASS);
		}
		else{
			report.addReportStep("Valid STATUS CODE should be updated in OBT_SUSB tables",
					"Invalid STATUS CODE found in few records", StepResult.FAIL);
		}
	}

public void OBT_table_validation_BISTIBCL() throws Exception {
	
	List<String> store_numbers = new ArrayList<String>();
	
//Getting Store assorted to the market
String query = "SELECT STR_NBR FROM STR WHERE MKT_NBR =" +dataTable.getData(DataColumn.mkt)+" WITH UR;";
DB2_tables_connect(query);
try{
while(DB2RS.next()){
	store_numbers.add(DB2RS.getString("STR_NBR"));		
}
System.out.println("Store number: "+store_numbers);	
DB2RS.close();
stmt.close();
con.close();
}
catch(Exception e){
	
	report.addReportStep("Should fetch STORE number from STR table",
			"Error Fetching record", StepResult.FAIL);
}

String DEST_ORG_KEY_VAL,Table_str_nbr=null,Table_mkt_nbr=null,Input_mkt_nbr;
Input_mkt_nbr = dataTable.getData(DataColumn.mkt);
if(Input_mkt_nbr.length()==1){Input_mkt_nbr = "000"+Input_mkt_nbr;}
else if(Input_mkt_nbr.length()==2){Input_mkt_nbr = "00"+Input_mkt_nbr;}
else if(Input_mkt_nbr.length()==3){Input_mkt_nbr = "0"+Input_mkt_nbr;}
System.out.println("Final market : "+Input_mkt_nbr);	
int count=0,sub_string_count=0;
boolean str_flag=true,mkt_flag=true,tran_typ_flag=true, tran_hdr_flag=true,tran_hdr_flag1=true,user_id_flag=true,
		job_step_flag=true,req_id_flag=true,obt_stat_flag=true;
if(!(store_numbers.isEmpty())){
//Fetching OBT SUSB Table
//Forming Market Number from input

query = "select * from obt_susp order by Trans_crt_ts desc FETCH FIRST 100 ROWS ONLY with ur ;";
DB2_tables_connect(query);
while(DB2RS.next()){
	count++;
	
	//validating DEST_ORG_KEY_VAL column - Store and market number
	DEST_ORG_KEY_VAL=DB2RS.getString("DEST_ORG_KEY_VAL");
	Table_str_nbr = DEST_ORG_KEY_VAL.substring(DEST_ORG_KEY_VAL.length()-4);
	for(int i=0;i>=3;i++)
	{
		if(DEST_ORG_KEY_VAL.substring(DEST_ORG_KEY_VAL.length()-4).charAt(i)=='0')
		{sub_string_count++;}
		else{break;}
	}
	
	//validating Store number
	//seperating preceeding Zero's for Store number from table value
	Table_str_nbr = Table_str_nbr.substring(sub_string_count);		
	if(!(store_numbers.contains(Table_str_nbr))){str_flag=false;}
	
	//validating market number
	Table_mkt_nbr = DEST_ORG_KEY_VAL.substring(DEST_ORG_KEY_VAL.length()-8,DEST_ORG_KEY_VAL.length()-4);
	if(!(Table_mkt_nbr.equalsIgnoreCase(Input_mkt_nbr))){mkt_flag=false;}
	
	//Validating TRANS_TYP_ID column - trigger type		
	if(!(DB2RS.getString("TRANS_TYP_ID").trim().equalsIgnoreCase("BISTIBCL"))){tran_typ_flag=false;}
	
	
	//validating TRANS_HDR_KEY_VAL column - SKU number		
	String TRANS_HDR_KEY_VAL=DB2RS.getString("TRANS_HDR_KEY_VAL");
	TRANS_HDR_KEY_VAL=TRANS_HDR_KEY_VAL.trim();
	if(!(TRANS_HDR_KEY_VAL.contains(dataTable.getData(DataColumn.sku_input)))){tran_hdr_flag=false;}
	
	//validating TRANS_HDR_KEY_VAL column - MVNDR number		
    if(!(TRANS_HDR_KEY_VAL.contains(dataTable.getData(DataColumn.mvndr_input)))){tran_hdr_flag1=false;}
	
	
	//validating USER_ID column 		
	String USER_ID=DB2RS.getString("USER_ID");
	USER_ID=USER_ID.trim();
	if(!(USER_ID.equalsIgnoreCase(dataTable.getCommonData(CommonDataColumn.SignInUser)))){user_id_flag=false;}
	
	
	// validating DLOG_JOB_STEP_ID column  - Request status
	String DLOG_JOB_STEP_ID=DB2RS.getString("DLOG_JOB_STEP_ID");
	DLOG_JOB_STEP_ID=DLOG_JOB_STEP_ID.trim();
	if(!(DLOG_JOB_STEP_ID.equalsIgnoreCase("TRANSMIT"))){job_step_flag=false;}
	
	// validating DLOG_JOB_ID column - request_id
	String DLOG_JOB_ID=DB2RS.getString("DLOG_JOB_ID");
	DLOG_JOB_ID=DLOG_JOB_ID.trim();
	if(!(DLOG_JOB_ID.equalsIgnoreCase(request_id))){req_id_flag=false;}
			
	// validating OBT_STAT_IND column - request_id
	String OBT_STAT_IND=DB2RS.getString("OBT_STAT_IND");
	OBT_STAT_IND=OBT_STAT_IND.trim();
	if(!(OBT_STAT_IND.equalsIgnoreCase("WT"))){obt_stat_flag=false;}
			
	//Breaking the loop
	if(count==store_numbers.size()){break;}
	
	
}
}
else{
	report.addReportStep("Store number should be present in STR tables",
			"No store number found", StepResult.FAIL);
	
}
DB2RS.close();
stmt.close();
con.close();

//Store Validation adding report
if(str_flag){
	report.addReportStep("Entry for all the STORE NUMBER should be present in OBT_SUSP table",
			"All assorted STORE NUMBER are present in OBT_SUSP table", StepResult.PASS);
}
else{
	report.addReportStep("Entry for all the STORE NUMBER should be present in OBT_SUSP table",
			"Invalid STORE NUMBER found in few records.", StepResult.FAIL);
}
//Market Validation adding report
if(mkt_flag){
	report.addReportStep("MARKET NUMBER should be updated in OBT_SUSB tables",
			"MARKET NUMBER updated successfully for all the records in OBT_SUSP table", StepResult.PASS);
}
else{
	report.addReportStep("MARKET NUMBER should be updated in OBT_SUSB tables",
			"Invalid MARKET NUMBER found in few records", StepResult.FAIL);
}
//TRANS_TYP_ID - Trigger type Validation -  adding report
	if(tran_typ_flag){
		report.addReportStep("Valid TRIGGER TYPE should be updated in OBT_SUSB tables",
				"Valid TRIGGER TYPE is present for all the records in OBT_SUSP table", StepResult.PASS);
	}
	else{
		report.addReportStep("Valid TRIGGER TYPE should be updated in OBT_SUSB tables",
				"Invalid TRIGGER TYPE found in few records", StepResult.FAIL);
	}	

//TRANS_HDR_EY_ID - SKU number validation - adding report
	if(tran_hdr_flag){
	
		report.addReportStep("Valid SKU NUMBER should be updated in OBT_SUSB tables",
				"Valid  SKU Invalid TRIGGER TYPE found in few records is present for all the records in OBT_SUSP table", StepResult.PASS);
	}
	else{
		report.addReportStep("Valid SKU NUMBER should be updated in OBT_SUSB tables",
				"Invalid SKU NUMBER found in few records", StepResult.FAIL);
	}
	
//TRANS_HDR_EY_ID -MVNDR number validation - adding report
		if(tran_hdr_flag1){
					
			report.addReportStep("Valid MVNDR NUMBER should be updated in OBT_SUSB tables",
								"Valid  MVNDR Invalid TRIGGER TYPE found in few records is present for all the records in OBT_SUSP table", StepResult.PASS);
		}
	     else{
			report.addReportStep("Valid MVNDR NUMBER should be updated in OBT_SUSB tables",
							"Invalid MVNDR NUMBER found in few records", StepResult.FAIL);
		}
	
//USER_ID column validating - adding report
	if(user_id_flag){
		report.addReportStep("Valid USER ID should be updated in OBT_SUSB tables",
				"Valid USER ID is present for all the records in OBT_SUSP table", StepResult.PASS);
	}
	else{
		report.addReportStep("Valid USER ID should be updated in OBT_SUSB tables",
				"Invalid USER ID found in few records", StepResult.FAIL);
	}

// DLOG_JOB_STEP_ID validation - adding report 
if(job_step_flag)
{
	report.addReportStep("Valid REQUEST STATUS should be updated in OBT_SUSB tables",
			"Valid REQUEST STATUS is present for all the records in OBT_SUSP table", StepResult.PASS);
}
else{
	report.addReportStep("Valid REQUEST STATUS should be updated in OBT_SUSB tables",
			"Invalid REQUEST STATUS found in few records", StepResult.FAIL);
}

// DLOG_JOB_ID validation - adding report
if(req_id_flag)
{
	report.addReportStep("Valid REQUEST ID should be updated in OBT_SUSB tables",
			"Valid REQUEST ID is present for all the records in OBT_SUSP table", StepResult.PASS);
}
else{
	report.addReportStep("Valid REQUEST ID should be updated in OBT_SUSB tables",
			"Invalid REQUEST ID found in few records", StepResult.FAIL);
}

// OBT_STAT_IND validation - adding report
	if(obt_stat_flag)
	{
		report.addReportStep("Valid STATUS CODE should be updated in OBT_SUSB tables",
				"Valid STATUS CODE is present for all the records in OBT_SUSP table", StepResult.PASS);
	}
	else{
		report.addReportStep("Valid STATUS CODE should be updated in OBT_SUSB tables",
				"Invalid STATUS CODE found in few records", StepResult.FAIL);
	}
}
	
	
	
	
public void OBT_table_validation() throws Exception {
		
		List<String> store_numbers = new ArrayList<String>();
		
	//Getting Store assorted to the market
	String query = "SELECT STR_NBR FROM STR WHERE MKT_NBR =" +dataTable.getData(DataColumn.mkt)+" WITH UR;";
	DB2_tables_connect(query);
	try{
	while(DB2RS.next()){
		store_numbers.add(DB2RS.getString("STR_NBR"));		
	}
	System.out.println("Store number: "+store_numbers);	
	DB2RS.close();
	stmt.close();
	con.close();
	}
	catch(Exception e){
		
		report.addReportStep("Should fetch STORE number from STR table",
				"Error Fetching record", StepResult.FAIL);
	}
	
	String DEST_ORG_KEY_VAL,Table_str_nbr=null,Table_mkt_nbr=null,Input_mkt_nbr;
	Input_mkt_nbr = dataTable.getData(DataColumn.mkt);
	if(Input_mkt_nbr.length()==1){Input_mkt_nbr = "000"+Input_mkt_nbr;}
	else if(Input_mkt_nbr.length()==2){Input_mkt_nbr = "00"+Input_mkt_nbr;}
	else if(Input_mkt_nbr.length()==3){Input_mkt_nbr = "0"+Input_mkt_nbr;}
	System.out.println("Final market : "+Input_mkt_nbr);	
	int count=0,sub_string_count=0;
	boolean str_flag=true,mkt_flag=true,tran_typ_flag=true, tran_hdr_flag=true,user_id_flag=true,
			job_step_flag=true,req_id_flag=true,obt_stat_flag=true;
	if(!(store_numbers.isEmpty())){
	//Fetching OBT SUSB Table
	//Forming Market Number from input
	
	query = "select * from obt_susp order by Trans_crt_ts desc FETCH FIRST 100 ROWS ONLY with ur ;";
	DB2_tables_connect(query);
	while(DB2RS.next()){
		count++;
		
		//validating DEST_ORG_KEY_VAL column - Store and market number
		DEST_ORG_KEY_VAL=DB2RS.getString("DEST_ORG_KEY_VAL");
		Table_str_nbr = DEST_ORG_KEY_VAL.substring(DEST_ORG_KEY_VAL.length()-4);
		for(int i=0;i>=3;i++)
		{
			if(DEST_ORG_KEY_VAL.substring(DEST_ORG_KEY_VAL.length()-4).charAt(i)=='0')
			{sub_string_count++;}
			else{break;}
		}
		
		//validating Store number
		//seperating preceeding Zero's for Store number from table value
		Table_str_nbr = Table_str_nbr.substring(sub_string_count);		
		if(!(store_numbers.contains(Table_str_nbr))){str_flag=false;}
		
		//validating market number
		Table_mkt_nbr = DEST_ORG_KEY_VAL.substring(DEST_ORG_KEY_VAL.length()-8,DEST_ORG_KEY_VAL.length()-4);
		if(!(Table_mkt_nbr.equalsIgnoreCase(Input_mkt_nbr))){mkt_flag=false;}
		
		//Validating TRANS_TYP_ID column - trigger type		
		if(!(DB2RS.getString("TRANS_TYP_ID").trim().equalsIgnoreCase("BISTIO"))){tran_typ_flag=false;}
		
		
		//validating TRANS_HDR_KEY_VAL column - SKU number		
		String TRANS_HDR_KEY_VAL=DB2RS.getString("TRANS_HDR_KEY_VAL");
		TRANS_HDR_KEY_VAL=TRANS_HDR_KEY_VAL.trim();
		if(!(TRANS_HDR_KEY_VAL.contains(dataTable.getData(DataColumn.sku_input)))){tran_hdr_flag=false;}
		
		
		//validating USER_ID column 		
		String USER_ID=DB2RS.getString("USER_ID");
		USER_ID=USER_ID.trim();
		if(!(USER_ID.equalsIgnoreCase(dataTable.getCommonData(CommonDataColumn.SignInUser)))){user_id_flag=false;}
		
		
		// validating DLOG_JOB_STEP_ID column  - Request status
		String DLOG_JOB_STEP_ID=DB2RS.getString("DLOG_JOB_STEP_ID");
		DLOG_JOB_STEP_ID=DLOG_JOB_STEP_ID.trim();
		if(!(DLOG_JOB_STEP_ID.equalsIgnoreCase("TRANSMIT"))){job_step_flag=false;}
		
		// validating DLOG_JOB_ID column - request_id
		String DLOG_JOB_ID=DB2RS.getString("DLOG_JOB_ID");
		DLOG_JOB_ID=DLOG_JOB_ID.trim();
		if(!(DLOG_JOB_ID.equalsIgnoreCase(request_id))){req_id_flag=false;}
				
		// validating OBT_STAT_IND column - request_id
		String OBT_STAT_IND=DB2RS.getString("OBT_STAT_IND");
		OBT_STAT_IND=OBT_STAT_IND.trim();
		if(!(OBT_STAT_IND.equalsIgnoreCase("WT"))){obt_stat_flag=false;}
				
		//Breaking the loop
		if(count==store_numbers.size()){break;}
		
		
	}
	}
	else{
		report.addReportStep("Store number should be present in STR tables",
				"No store number found", StepResult.FAIL);
		
	}
	DB2RS.close();
	stmt.close();
	con.close();
	
	//Store Validation adding report
	if(str_flag){
		report.addReportStep("Entry for all the STORE NUMBER should be present in OBT_SUSP table",
				"All assorted STORE NUMBER are present in OBT_SUSP table", StepResult.PASS);
	}
	else{
		report.addReportStep("Entry for all the STORE NUMBER should be present in OBT_SUSP table",
				"Invalid STORE NUMBER found in few records.", StepResult.FAIL);
	}
	//Market Validation adding report
	if(mkt_flag){
		report.addReportStep("MARKET NUMBER should be updated in OBT_SUSB tables",
				"MARKET NUMBER updated successfully for all the records in OBT_SUSP table", StepResult.PASS);
	}
	else{
		report.addReportStep("MARKET NUMBER should be updated in OBT_SUSB tables",
				"Invalid MARKET NUMBER found in few records", StepResult.FAIL);
	}
	//TRANS_TYP_ID - Trigger type Validation -  adding report
		if(tran_typ_flag){
			report.addReportStep("Valid TRIGGER TYPE should be updated in OBT_SUSB tables",
					"Valid TRIGGER TYPE is present for all the records in OBT_SUSP table", StepResult.PASS);
		}
		else{
			report.addReportStep("Valid TRIGGER TYPE should be updated in OBT_SUSB tables",
					"Invalid TRIGGER TYPE found in few records", StepResult.FAIL);
		}	
	
	//TRANS_HDR_EY_ID - SKU number validation - adding report
		if(tran_hdr_flag){
		
			report.addReportStep("Valid SKU NUMBER should be updated in OBT_SUSB tables",
					"Valid  SKU Invalid TRIGGER TYPE found in few records is present for all the records in OBT_SUSP table", StepResult.PASS);
		}
		else{
			report.addReportStep("Valid SKU NUMBER should be updated in OBT_SUSB tables",
					"Invalid SKU NUMBER found in few records", StepResult.FAIL);
		}
		
	//USER_ID column validating - adding report
		if(user_id_flag){
			report.addReportStep("Valid USER ID should be updated in OBT_SUSB tables",
					"Valid USER ID is present for all the records in OBT_SUSP table", StepResult.PASS);
		}
		else{
			report.addReportStep("Valid USER ID should be updated in OBT_SUSB tables",
					"Invalid USER ID found in few records", StepResult.FAIL);
		}
	
	// DLOG_JOB_STEP_ID validation - adding report 
	if(job_step_flag)
	{
		report.addReportStep("Valid REQUEST STATUS should be updated in OBT_SUSB tables",
				"Valid REQUEST STATUS is present for all the records in OBT_SUSP table", StepResult.PASS);
	}
	else{
		report.addReportStep("Valid REQUEST STATUS should be updated in OBT_SUSB tables",
				"Invalid REQUEST STATUS found in few records", StepResult.FAIL);
	}
	
	// DLOG_JOB_ID validation - adding report
	if(req_id_flag)
	{
		report.addReportStep("Valid REQUEST ID should be updated in OBT_SUSB tables",
				"Valid REQUEST ID is present for all the records in OBT_SUSP table", StepResult.PASS);
	}
	else{
		report.addReportStep("Valid REQUEST ID should be updated in OBT_SUSB tables",
				"Invalid REQUEST ID found in few records", StepResult.FAIL);
	}

	// OBT_STAT_IND validation - adding report
		if(obt_stat_flag)
		{
			report.addReportStep("Valid STATUS CODE should be updated in OBT_SUSB tables",
					"Valid STATUS CODE is present for all the records in OBT_SUSP table", StepResult.PASS);
		}
		else{
			report.addReportStep("Valid STATUS CODE should be updated in OBT_SUSB tables",
					"Invalid STATUS CODE found in few records", StepResult.FAIL);
		}
	}
    
    



	
	public void DB2_tables_connect(String query) throws Exception {
		Class.forName("com.ibm.db2.jcc.DB2Driver");
		con = DriverManager.getConnection(db2q1_dbUrl, db2q1_username,
				db2q1_password);
		stmt = con.createStatement();
		DB2RS = stmt.executeQuery(query);
	} 
	 
}

