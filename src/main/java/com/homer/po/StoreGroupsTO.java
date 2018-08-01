package com.homer.po;

import java.util.List;

public class StoreGroupsTO {
	private String storeGroupNumber;
	private List<StoresTO> stores;
	public String getStoreGroupNumber() {
		return storeGroupNumber;
	}
	public void setStoreGroupNumber(String storeGroupNumber) {
		this.storeGroupNumber = storeGroupNumber;
	}
	public List<StoresTO> getStores() {
		return stores;
	}
	public void setStores(List<StoresTO> stores) {
		this.stores = stores;
	}
	
	
}
