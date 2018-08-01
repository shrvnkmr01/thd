package com.homer.po;

import java.util.List;

public class SkusTO {
	
	private Integer skuNumber;
	private Integer skuClass;
	private Integer skuSubClass;
	private String skuSubDepartment;
	private Integer skuGroupId;
	private List<StoreGroupsTO> storeGroups;
	public Integer getSkuNumber() {
		return skuNumber;
	}
	public void setSkuNumber(Integer skuNumber) {
		this.skuNumber = skuNumber;
	}
	public Integer getSkuClass() {
		return skuClass;
	}
	public void setSkuClass(Integer skuClass) {
		this.skuClass = skuClass;
	}
	public Integer getSkuSubClass() {
		return skuSubClass;
	}
	public void setSkuSubClass(Integer skuSubClass) {
		this.skuSubClass = skuSubClass;
	}
	public String getSkuSubDepartment() {
		return skuSubDepartment;
	}
	public void setSkuSubDepartment(String skuSubDepartment) {
		this.skuSubDepartment = skuSubDepartment;
	}
	public Integer getSkuGroupId() {
		return skuGroupId;
	}
	public void setSkuGroupId(Integer skuGroupId) {
		this.skuGroupId = skuGroupId;
	}
	public List<StoreGroupsTO> getStoreGroups() {
		return storeGroups;
	}
	public void setStoreGroups(List<StoreGroupsTO> storeGroups) {
		this.storeGroups = storeGroups;
	}
}
