package org.farm.fms.entity.ejb;

import javax.ejb.Stateless;

import org.farm.fms.etntity.Sales;

@Stateless
public class SalesEJB extends AbstructHome<Sales, Integer> {

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
	public Sales findById(Integer id) {

		return findById(Sales.class, id);
	}

}
