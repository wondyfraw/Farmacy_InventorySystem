package org.farm.pojo;

import java.util.Calendar;
import java.util.Date;

import javax.ejb.EJB;

import org.farm.fms.entity.ejb.DispensaryEJB;
import org.farm.fms.etntity.Dispensary;
import org.farm.fms.etntity.Sales;

public class SalesPOJO {

	public SalesPOJO() {

	}

	@EJB
	private DispensaryEJB dispensaryEJB;

	public Sales mapSalesPOJO(MapperPOJO mapperPOJO, String salesPersonName, Dispensary dispensary) {
		Sales sales = new Sales();
		Date date = Calendar.getInstance().getTime();

		if (mapperPOJO != null && salesPersonName != null && dispensary != null) {
			// dispensary = dispensaryEJB.findById(mapperPOJO.getDispensaryId());
			// if (dispensary != null) {
			sales.setBatchNumber(mapperPOJO.getBathcNumber());
			sales.setBrand(mapperPOJO.getBrand());
			sales.setDispensary(dispensary.getIdDispensary());
			sales.setDose(mapperPOJO.getDose());
			sales.setDrugName(mapperPOJO.getDrugName());
			sales.setQuantity(mapperPOJO.getQuantityInBox());
			sales.setRegistrationdate(date);
			sales.setSalesPerson(salesPersonName);
			sales.setTotalPrice(mapperPOJO.getSalesPrice() * mapperPOJO.getQuantityInBox());
			sales.setUnit(mapperPOJO.getUnit());
			sales.setUnitPrice(mapperPOJO.getSalesPrice());
			sales.setPackUnit(mapperPOJO.getPackUnit());
			sales.setWeight(mapperPOJO.getWeight());
			// }
		}
		return sales;
	}

}
