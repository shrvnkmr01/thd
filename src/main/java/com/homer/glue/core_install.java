package com.homer.glue;

import com.homer.dao.And;
import com.homer.dao.DataClass;

public class core_install extends BaseStepDefn{

	public core_install(DataClass data) {
		super(data);
		// TODO Auto-generated constructor stub
	}

	@And("^select core install request$")
	public void select_core_install_request() throws Throwable { 
	 // Write code here that turns the phrase above into concrete actions 
		install.select_CoreInstall_Request();
	}

    @And("^create a core install request$")
    public void create_a_core_install_request() throws Throwable { 
    // Write code here that turns the phrase above into concrete actions 
    	install.requesDetails();
    }   
    
    @And("^update excel with retail in cost-retail sheet and upload$")
    public void update_excel_with_retail_in_cost_retail_sheet_and_upload() throws Throwable { 
     // Write code here that turns the phrase above into concrete actions 
    	install.ExcelClear();
    	install.Retail_ExcelWrite();
    	install.ExcelUpload();
    }
    
    @And("^update excel with cost in cost-retail sheet and upload$")
    public void update_excel_with_cost_in_cost_retail_sheet_and_upload() throws Throwable { 
     // Write code here that turns the phrase above into concrete actions 
    	install.ExcelClear();
    	install.Cost_ExcelWrite();
    	install.ExcelUpload();
    }
    
    
    @And("^update excel with retail in sku-hdr sheet and upload$")
    public void update_excel_with_retail_in_sku_hdr_sheet_and_upload() throws Throwable { 
     // Write code here that turns the phrase above into concrete actions 
    	install.ExcelClear();
    	install.Retail_ExcelWrite_SkuHdr();
    	install.ExcelUpload();
    }
    
    @And("^update excel with cost in sku-hdr sheet and upload$")
    public void update_excel_with_cost_in_sku_hdr_sheet_and_upload() throws Throwable { 
     // Write code here that turns the phrase above into concrete actions 
    	install.ExcelClear();
    	install.Cost_ExcelWrite_SkuHdr();
    	install.ExcelUpload();
    }
    
    @And("^update excel with base option retail in sku-hdr sheet and upload$")
    public void update_excel_with_base_option_retail_in_sku_hdr_sheet_and_upload() throws Throwable { 
     // Write code here that turns the phrase above into concrete actions 
    	install.ExcelClear();
    	install.Baseoption_Retail_ExcelWrite();
    	install.ExcelUpload();
    }
    
    @And("^update excel with base option cost in sku-hdr sheet and upload$")
    public void update_excel_with_base_option_cost_in_sku_hdr_sheet_and_upload() throws Throwable { 
     // Write code here that turns the phrase above into concrete actions 
    	install.ExcelClear();
    	install.baseoption_Cost_ExcelWrite();
    	install.ExcelUpload();
    }
    

    
    @And("^Validate Retail triggers in OBT SUSP table_BISTIO$")
    public void validate_Retail_triggers_in_OBT_SUSP_table_BISTIO() throws Throwable { 
    // Write code here that turns the phrase above into concrete actions 
	 install.OBT_table_validation(); 
   }
 
    
   @And("^Validate cost triggers in OBT SUSP table_BISTIOC$")
   public void validate_cost_triggers_in_OBT_SUSP_table_BISTIOC() throws Throwable { 
    // Write code here that turns the phrase above into concrete actions 
	   install.OBT_table_validation_BISTIOC();
   }
   
   @And("^Validate Retail triggers in OBT SUSP table_BISTSKU$")
   public void validate_Retail_triggers_in_OBT_SUSP_table_BISTSKU() throws Throwable { 
    // Write code here that turns the phrase above into concrete actions 
	   install.OBT_table_validation_BISTSKU();
   }
  
   @And("^Validate Cost triggers in OBT SUSP table_BISTVSKU$")
   public void validate_Cost_triggers_in_OBT_SUSP_table_BISTVSKU() throws Throwable { 
    // Write code here that turns the phrase above into concrete actions 
	   install.OBT_table_validation_BISTVSKU();
   }
   
 
   @And("^Validate Retail triggers in OBT SUSP table_BISTIBPL$")
   public void validate_Retail_triggers_in_OBT_SUSP_table_BISTIBPL() throws Throwable { 
    // Write code here that turns the phrase above into concrete actions 
     install.OBT_table_validation_BISTIBPL();
   }
   
   @And("^Validate Cost triggers in OBT SUSP table_BISTIBCL$")
   public void validate_Cost_triggers_in_OBT_SUSP_table_BISTIBCL() throws Throwable { 
    // Write code here that turns the phrase above into concrete actions 
	   install.OBT_table_validation_BISTIBCL();
   }

   
   @And("^Validate DB retail tables$")
   public void validate_DB_retail_tables() throws Throwable { 
    // Write code here that turns the phrase above into concrete actions 
	   DBValidations.DB2_PERM_SKU_STR_GRP_OPT_RETL();
       DBValidations.DB2_EFF_SKU_STR_GRP_OPT_RETL();
       DBValidations.DB2_EFF_SKU_STR_GRP_OPT_RETL_CNTRB();
       DBValidations.Oracle_SKU_CHG_RQST_validations();
      
   }
   
   @And("^Validate DB cost tables$")
   public void validate_DB_cost_tables() throws Throwable { 
    // Write code here that turns the phrase above into concrete actions 
	   DBValidations.DB2_PERM_SKU_STR_GRP_OPT_COST();
	   DBValidations.DB2_EFF_SKU_STR_GRP_OPT_COST();
	   DBValidations.DB2_EFF_SKSTR_GRP_OPT_COST_CNTRB();
	   DBValidations.Oracle_SKU_CHG_RQST_validations();
   }

    
    
    
    
    
    
    
    
}
