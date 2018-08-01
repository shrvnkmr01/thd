package com.homer.po;

import java.io.File;
import java.io.FileInputStream;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.homer.dao.DataColumn;
import com.homer.dao.InstanceContainer;
import com.homer.enums.EnumClass.StepResult;

public class DBValidations extends PageBase{

	public DBValidations(InstanceContainer ic) {
		super(ic);
	}

	public void Excel_updatedvalues() throws Exception {
		
		FileInputStream excelFile = new FileInputStream(
				new File(System.getProperty("user.dir") + "\\Input_Excel\\HDI_Product_SKU.xlsx"));
		wb = new XSSFWorkbook(excelFile);
		try{
			Department = wb.getSheetAt(0).getRow(1).getCell(0).toString();
			String sku= wb.getSheetAt(0).getRow(1).getCell(3).toString();
			//SKU = Integer.parseInt(sku);
			String retail =  wb.getSheetAt(0).getRow(1).getCell(12).toString();
			retail_price = Double.parseDouble(retail);
			String cost =  wb.getSheetAt(0).getRow(1).getCell(10).toString();
			cost_price = Double.parseDouble(cost);
			vendor = wb.getSheetAt(0).getRow(1).getCell(7).toString();
			zone = wb.getSheetAt(0).getRow(1).getCell(5).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String dbdateparse(String input) throws Exception {
		
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = parser.parse(input);
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        DBeffectivedate = formatter.format(date);
        
        return DBeffectivedate;
	}

	public void DB2_PERM_SKU_STR_GRP_RETL() throws Exception {
		
		try {
			DB2_tables_connect("select SKU_NBR,EFF_BGN_TS,RETL_AMT from PERM_SKU_STR_GRP_RETL where "
					+ "SKU_CHG_RQST_ID in ("+request_id +") with ur;");

			int SKU_NBR = 0;
			String EFF_BGN_TS = null;
			double RETL_AMT = 0;
			
			while (DB2RS.next()) {
				SKU_NBR  = DB2RS.getInt("SKU_NBR");
				EFF_BGN_TS = DB2RS.getString("EFF_BGN_TS");
				RETL_AMT = Math.round(DB2RS.getDouble("RETL_AMT") * 100.0) / 100.0;
			}
			Excel_updatedvalues();
			dbdateparse(EFF_BGN_TS);

			if (SKU==SKU_NBR && DBeffectivedate.equals(datesubmitted) && RETL_AMT == retail_price) {
				report.addReportStep("Verify Retail is updated in PERM_SKU_STR_GRP_RETL for the given requestID "+request_id+" with a retail of $"+RETL_AMT+" for SKU"+SKU_NBR+" " , 
						"Retail is updated in PERM_SKU_STR_GRP_RETL table for the given requestID "+request_id+" ",StepResult.PASS);
			} else {
				report.addReportStep("Verify Retail is updated in PERM_SKU_STR_GRP_RETL for the given requestID "+request_id+" with a retail of $"+RETL_AMT+" for SKU"+SKU_NBR+" ", 
						"Retail is not updated in PERM_SKU_STR_GRP_RETL table for the given requestID "+request_id+" ",StepResult.FAIL);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		DB2RS.close();
		stmt.close();
		con.close();
	}

	public void DB2_PERM_SKU_STR_GRP_COST() throws Exception {
		try {
			DB2_tables_connect("select * from PERM_SKU_STR_GRP_COST where "
					+ "SKU_CHG_RQST_ID in ("+request_id +") with ur;");

			int SKU_NBR = 0;
			String EFF_BGN_TS = null;
			double COST_AMT = 0;
			String MVNDR_NBR=null;
			
			while (DB2RS.next()) {
				SKU_NBR  = DB2RS.getInt("SKU_NBR");
				EFF_BGN_TS = DB2RS.getString("EFF_BGN_TS");
				MVNDR_NBR = DB2RS.getString("MVNDR_NBR");
				COST_AMT = DB2RS.getDouble("COST_AMT");
			}
			Excel_updatedvalues();
			dbdateparse(EFF_BGN_TS);

			if ((SKU==SKU_NBR && DBeffectivedate.equals(datesubmitted) && 
					COST_AMT == cost_price && MVNDR_NBR.equals(vendor)))    {
				report.addReportStep("Verify Cost is updated in PERM_SKU_STR_GRP_COST for the given requestID "+request_id+" with a cost of $"+COST_AMT+" for SKU "+SKU_NBR+" with Mvendor "+MVNDR_NBR+" " , 
						"Cost is updated in PERM_SKU_STR_GRP_COST table for the given requestID "+request_id+" ",StepResult.PASS);
			} else {
				report.addReportStep("Verify Cost is updated in PERM_SKU_STR_GRP_COST for the given requestID "+request_id+" with a cost of $"+COST_AMT+" for SKU "+SKU_NBR+" with Mvendor "+MVNDR_NBR+" ", 
						"Cost is not updated in PERM_SKU_STR_GRP_COST table for the given requestID "+request_id+" ",StepResult.FAIL);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		DB2RS.close();
		stmt.close();
		con.close();
	}

	public void DB2_PC_TRNSM_LOG() throws Exception {
		try {
			DB2_tables_connect("select * from PC_TRNSM_LOG where "
					+ "SKU_CHG_RQST_ID in ("+request_id+") with ur;");

			int SKU_NBR = 0;
			String NEW_RETL_EFF_TS = null;
			
			while (DB2RS.next()) {
				SKU_NBR  = DB2RS.getInt("SKU_NBR");
				NEW_RETL_EFF_TS = DB2RS.getString("NEW_RETL_EFF_TS");
				NEW_RETL_AMT = Math.round(DB2RS.getDouble("NEW_RETL_AMT") * 100.0) / 100.0;
			}
			Excel_updatedvalues();
			dbdateparse(NEW_RETL_EFF_TS);

			if (SKU==SKU_NBR && DBeffectivedate.equals(datesubmitted) && NEW_RETL_AMT == retail_price ) {
				report.addReportStep("Verify Retail is updated in PC_TRNSM_LOG for the given requestID "+request_id+" with a retail of $ "+NEW_RETL_AMT+" for SKU "+SKU_NBR+" "
						+ "" , "Retail is updated in PC_TRNSM_LOG table for the given requestID "+request_id+" ",StepResult.PASS);
			} else {
				report.addReportStep("Verify Retail is updated in PC_TRNSM_LOG for the given requestID "+request_id+" with a retail of $ "+NEW_RETL_AMT+" for SKU "+SKU_NBR+" ", 
						"Retail is NOT updated in PC_TRNSM_LOG table for the given requestID "+request_id+" ",StepResult.FAIL);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		DB2RS.close();
		stmt.close();
		con.close();
	}

	public void DB2_CC_TRNSM_LOG() throws Exception {
		try {
			DB2_tables_connect(
					"select * from CC_TRNSM_LOG where "
					+ "SKU_CHG_RQST_ID in ("+request_id+") with ur;");

			int SKU_NBR = 0;
			String NEW_COST_EFF_TS = null;
			String MVNDR_NBR=null;
			
			while (DB2RS.next()) {
				SKU_NBR  = DB2RS.getInt("SKU_NBR");
				NEW_COST_EFF_TS = DB2RS.getString("NEW_COST_EFF_TS");
				MVNDR_NBR = DB2RS.getString("MVNDR_NBR");
				NEW_COST_AMT = DB2RS.getDouble("NEW_COST_AMT");
			}
			Excel_updatedvalues();
			dbdateparse(NEW_COST_EFF_TS);

			if ((SKU==SKU_NBR && DBeffectivedate.equals(datesubmitted) && 
					(NEW_COST_AMT==cost_price) && 
					(MVNDR_NBR.equals( vendor) )))    {
				report.addReportStep("Verify Cost is updated in CC_TRNSM_LOG for the given requestID "+request_id+" with a cost of $ "+NEW_COST_AMT+" for SKU "+SKU_NBR+" with MVendor "+MVNDR_NBR+" " , 
						"Cost is updated in CC_TRNSM_LOG table for the given requestID "+request_id+" ",StepResult.PASS);
			} else {
				report.addReportStep("Verify Cost is updated in CC_TRNSM_LOG for the given requestID "+request_id+" with a cost of $ "+NEW_COST_AMT+" for SKU "+SKU_NBR+" with MVendor "+MVNDR_NBR+" ", 
						"Cost is not updated in CC_TRNSM_LOG table for the given requestID "+request_id+" ",StepResult.FAIL);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		DB2RS.close();
		stmt.close();
		con.close();
	}

	public void Oracle_SKU_CHG_RQST_validations() throws Exception {
		
		try {
			Oracle_tables_connect("select * from SKU_CHG_RQST where SKU_CHG_RQST_ID  in ("+request_id+")");
			String SKU_CHG_RQST_ID = null;
			String SKCHG_RQST_STAT_CD = null;
				
			while (OracleRS.next()) {
				EFF_BGN_TS	= OracleRS.getString("EFF_BGN_TS");
				SKU_CHG_RQST_ID = OracleRS.getString("SKU_CHG_RQST_ID");
				SKCHG_RQST_STAT_CD = OracleRS.getString("SKCHG_RQST_STAT_CD");
			}			
			dbdateparse(EFF_BGN_TS);	
			if (request_id.equals(SKU_CHG_RQST_ID) && DBeffectivedate.equals(datesubmitted)) {
				report.addReportStep(
						"Verify whether the submitted record is transmitted in Oracle SCR table",
						"Submitted request is present in Oracle DB, SKU_CHG_RQST_ID: "+SKU_CHG_RQST_ID+""
								+ "EFF_BGN_TS: "+EFF_BGN_TS+", SKCHG_RQST_STAT_CD: "+SKCHG_RQST_STAT_CD+"", 
								StepResult.PASS);
			} else {
				report.addReportStep(
						"Verify whether the submitted record is transmitted in Oracle SCR table",
						"Unable to find the request submitted",
						StepResult.FAIL);
			}	
			OracleRS.close();
			stmt.close();
			con.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	} 
	
	public void Oracle_COST_RETL_CHG_RQST_validations() throws Exception {
		
		try {
			Oracle_tables_connect("select * from COST_RETL_CHG_RQST where SKU_CHG_RQST_ID  in ("+request_id+")");
			String SKU_CHG_RQST_ID = null;
			String SCRQ_DTL_STAT_CD = null;
			String NEW_RETL_AMT = null, NEW_COST_AMT = null, SKU_NBR = null, EFF_BGN_TS = null, BUS_STR_GRP_ID = null;
				
			while (OracleRS.next()) {
				SKU_CHG_RQST_ID = OracleRS.getString("SKU_CHG_RQST_ID");
				SCRQ_DTL_STAT_CD = OracleRS.getString("SCRQ_DTL_STAT_CD");
				NEW_RETL_AMT = OracleRS.getString("NEW_RETL_AMT");
				NEW_COST_AMT = OracleRS.getString("NEW_COST_AMT");
				SKU_NBR = OracleRS.getString("SKU_NBR");
				EFF_BGN_TS = OracleRS.getString("EFF_BGN_TS");
				BUS_STR_GRP_ID = OracleRS.getString("BUS_STR_GRP_ID");				
			}			
			dbdateparse(EFF_BGN_TS);
			if (request_id.equals(SKU_CHG_RQST_ID) && DBeffectivedate.equals(datesubmitted)) {
				report.addReportStep(
						"Verify whether the submitted record is transmitted in Oracle CRC table",
						"Submitted request is present in Oracle DB, SKU_CHG_RQST_ID: "+SKU_CHG_RQST_ID+", "
								+ "EFF_BGN_TS: "+EFF_BGN_TS+", SKCHG_RQST_STAT_CD: "+SCRQ_DTL_STAT_CD+", "
										+ "SKU_NBR:"+SKU_NBR+", NEW_RETL_AMT: "+NEW_RETL_AMT+", NEW_COST_AMT:"+NEW_COST_AMT+", "
												+ "BUS_STR_GRP_ID:"+BUS_STR_GRP_ID+"", StepResult.PASS);
			} else {
				report.addReportStep(
						"Verify whether the submitted record is transmitted in Oracle CRC table",
						"Unable to find the request submitted",
						StepResult.FAIL);
			}	
			OracleRS.close();
			stmt.close();
			con.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	
	//DB2 Validation Core Install
	
	public void DB2_PERM_SKU_STR_GRP_OPT_RETL() throws Exception {
        
        try {
                        System.out.println("request id:" +request_id);
                        DB2_tables_connect("SELECT SKU_NBR,STR_GRP_ID,OPT_NBR,EFF_BGN_TS,RETL_AMT FROM PERM_SKU_STR_GRP_OPT_RETL WHERE "
                                                        + "SKU_CHG_RQST_ID in ("+request_id+") with ur;");       
                        

                        int SKU_NBR = 0,MKT_NBR=0,OPT_NBR=0;
                        String EFF_BGN_TS = null;
                        double RETL_AMT = 0;
                        
                        
                        while (DB2RS.next()) {
                                        SKU_NBR  = DB2RS.getInt("SKU_NBR");
                                        EFF_BGN_TS = DB2RS.getString("EFF_BGN_TS");
                                        RETL_AMT = Math.round(DB2RS.getDouble("RETL_AMT") * 100.0) / 100.0;
                                        MKT_NBR =DB2RS.getInt("STR_GRP_ID");
                                        OPT_NBR=DB2RS.getInt("OPT_NBR");
                                                        
                        }
                        Excel_updatedvalues_coreInstall();
                        dbdateparse(EFF_BGN_TS);
                        
                        System.out.println("retail_price :"+retail_price);

                        if (SKU==SKU_NBR && market_nbr==MKT_NBR && option_nbr==OPT_NBR && DBeffectivedate.equals(datesubmitted) && RETL_AMT == core_install_OBT.retail_price) {
                                        report.addReportStep("Verify Retail is updated in PERM_SKU_STR_GRP_OPT_RETL for the given requestID "+request_id+" with a retail of $"+core_install_OBT.retail_price+" for SKU "+ SKU+"" , 
                                                                        "Retail is updated in PERM_SKU_STR_GRP_OPT_RETL table for the given requestID "+request_id+" with a retail of $"+RETL_AMT+" for SKU "+ SKU_NBR+" ",StepResult.PASS);
                        } else {
                                        report.addReportStep("Verify Retail is updated in PERM_SKU_STR_GRP_OPT_RETL for the given requestID "+request_id+" with a retail of $"+core_install_OBT.retail_price+" for SKU "+ SKU+"", 
                                                                        "Retail is not updated in PERM_SKU_STR_GRP_OPT_RETL table for the given requestID "+request_id+" with a retail of $"+RETL_AMT+" for SKU "+ SKU_NBR+" ",StepResult.FAIL);
                        }

        } catch (Exception e) {
                        e.printStackTrace();
        }
        DB2RS.close();
        stmt.close();
        con.close();
}
	
	public void DB2_EFF_SKU_STR_GRP_OPT_RETL() throws Exception {
        
        try {
                        System.out.println("request id:" +request_id);
                        DB2_tables_connect("SELECT SKU_NBR,STR_GRP_ID,OPT_NBR,EFF_BGN_TS,RETL_AMT FROM EFF_SKU_STR_GRP_OPT_RETL WHERE "
                                                        + "RETL_AMT in ("+core_install_OBT.retail_price+") with ur;");       
                        

                        int SKU_NBR = 0,MKT_NBR=0,OPT_NBR=0;
                        String EFF_BGN_TS = null;
                        double RETL_AMT = 0;
                        
                        
                        while (DB2RS.next()) {
                                        SKU_NBR  = DB2RS.getInt("SKU_NBR");
                                        EFF_BGN_TS = DB2RS.getString("EFF_BGN_TS");
                                        RETL_AMT = Math.round(DB2RS.getDouble("RETL_AMT") * 100.0) / 100.0;
                                        MKT_NBR =DB2RS.getInt("STR_GRP_ID");
                                        OPT_NBR=DB2RS.getInt("OPT_NBR");
                                                        
                        }
                        Excel_updatedvalues_coreInstall();
                        dbdateparse(EFF_BGN_TS);

                        if (SKU==SKU_NBR && market_nbr==MKT_NBR && option_nbr==OPT_NBR && DBeffectivedate.equals(datesubmitted) && RETL_AMT == core_install_OBT.retail_price) {
                                        report.addReportStep("Verify Retail is updated in EFF_SKU_STR_GRP_OPT_RETL for the given requestID "+request_id+" with a retail of $"+core_install_OBT.retail_price+" for SKU "+ SKU+" " , 
                                                                        "Retail is updated in EFF_SKU_STR_GRP_OPT_RETL table for the given requestID "+request_id+" with a retail of $"+RETL_AMT+" for SKU "+ SKU_NBR+"",StepResult.PASS);
                        } else {
                                        report.addReportStep("Verify Retail is updated in EFF_SKU_STR_GRP_OPT_RETL for the given requestID "+request_id+" with a retail of $"+core_install_OBT.retail_price+" for SKU "+ SKU+" ", 
                                                                        "Retail is not updated in EFF_SKU_STR_GRP_OPT_RETL table for the given requestID "+request_id+" with a retail of $"+RETL_AMT+" for SKU "+ SKU_NBR+"",StepResult.FAIL);
                        }

        } catch (Exception e) {
                        e.printStackTrace();
        }
        DB2RS.close();
        stmt.close();
        con.close();
}
	
	public void DB2_EFF_SKU_STR_GRP_OPT_RETL_CNTRB() throws Exception {
        
        try {
                        System.out.println("request id;" +request_id);
                        DB2_tables_connect("SELECT SKU_NBR,STR_GRP_ID,OPT_NBR,EFF_BGN_TS,RETL_AMT FROM EFF_SKU_STR_GRP_OPT_RETL_CNTRB WHERE "
                                                        + "SKU_CHG_RQST_ID in ("+request_id+") with ur;");       
                        

                        int SKU_NBR = 0,MKT_NBR=0,OPT_NBR=0;
                        String EFF_BGN_TS = null;
                        double RETL_AMT = 0;
                        
                        
                        while (DB2RS.next()) {
                                        SKU_NBR  = DB2RS.getInt("SKU_NBR");
                                        EFF_BGN_TS = DB2RS.getString("EFF_BGN_TS");
                                        RETL_AMT = Math.round(DB2RS.getDouble("RETL_AMT") * 100.0) / 100.0;
                                        MKT_NBR =DB2RS.getInt("STR_GRP_ID");
                                        OPT_NBR=DB2RS.getInt("OPT_NBR");
                                                        
                        }
                        Excel_updatedvalues_coreInstall();
                        dbdateparse(EFF_BGN_TS);

                        if (SKU==SKU_NBR && market_nbr==MKT_NBR && option_nbr==OPT_NBR && DBeffectivedate.equals(datesubmitted) && RETL_AMT == core_install_OBT.retail_price) {
                                        report.addReportStep("Verify Retail is updated in EFF_SKU_STR_GRP_OPT_RETL_CNTRB for the given requestID "+request_id+" with a retail of $"+core_install_OBT.retail_price+" for SKU "+ SKU+" " , 
                                                                        "Retail is updated in EFF_SKU_STR_GRP_OPT_RETL_CNTRB table for the given requestID "+request_id+" with a retail of $"+RETL_AMT+" for SKU "+ SKU_NBR+"",StepResult.PASS);
                        } else {
                                        report.addReportStep("Verify Retail is updated in EFF_SKU_STR_GRP_OPT_RETL_CNTRB for the given requestID "+request_id+" with a retail of $"+core_install_OBT.retail_price+" for SKU"+ SKU+"", 
                                                                        "Retail is not updated in EFF_SKU_STR_GRP_OPT_RETL_CNTRB table for the given requestID "+request_id+" with a retail of $"+RETL_AMT+" for SKU "+ SKU_NBR+" ",StepResult.FAIL);
                        }

        } catch (Exception e) {
                        e.printStackTrace();
        }
        DB2RS.close();
        stmt.close();
        con.close();
}
	
	//cost option tables
	
public void DB2_PERM_SKU_STR_GRP_OPT_COST() throws Exception {
        
        try {
                        
        	
        	Excel_updatedvalues_coreInstall();
            DB2_tables_connect("select SKU_NBR,STR_GRP_ID,MVNDR_NBR,EFF_BGN_TS,COST_AMT FROM PERM_SKU_STR_GRP_OPT_COST WHERE "
                                                        + "SKU_CHG_RQST_ID in ("+request_id+") and OPT_NBR in ("+option_nbr+")"+"with ur;");       
                        

                        int SKU_NBR = 0,MKT_NBR=0,MVNDR_NBR=0;
                        String EFF_BGN_TS = null;
                        double COST_AMT = 0;
                        
                        
                        while (DB2RS.next()) {
                                        SKU_NBR  = DB2RS.getInt("SKU_NBR");
                                        EFF_BGN_TS = DB2RS.getString("EFF_BGN_TS");
                                        COST_AMT = Math.round(DB2RS.getDouble("COST_AMT") * 100.0) / 100.0;
                                        MKT_NBR =DB2RS.getInt("STR_GRP_ID");
                                        MVNDR_NBR=DB2RS.getInt("MVNDR_NBR");
                                                        
                        }
                        
                        
                       
                        dbdateparse(EFF_BGN_TS);
                        
                        System.out.println("cost values from sheet:"+DBeffectivedate);
                        System.out.println("cost values from db:"+datesubmitted);

                        if (SKU==SKU_NBR && market_nbr==MKT_NBR && mvndr_nbr==MVNDR_NBR && DBeffectivedate.equals(datesubmitted) &&  core_install_OBT.cost_price== COST_AMT) {
                                        report.addReportStep("Verify cost is updated in PERM_SKU_STR_GRP_OPT_COST for the given requestID "+request_id+" with a cost of $"+core_install_OBT.cost_price+" for SKU "+ SKU+"" , 
                                                                        "cost is updated in PERM_SKU_STR_GRP_OPT_COST table for the given requestID "+request_id+" with a cost of $"+COST_AMT+" for SKU "+ SKU_NBR+"",StepResult.PASS);
                        } else {
                                        report.addReportStep("Verify cost is updated in PERM_SKU_STR_GRP_OPT_COST for the given requestID "+request_id+" with a cost of $"+core_install_OBT.cost_price+" for SKU "+ SKU+"", 
                                                                        "cost is not updated in PERM_SKU_STR_GRP_OPT_COST table for the given requestID "+request_id+" with a cost of $"+COST_AMT+" for SKU "+ SKU_NBR+" ",StepResult.FAIL);
                        }

        } catch (Exception e) {
                        e.printStackTrace();
        }
        DB2RS.close();
        stmt.close();
        con.close();
}
public void DB2_EFF_SKU_STR_GRP_OPT_COST() throws Exception {
    
    try {
                    
    	
    	Excel_updatedvalues_coreInstall();
        DB2_tables_connect("select SKU_NBR,STR_GRP_ID,MVNDR_NBR,OPT_NBR,EFF_BGN_TS,COST_AMT FROM EFF_SKU_STR_GRP_OPT_COST WHERE "
                                                    + "SKU_NBR in ("+SKU+") and COST_AMT in ("+core_install_OBT.cost_price+")"+"with ur;");       
                    

                    int SKU_NBR = 0,MKT_NBR=0,MVNDR_NBR=0,OPT_NBR=0;
                    String EFF_BGN_TS = null;
                    double COST_AMT = 0;
                    
                    
                    while (DB2RS.next()) {
                                    SKU_NBR  = DB2RS.getInt("SKU_NBR");
                                    EFF_BGN_TS = DB2RS.getString("EFF_BGN_TS");
                                    COST_AMT = Math.round(DB2RS.getDouble("COST_AMT") * 100.0) / 100.0;
                                    MKT_NBR =DB2RS.getInt("STR_GRP_ID");
                                    MVNDR_NBR=DB2RS.getInt("MVNDR_NBR");
                                    OPT_NBR=DB2RS.getInt("OPT_NBR");
                                                    
                    }
                  
                    dbdateparse(EFF_BGN_TS);

                    if (SKU==SKU_NBR && market_nbr==MKT_NBR && mvndr_nbr==MVNDR_NBR && DBeffectivedate.equals(datesubmitted) &&  core_install_OBT.cost_price== COST_AMT && option_nbr==OPT_NBR) {
                                    report.addReportStep("Verify Cost is updated in EFF_SKU_STR_GRP_OPT_COST for the given requestID "+request_id+" with a cost of $"+core_install_OBT.cost_price+" for SKU "+ SKU+" " , 
                                                                    "Cost is updated in EFF_SKU_STR_GRP_OPT_COST table for the given requestID "+request_id+" with a cost of $"+COST_AMT+" for SKU "+ SKU_NBR+" ",StepResult.PASS);
                    } else {
                                    report.addReportStep("Verify Cost is updated in EFF_SKU_STR_GRP_OPT_COST for the given requestID "+request_id+" with a cost of $"+core_install_OBT.cost_price+" for SKU "+ SKU+"", 
                                                                    "Cost is not updated in EFF_SKU_STR_GRP_OPT_COST table for the given requestID "+request_id+" with a cost of $"+COST_AMT+" for SKU "+ SKU_NBR+"  ",StepResult.FAIL);
                    }

    } catch (Exception e) {
                    e.printStackTrace();
    }
    DB2RS.close();
    stmt.close();
    con.close();
}

public void DB2_EFF_SKSTR_GRP_OPT_COST_CNTRB() throws Exception {
    
    try {
                    
    	
    	Excel_updatedvalues_coreInstall();
    	DB2_tables_connect("select SKU_NBR,STR_GRP_ID,MVNDR_NBR,EFF_BGN_TS,COST_AMT FROM EFF_SKSTR_GRP_OPT_COST_CNTRB WHERE "
                + "SKU_CHG_RQST_ID in ("+request_id+") and OPT_NBR in ("+option_nbr+")"+"with ur;"); 
    	

                    int SKU_NBR = 0,MKT_NBR=0,MVNDR_NBR=0;
                    String EFF_BGN_TS = null;
                    double COST_AMT = 0;
                    
                    
                    while (DB2RS.next()) {
                                    SKU_NBR  = DB2RS.getInt("SKU_NBR");
                                    EFF_BGN_TS = DB2RS.getString("EFF_BGN_TS");
                                    COST_AMT = Math.round(DB2RS.getDouble("COST_AMT") * 100.0) / 100.0;
                                    MKT_NBR =DB2RS.getInt("STR_GRP_ID");
                                    MVNDR_NBR=DB2RS.getInt("MVNDR_NBR");
                                   
                                                    
                    }
                   
                    dbdateparse(EFF_BGN_TS);

                    if (SKU==SKU_NBR && market_nbr==MKT_NBR && mvndr_nbr==MVNDR_NBR && DBeffectivedate.equals(datesubmitted) &&  core_install_OBT.cost_price== COST_AMT ) {
                                    report.addReportStep("Verify Cost is updated in EFF_SKSTR_GRP_OPT_COST_CNTRB for the given requestID "+request_id+" with a cost of $"+core_install_OBT.cost_price+" for SKU "+ SKU+" " , 
                                                                    "Cost is updated in EFF_SKSTR_GRP_OPT_COST_CNTRB table for the given requestID "+request_id+" with a cost of $"+COST_AMT+" for SKU "+ SKU_NBR+"",StepResult.PASS);
                    } else {
                                    report.addReportStep("Verify Cost is updated in EFF_SKSTR_GRP_OPT_COST_CNTRB for the given requestID "+request_id+" with a cost of $"+core_install_OBT.cost_price+" for SKU "+ SKU+"", 
                                                                    "Cost is not updated in EFF_SKSTR_GRP_OPT_COST_CNTRB table for the given requestID "+request_id+" with a cost of $"+COST_AMT+" for SKU "+ SKU_NBR+" ",StepResult.FAIL);
                    }

    } catch (Exception e) {
                    e.printStackTrace();
    }
    DB2RS.close();
    stmt.close();
    con.close();
}

	
	public void Excel_updatedvalues_coreInstall() throws Exception {
		
		String trigger_BISTSKU=dataTable.getData(DataColumn.trigger_input);
		SKU_excel =dataTable.getData(DataColumn.sku_input);
		SKU = Integer.parseInt(SKU_excel);
		option_nbr_excel = dataTable.getData(DataColumn.Option_ID);
		option_nbr = Integer.parseInt(option_nbr_excel);
		market_nbr_excel = dataTable.getData(DataColumn.mkt);
	    market_nbr = Integer.parseInt(market_nbr_excel);
	    mvndr_nbr_excel=dataTable.getData(DataColumn.mvndr_input);
	    mvndr_nbr = Integer.parseInt(mvndr_nbr_excel);
        
       /* FileInputStream excelFile = new FileInputStream(
                                        new File(System.getProperty("user.dir") + "\\Input_Excel\\core_install.xlsx"));
        wb = new XSSFWorkbook(excelFile);
        try{
                        
            if( trigger_BISTSKU.equals("BISTSKU")|| trigger_BISTSKU.equals("BISTVSKU")) {
            	if(wb.getSheetAt(0).getRow(2).getCell(9).toString()!=null){
            	String retail =  wb.getSheetAt(0).getRow(2).getCell(9).toString();
                retail_price = Double.parseDouble(retail);
            	}
            	else{
                String cost =  wb.getSheetAt(0).getRow(2).getCell(11).toString();
                cost_price = Double.parseDouble(cost);
            }}
            else{
            	if(wb.getSheetAt(1).getRow(2).getCell(16).toString()!=null)
            	{
            	String retail =  wb.getSheetAt(1).getRow(2).getCell(16).toString();
                retail_price = Double.parseDouble(retail);}
            	else{
                String cost =  wb.getSheetAt(1).getRow(2).getCell(14).toString();
                cost_price = Double.parseDouble(cost);
                System.out.println("cost from sheet:"+cost_price);
            	}
            	
            }
        	
                        
              } catch (Exception e) {
                        e.printStackTrace();
        }*/
}
	

	
	// DB2 Connection Utility method
	public void DB2_tables_connect(String query) throws Exception {
		
		try{
			Class.forName("com.ibm.db2.jcc.DB2Driver");
			con = DriverManager.getConnection(db2q1_dbUrl, db2q1_username, db2q1_password);
			stmt = con.createStatement();
			DB2RS = stmt.executeQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
			report.addReportStep("Connection is not established with DB2 tables",
					"Unable to connect to DB2 tables", StepResult.FAIL);
		}
	}

	// Oracle connection Utility method
	public void Oracle_tables_connect(String query) throws Exception {
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(Oracle_dbUrl, Oracle_username, Oracle_password);
			stmt = con.createStatement();
			OracleRS = stmt.executeQuery(query);
		} catch(Exception e) {	
			e.printStackTrace();
			report.addReportStep("Connection is not established with DB2 tables",
				"Unable to connect to DB2 tables", StepResult.FAIL);
		}
	}
	
}
