package com.homer.glue;

import com.homer.dao.And;
import com.homer.dao.CommonDataColumn;
import com.homer.dao.DataClass;
import com.homer.dao.Given;
import com.homer.dao.Then;

public class Login_common extends BaseStepDefn {

	public Login_common(DataClass data) {
		super(data);
	}

	@Given("^Login detail of application$")
	public void login_detail_of_application() throws Throwable {
		HDI_Product.openApplicationUrl();
		HDI_Product.loginToApplication();
	}

	@And("^select HDI product request$")
	public void select_HDI_product_request() throws Throwable {
		HDI_Product.select_HdiProduct_Request();
	}

	@And("^create a Hdi product request$")
	public void create_a_Hdi_product_request() throws Throwable {
		HDI_Product.requesDetails();
		HDI_Product.ExcelWrite();
		HDI_Product.ExcelUpload();
	}

	@And("^Submit the Request$")
	public void submit_the_Request() throws Throwable {
		HDI_Product.SubmitRequest();
	}

	@Then("^validate the request in DB(\\d+) tables$")
	public void validate_the_request_in_DB2_tables() throws Throwable {
		DBValidations.DB2_PERM_SKU_STR_GRP_RETL();
		DBValidations.DB2_PERM_SKU_STR_GRP_COST();
		DBValidations.DB2_PC_TRNSM_LOG();
		DBValidations.DB2_CC_TRNSM_LOG();
	}

	@Then("^validate Oracle tables$")
	public void validate_Oracle_tables() throws Throwable {
		DBValidations.Oracle_SKU_CHG_RQST_validations();
		DBValidations.Oracle_COST_RETL_CHG_RQST_validations();
	}
	
	@Then("^Cassandra services for Retail and Cost$")
	public void cassandra_services_for_Retail_and_Cost() throws Throwable { 
		HDI_Services.read_store_pricingzoneWS();
		HDI_Services.validate_cassandra_RetailWS();
		HDI_Services.validate_cassandra_CostWS();
	}
}
