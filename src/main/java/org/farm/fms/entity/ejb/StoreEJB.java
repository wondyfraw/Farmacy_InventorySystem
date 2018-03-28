package org.farm.fms.entity.ejb;

import javax.ejb.Stateless;

import org.farm.fms.etntity.Store;

@Stateless
public class StoreEJB extends AbstructHome<Store, Integer> {

	private Store store;

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
	public Store findById(Integer id) {

		return findById(Store.class, id);
	}
}
