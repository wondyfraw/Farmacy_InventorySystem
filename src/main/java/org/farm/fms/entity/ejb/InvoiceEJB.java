package org.farm.fms.entity.ejb;

import org.farm.fms.etntity.Invoice;

public class InvoiceEJB extends AbstructHome<Invoice, Integer> {

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
	public Invoice findById(Integer id) {

		return findById(Invoice.class, id);
	}

}
