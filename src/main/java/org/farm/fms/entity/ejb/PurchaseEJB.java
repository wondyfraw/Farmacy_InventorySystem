package org.farm.fms.entity.ejb;

import javax.ejb.Stateless;

import org.farm.fms.etntity.Purchase;

@Stateless
public class PurchaseEJB extends AbstructHome<Purchase, Integer> {

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Purchase findById(Integer id) {

		return findById(Purchase.class, id);
	}

}
