package org.farm.fms.entity.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

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

	public List<Dispensary> findDispensaryByStoreId(Integer storeId) {
		List<Dispensary> dispensaryList = new ArrayList<Dispensary>();

		TypedQuery<Dispensary> dispensaryQuery = entityManager.createNamedQuery("findDrugByStoreId", Dispensary.class);
		dispensaryQuery.setParameter("storeId", storeId);
		dispensaryList = dispensaryQuery.getResultList();

		return dispensaryList;
	}

}
