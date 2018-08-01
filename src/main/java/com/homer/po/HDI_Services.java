package com.homer.po;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.homer.dao.InstanceContainer;

import static io.restassured.RestAssured.*;
import io.restassured.response.Response;

public class HDI_Services extends PageBase {

	public HDI_Services(InstanceContainer ic) {
		super(ic);
	}
	
	public List<Integer> strnbr = new ArrayList<>();
	
	public void read_store_pricingzoneWS() throws Exception {

		Response resp = given().contentType("application/json")
				.body("[{\"skuNumber\":"+SKU+"}]").
				when().post("https://pricingzonews-q1.apps-np.homedepot.com/skusearch/readZoneStore?category=hdi");
		
		if(resp.getStatusCode() == 200) {
			String response = resp.getBody().asString();
			SkuDTO skuDTO = new Gson().fromJson(response, SkuDTO.class);
			for(SkusTO skuTo : skuDTO.getSkus()){
				for(StoreGroupsTO storeGrp : skuTo.getStoreGroups()){
					if(storeGrp.getStoreGroupNumber().contains(zone)){
						for(StoresTO strs: storeGrp.getStores()) {
							strnbr.add(strs.getStorenumber());
						}
					}
				}
			}
		}	
	}
	
	public void validate_cassandra_RetailWS() throws Exception {
		String date = DBValidations.EFF_BGN_TS.replaceAll(" ", "T");
		
		Response response = given().when().get("https://origin-api.gcp-stage.homedepot.com/retail/pricing/v1"
				+ "/sku/"+SKU+"/store/"+strnbr.get(0)+"?effectiveDateTime="+date+"");
		if(response.getStatusCode() == 200) {
			if(Double.parseDouble(response.jsonPath().getString("retail")) == retail_price) {
				System.out.println(true);
			} else {
				
			}
		}
	}
	
	public void validate_cassandra_CostWS() throws Exception {
		String date = DBValidations.EFF_BGN_TS.replaceAll(" ", "T");
		
		Response response = given().when().get("http://enterprise-cost-lookup-rc.apps-np.homedepot.com/cost/market/v1/sku/"+SKU+"/"
				+"store/"+strnbr.get(0)+"/vendor/"+vendor+"/department/"+Department+"?effectiveDateTime="+date+"");
		if(response.getStatusCode() == 200) {
			if(Double.parseDouble(response.jsonPath().getString("cost")) == cost_price) {
				System.out.println(true);
			} else {
				
			}
		}
	}
}



