package org.farm.fms.entity.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.farm.fms.etntity.Users;

@Stateless
public abstract class AbstructHibernateHome<Entity, PType> {

	protected Log log = LogFactory.getLog(this.getClass());

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

		final String queryHQL = "SELECT a FROM  " + entityName + "  a";

		Query query = entityManager.createQuery(queryHQL);

		return (List<Entity>) query.getResultList();
	}

	/**
	 * <b>Attension!<br/>
	 * Launch a RuntimeException in case of errors.
	 * 
	 * @param id
	 * @return
	 */

	public abstract Entity findById(PType id);

	public Entity findById(Class<Entity> entityClass, PType id) {

		Entity instance;
		instance = entityManager.find(entityClass, id);

		return instance;
	}

	public void removeById(PType id) {
		Entity entity = findById(id);
		remove(entity);
	}

	/**
	 * Deletes the Entity instance from DB. <br/>
	 * Launch a RuntimeException in case of errors.
	 * 
	 * @param persistentInstance
	 */

	private void remove(Entity persistentInstance) {

		if (persistentInstance == null) {
			log.error("You tried to destroy a null entity.");
			return;
		}
		if (log.isDebugEnabled())
			log.debug("removing" + persistentInstance.getClass().getSimpleName() + " instance: "
					+ persistentInstance.toString());

		entityManager.remove(persistentInstance);
	}

	public void persists(Entity entity) {

		log.debug("persisting " + entity.getClass().getSimpleName() + " instance");
		entityManager.persist(entity);

		log.debug("persist successful");
	}

	public void merge(Entity entity) {
		if (entity != null)
			entityManager.merge(entity);
	}

	public List<Entity> findByName(String name) {
		return null;
	}

	public void persistUser(Users users) {
		log.debug("persisting " + users.getClass().getSimpleName() + " instance");
		entityManager.persist(users);

		log.debug("persist successful");
	}

	public String getEntityName() {
		String homeName = this.getClass().getSimpleName();

		return StringUtils.replace(homeName, "EJB", "");
	}

}
