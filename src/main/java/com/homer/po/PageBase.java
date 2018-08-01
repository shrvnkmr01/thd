package com.homer.po;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.homer.dao.CommonData;
import com.homer.dao.InstanceContainer;
import com.homer.helper.DataTable;
import com.homer.reports.Report;
import com.homer.resuablecomponents.ReusableComponents;
import com.homer.resuablecomponents.WebDriverHelper;

public class PageBase <CHILD extends PageBase<CHILD>>{
	
	protected InstanceContainer ic;
	protected WebDriver driver;
	protected WebDriverHelper wh;
	protected DataTable dataTable;
	protected ReusableComponents rc;
	protected Report report;
	protected CommonData commonData;
	protected boolean expectedResult;
	protected boolean isListViewSelected;
	
	public PageBase(InstanceContainer ic) {
		
		this.ic = ic;
		this.driver = ic.driver;
		this.wh = ic.wh;
		this.rc = ic.rc;
		this.dataTable = ic.dataTable;
		this.report = ic.report;
		this.commonData = ic.commonData;
	}
	
	//Page Contents
	public static final By userName = By.xpath("//label[text()='User ID']/following-sibling::input");
	public static final By password = By.xpath("//label[text()='Password']/following-sibling::input");
	public static final By loginButton = By.xpath("//button[@class='button primary']");
	public static final By newRequest = By.xpath("//*[@id='requestBtn']");
	public static final By productRequest = By.xpath("//*[@id='labor']/a");
	public static final By InstallRequest = By.xpath("//*[@id='instl']/a");
	public static final By upload_progressbar = By.xpath("//*[text()='Upload In Progress']");	
	public static final By requestDesc = By.xpath(".//input[@id='request-title']");
	public static final By requestTitle = By.xpath("//*[@id='reqtxt']");
	public static final By OrangeID = By.xpath("//*[@id='orgidtxt']");
	public static final By upload_button = By.xpath("//input[@type='file']"); 
	public static final By browse_button = By.xpath("//*[text()='Browse']");
	public static final By uploadClick = By.xpath("//*[@id='uploadButton']");
	public static final By dialog_close = By.xpath("//*[contains(@id,'close')]");
	public static final By req_status = By.xpath(".//td[2]");
	public static final By req_id = By.xpath(".//td[1]");
	public static final By submitted_date = By.xpath(".//td[5]");
	public static final By submit = By.xpath(".//*[@id='submitButton']");
	public static String request_id;
	public static final By pageTitle = By.xpath(".//*[@class='page-title']");
	public static String SKU_excel,market_nbr_excel,option_nbr_excel,mvndr_nbr_excel;
	public static int  SKU;
	public static int market_nbr,option_nbr,mvndr_nbr;
	public static double retail_price;
	public static double cost_price,cost_price_input;
	public static String datesubmitted;	
	public static String DBeffectivedate;
	public static String Department;
	public static String vendor;
	public static String zone;
	public static Date date;
	public static int Store;
	public static String EFF_BGN_TS = null;
	public static double NEW_RETL_AMT = 0;
	public static double NEW_COST_AMT = 0;
	
	public static String db2q1_dbUrl = "jdbc:db2://ibdynw1.sysplex.homedepot.com:5325/NW1";
	public static String db2q1_username = "Q11MM67";		
	public static String db2q1_password = "67MM11Q";
	
	public static String Oracle_dbUrl = "jdbc:oracle:thin:@//pnpmm77z.homedepot.com:1521/dqa19ss_rw_base_01";
	public static String Oracle_username = "PRCNAP11";		
	public static String Oracle_password = "PUh6spVx";
	
	public static Statement stmt;            
	public static ResultSet OracleRS;
	public static ResultSet DB2RS;
	public static Connection con;
	public static Workbook wb;
	
}
