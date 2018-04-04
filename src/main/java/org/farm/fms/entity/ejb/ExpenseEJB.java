package org.farm.fms.entity.ejb;

import org.farm.fms.etntity.Expense;

public class ExpenseEJB extends AbstructHome<Expense, Integer> {

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
	public Expense findById(Integer id) {

		return findById(Expense.class, id);
	}

}
