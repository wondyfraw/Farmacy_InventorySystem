package org.farm.utils;

import java.util.ArrayList;
import java.util.List;

import org.farm.fms.etntity.Store;

public class ManageCart {

	private Store store;

	public ManageCart() {

	}

	public List<Store> modifyQuantity(List<Store> drugList, Integer quantity, int index) {

		drugList.get(index).setQuantityInBox(quantity + drugList.get(index).getQuantityInBox());
		return drugList;
	}

	public List<Store> addToCartList(Store drug, Integer quantity) {
		List<Store> drugList = new ArrayList<Store>();
		store = new Store();
		store = drug;
		store.setQuantityInBox(quantity);
		drugList.add(store);
		return drugList;
	}

	public List<Store> mapData(Store drug, Store store, Integer quantity, List<Store> newList) {
		drug.setStoreId(store.getStoreId());
		drug.setDrugName(store.getDrugName());
		drug.setBathcNumber(store.getBathcNumber());
		drug.setBrand(store.getBrand());
		drug.setExpireDate(store.getExpireDate());
		drug.setManufacturingDate(store.getManufacturingDate());
		drug.setPackType(store.getPackType());
		drug.setPackUnit(store.getPackUnit());
		drug.setModifyDate(store.getModifyDate());
		drug.setQuantityInBox(quantity);
		drug.setQuantityperBoxperUnit(store.getQuantityperBoxperUnit());
		drug.setQuantityperUnitperTab(store.getQuantityperUnitperTab());
		drug.setUnit(store.getUnit());
		drug.setUnitPrice(store.getUnitPrice());
		drug.setSalesPrice(store.getSalesPrice());
		drug.setWeight(store.getWeight());
		newList.add(drug);
		drug.setRegistrationDate(store.getRegistrationDate());
		return newList;
	}

}
