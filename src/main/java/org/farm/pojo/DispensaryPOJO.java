package org.farm.pojo;

import java.util.Calendar;
import java.util.Date;

import org.farm.fms.etntity.Dispensary;
import org.farm.fms.etntity.Store;

public class DispensaryPOJO {

	public DispensaryPOJO() {

	}

	public Dispensary mapDispensaryPOJO(Store store, Integer quantity, String email) {
		Dispensary dispensary = new Dispensary();
		Date now = Calendar.getInstance().getTime();

		if (store != null && quantity != null && email != null) {
			dispensary.setAdminName(email);
			dispensary.setDispensaryDate(now);
			dispensary.setQuantityInBox(quantity);
			dispensary.setQuantityPerPack(store.getQuantityperBoxperUnit());
			dispensary.setStore(store);
			dispensary.setQuantityPerTab(store.getQuantityperUnitperTab());
			dispensary.setQuantityPerUnit(
					store.getQuantityInBox() * store.getQuantityperBoxperUnit() * store.getQuantityperUnitperTab());
		}
		return dispensary;
	}
}
