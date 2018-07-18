package org.farm.pojo;

import java.util.Calendar;
import java.util.Date;

import org.farm.fms.etntity.Dispensary;
import org.farm.fms.etntity.Store;

public class DispensaryPOJO {

	public DispensaryPOJO() {

	}

	public Dispensary mapDispensaryPOJO(Store store, String email) {
		Dispensary dispensary = new Dispensary();
		Date now = Calendar.getInstance().getTime();

		if (store != null && email != null) {
			dispensary.setAdminName(email);
			dispensary.setDispensaryDate(now);
			dispensary.setQuantityInBox(store.getQuantityInBox());
			dispensary.setQuantityPerPack(store.getQuantityperBoxperUnit());
			dispensary.setStore(store);
			dispensary.setQuantityPerTab(store.getQuantityperUnitperTab());
			if (store.getPackType().equals("Box")) {
				if (store.getPackUnit().equals("Strip")) {
					dispensary.setQuantityPerUnit(store.getQuantityInBox() * store.getQuantityperBoxperUnit()
							* store.getQuantityperUnitperTab());
					dispensary.setTotalUnitPack(store.getQuantityInBox() * store.getQuantityperBoxperUnit()); // total
																												// number
																												// of
																												// strip
																												// ready
																												// for
																												// sale(dispensary)
				} else {
					dispensary.setQuantityPerUnit(store.getQuantityInBox() * store.getQuantityperBoxperUnit());
				}
			}
			if (store.getPackType().equals("Cup")) {
				dispensary.setQuantityPerUnit(store.getQuantityInBox() * store.getQuantityperBoxperUnit());
			}

			if (store.getPackType().equals("Packed")) {
				dispensary.setQuantityPerUnit(store.getQuantityInBox() * store.getQuantityperBoxperUnit());
			}
		}
		return dispensary;
	}
}
