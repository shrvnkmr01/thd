package com.homer.glue;

import org.openqa.selenium.WebDriver;

import com.homer.dao.CommonData;
import com.homer.dao.DataClass;
import com.homer.dao.InstanceContainer;
import com.homer.helper.DataTable;
import com.homer.po.*;
import com.homer.reports.Report;
import com.homer.resuablecomponents.ReportUtil;
import com.homer.resuablecomponents.ReusableComponents;
import com.homer.resuablecomponents.WebDriverHelper;

public class BaseStepDefn {
	
	protected Report report;
	protected DataTable dataTable;
	protected WebDriverHelper wh;
	protected DataClass data;
	protected WebDriver driver;
	protected ReusableComponents rc;
	protected InstanceContainer ic;
	CommonData commonData;
	protected WebDriver newWebDriver;
	protected String testTxt;
	protected ReportUtil rptUtil;
	
	protected HDI_Product HDI_Product;
	protected DBValidations DBValidations;
	protected HDI_Services HDI_Services; 
	protected core_install_OBT install; 
	

	public BaseStepDefn(DataClass data) {	
		
		this.data = data;
		this.driver = data.driver;
		this.report = data.report;
		this.dataTable = data.dataTable;		
		this.commonData = (CommonData)data.commonData;
	
		wh = new WebDriverHelper(driver, report, dataTable);
		rc = new ReusableComponents(driver, report, wh, dataTable, data);
		ic = new InstanceContainer(driver, report,dataTable, wh, rc,commonData);
		
		HDI_Product = new HDI_Product(ic);
		install = new core_install_OBT(ic);
		DBValidations = new DBValidations(ic);
		HDI_Services = new HDI_Services(ic);
		
		if(data.tools!=null) {			
			driver = (WebDriver)data.tools;
			rptUtil = new ReportUtil(driver,report, data);
		}	
	}
}
