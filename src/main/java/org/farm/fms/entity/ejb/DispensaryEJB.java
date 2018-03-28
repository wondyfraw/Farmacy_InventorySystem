package org.farm.fms.entity.ejb;

import javax.ejb.Stateless;

import org.farm.fms.etntity.Dispensary;

@Stateless
public class DispensaryEJB extends AbstructHome<Dispensary, Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2867172369447803734L;

	@Override
	public Integer getId() {
		return null;
	}

	@Override
	public void setId(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Dispensary findById(Integer id) {

		return findById(Dispensary.class, id);
	}

}
