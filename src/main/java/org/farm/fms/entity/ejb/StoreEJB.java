package org.farm.fms.entity.ejb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

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

	public void updateStore(Store store) {
		merge(store);
	}

	@Override
	public Store findById(Integer id) {

		return findById(Store.class, id);
	}

	/**
	 * 
	 * @param today
	 * @return list of expired drugs
	 */
	public List<Store> findExpiredDrugFromStore(Date today) {

		List<Store> drugList = new ArrayList<Store>();

		String filter = new SimpleDateFormat("yyyy-MM-dd").format(today);
		Date filterDate = convertDate(filter);

		TypedQuery<Store> storeQuery = entityManager.createNamedQuery("findExpiredDrugFromStore", Store.class);
		storeQuery.setParameter("today", filterDate);
		drugList = storeQuery.getResultList();
		return drugList;
	}

	private Date convertDate(String date) {
		Date filter = null;
		try {
			filter = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		} catch (ParseException e) {
			log.equals("----Is not a correct date format-----");
		}

		return filter;
	}
}
