package com.homer.executionclasses;

import java.util.HashMap;

import com.homer.dao.BaseExecutionClass;
import com.homer.dao.DataColumn;
import com.homer.dao.InstanceContainer;
import com.homer.enums.EnumClass.StepResult;
import com.homer.model.TestCase;
import com.homer.resuablecomponents.ReusableComponents;
import com.homer.resuablecomponents.WebDriverHelper;

public class Search extends BaseExecutionClass {

	ReusableComponents rc;
	WebDriverHelper wh;
	InstanceContainer ic;
	TestCase testCase;
	
	public Search(TestCase currentTestCase) throws Exception {
		super(currentTestCase);

		this.testCase = currentTestCase;
		wh = new WebDriverHelper(driver, report, dataTable,
				currentTestCase.browser);
		
		rc = new ReusableComponents(driver, report, wh, dataTable);
		
		ic = new InstanceContainer(driver, report, dataTable, wh, rc);
	}	
	
	/**
	 *Method to search keyword
	 * @throws Exception
	 */
	void searchKeyword() throws Exception {
		
		System.out.println("Test case name " +testCase.testCaseName);
		System.out.println("Report folder name " +testCase.reportFolder);
		System.out.println("Screenshot folder name "+testCase.almScreenShotPath);
		
		
		//homePage.open();
		//homePage.searchKeyword(dataTable.getData(DataColumn.Keyword));
		
	}
	
	/**
	 * Method for test cases - Test Search1
	 * @throws Exception
	 */
	public void test_Search1() throws Exception {
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		System.out.println("Non BDD execution Test search1");
		
		throw new NullPointerException();
		/*map.get("ghghjg");
		searchKeyword();*/
	}
	
	/**
	* Method for test case - Test Case 1 update in test suite 1
	* @throws Exception
	*/
	public void test_Case_1_update_in_test_suite_1() throws Exception { 
	 
		report.addReportStep("Test ALM", 
				"ALM test",
				StepResult.PASS);
	}
}
