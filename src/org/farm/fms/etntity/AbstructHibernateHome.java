package org.farm.fms.etntity;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public abstract class AbstructHibernateHome<Entity, PType> {

	// protected final Log log = LogFactory.getLog(this.getClass());
	@PersistenceContext
	protected EntityManager entityManager;

	/**
	 * Returns all rows in the table corresponding to the entity
	 * 
	 * 
	 * @return all data from the specified table
	 */
	public List<Entity> findAll() {
		String entityName = getEntityName();

		final String queryHQL = "SELECT a FROM" + entityName + "a";

		Query query = entityManager.createQuery(queryHQL);

		return (List<Entity>) query.getResultList();
	}

	public void persists(Entity entity) {

		if (entity != null)
			entityManager.persist(entity);
	}

	public void merge(Entity entity) {
		if (entity != null)
			entityManager.merge(entity);
	}

	public List<Entity> findByName(String name) {
		return null;
	}

	public String getEntityName() {
		String homeName = this.getClass().getSimpleName();
		return homeName;
	}

}
