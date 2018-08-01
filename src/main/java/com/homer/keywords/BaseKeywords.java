package com.homer.keywords;

import org.openqa.selenium.WebDriver;

import com.homer.dao.CommonData;
import com.homer.dao.DataClass;
import com.homer.dao.InstanceContainer;
import com.homer.helper.DataTable;
import com.homer.reports.Report;
import com.homer.resuablecomponents.ReportUtil;
import com.homer.resuablecomponents.ReusableComponents;
import com.homer.resuablecomponents.WebDriverHelper;

public class BaseKeywords {

	protected Report report;
	protected DataTable dataTable;
	
	protected WebDriverHelper wh;
	protected DataClass data;
	
	protected WebDriver driver;
	protected ReusableComponents rc;
	protected InstanceContainer ic;
	
	CommonData commonData;	
	
	
	protected ReportUtil rptUtil;
	
	public BaseKeywords(DataClass data) {		
	
		this.data = data;
		this.driver = data.driver;
		this.report = data.report;
		this.dataTable = data.dataTable;		
		this.commonData = (CommonData)data.commonData;
		
		wh = new WebDriverHelper(driver, report, dataTable);
		rc = new ReusableComponents(driver, report, wh, dataTable);
		
		ic = new InstanceContainer(driver, report,dataTable, wh, rc,commonData);
		
		if(data.tools!=null) {
			
			driver = (WebDriver)data.tools;
			rptUtil = new ReportUtil(driver,report, data);
		}
		
	}
}
