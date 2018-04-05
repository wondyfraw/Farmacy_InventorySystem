package org.farm.pojo;

import java.util.Calendar;
import java.util.Date;

import javax.ejb.EJB;

import org.farm.fms.entity.ejb.DispensaryEJB;
import org.farm.fms.etntity.Dispensary;
import org.farm.fms.etntity.Sales;
import org.farm.fms.etntity.Store;

public class SalesPOJO {

	public SalesPOJO() {

	}

	@EJB
	private DispensaryEJB dispensaryEJB;

	public Sales mapSalesPOJO(Store store, Integer quantity, Integer dose, Double unitPrice, Double totalPrice,
			String salesPersonName) {
		Sales sales = new Sales();
		Dispensary dispensary = new Dispensary();
		Date date = Calendar.getInstance().getTime();

		if (store != null && quantity != null && dose != null && unitPrice != null && totalPrice != null
				&& salesPersonName != null) {
			dispensary = dispensaryEJB.findById(store.getStoreId());
			if (dispensary != null) {
				sales.setBatchNumber(store.getBathcNumber());
				sales.setBrand(store.getBrand());
				sales.setDispensary(dispensary);
				sales.setDose(dose);
				sales.setDrugName(store.getDrugName());
				sales.setQuantity(quantity);
				sales.setRegistrationdate(date);
				sales.setSalesPerson(salesPersonName);
				sales.setTotalPrice(totalPrice);
				sales.setUnit(store.getUnit());
				sales.setUnitPrice(unitPrice);
				sales.setWeight(store.getWeight());
			}
		}
		return sales;
	}

}
