package org.farm.fms.etntity;

import java.util.List;

import javax.persistence.TypedQuery;

import org.farm.exception.MyStoreException;

public abstract class AbstructHome<Entity, PType> extends AbstructHibernateHome<Entity, PType> {

	/**
	 * 
	 * @param entity
	 */

	public void persistEntity(Entity entity) {
		persists(entity);
	}

	/**
	 * 
	 * @param entity
	 */

	public void mergeEntity(Entity entity) {
		merge(entity);
	}

	public List<Entity> findAll() {
		return findAll();
	}

	/**
	 * Search medicine from store table
	 * 
	 * @param drugName
	 * @return
	 * @throws MyStoreException
	 */

	public List<Stores> findByDrugName(String drugName) throws MyStoreException {
		try {

			TypedQuery<Stores> queryStore = entityManager.createNamedQuery("storeSearchByDrugName", Stores.class);
			queryStore.setParameter("drugName", drugName);
			List<Stores> queryResult = queryStore.getResultList();

			return queryResult;

		} catch (Exception e) {
			throw new MyStoreException("Medicine not found exception", e);
		}
	}

}
