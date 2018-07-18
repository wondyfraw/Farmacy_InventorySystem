package org.farm.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.farm.fms.etntity.Dispensary;
import org.farm.fms.etntity.Store;

public class Mapper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4262031844857818105L;

	public Mapper() {

	}

	public MapperPOJO mapToSales(Dispensary dispensary, Store store) {

		if (dispensary != null && store != null) {
			MapperPOJO mapperPOJO = new MapperPOJO();
			mapperPOJO.setBathcNumber(store.getBathcNumber());
			mapperPOJO.setBrand(store.getBrand());
			mapperPOJO.setDrugName(store.getDrugName());
			mapperPOJO.setExpireDate(store.getExpireDate());
			mapperPOJO.setManufacturingDate(store.getManufacturingDate());
			mapperPOJO.setPackUnit(store.getPackUnit());
			mapperPOJO.setQuantityInBox(dispensary.getQuantityInBox());
			mapperPOJO.setQuantityPerPack(dispensary.getQuantityPerPack());
			mapperPOJO.setQuantityPerUnitPack(dispensary.getQuantityPerTab());
			mapperPOJO.setQuantityPerUnit(dispensary.getQuantityPerUnit());
			mapperPOJO.setRegistrationDate(store.getRegistrationDate());
			mapperPOJO.setSalesPrice(store.getSalesPrice());
			mapperPOJO.setStoreId(dispensary.getStore().getStoreId());
			mapperPOJO.setUnit(store.getUnit());
			mapperPOJO.setPackType(store.getPackType());
			mapperPOJO.setWeight(store.getWeight());
			mapperPOJO.setDispensaryId(dispensary.getIdDispensary());

			return mapperPOJO;
		} else
			return null;
	}

	public List<MapperPOJO> modifyQuantity(List<MapperPOJO> mapperPOJOList, Integer quantity, Integer dose, int j) {

		mapperPOJOList.get(j).setQuantityInBox(quantity + mapperPOJOList.get(j).getQuantityInBox());
		mapperPOJOList.get(j).setDose((quantity * dose) + mapperPOJOList.get(j).getDose());
		mapperPOJOList.get(j).setQuantityPerUnit((quantity * dose) + mapperPOJOList.get(j).getQuantityPerUnit());
		if (mapperPOJOList.get(j).getPackType().equals("Box"))
			mapperPOJOList.get(j).setTotalUnitPack(((quantity * dose) / mapperPOJOList.get(j).getQuantityPerUnitPack())
					+ mapperPOJOList.get(j).getTotalUnitPack());

		return mapperPOJOList;
	}

	public List<MapperPOJO> mapdata(MapperPOJO drug, MapperPOJO mapperPOJO, Integer quantity, Integer dose) {

		if (mapperPOJO != null && quantity != null && dose != null) {
			List<MapperPOJO> newList = new ArrayList<MapperPOJO>();
			drug.setBathcNumber(mapperPOJO.getBathcNumber());
			drug.setBrand(mapperPOJO.getBrand());
			drug.setDispensaryId(mapperPOJO.getDispensaryId());
			drug.setDose(dose);
			drug.setDrugName(mapperPOJO.getDrugName());
			drug.setExpireDate(mapperPOJO.getExpireDate());
			drug.setManufacturingDate(mapperPOJO.getManufacturingDate());
			drug.setPackUnit(mapperPOJO.getPackUnit());
			drug.setQuantityInBox(quantity);
			drug.setQuantityPerPack(mapperPOJO.getQuantityPerPack());
			drug.setQuantityPerUnitPack(mapperPOJO.getQuantityPerUnitPack());
			drug.setQuantityPerUnit(quantity * dose);
			if (mapperPOJO.getPackType().equals("Box"))
				drug.setTotalUnitPack((quantity * dose) / mapperPOJO.getQuantityPerUnitPack());
			drug.setRegistrationDate(mapperPOJO.getRegistrationDate());
			drug.setSalesPrice(mapperPOJO.getSalesPrice());
			drug.setStoreId(mapperPOJO.getStoreId());
			drug.setUnit(mapperPOJO.getUnit());
			drug.setWeight(mapperPOJO.getWeight());
			newList.add(drug);
			return newList;
		} else
			return null;
	}
}
