package org.farm.fms.entity.ejb;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.TypedQuery;

import org.farm.exception.MyStoreException;
import org.farm.fms.etntity.PrimaryKey;
import org.farm.fms.etntity.Store;
import org.farm.fms.etntity.Users;

/**
 * 
 * @author Wonde
 *
 * @param <Entity>
 *            Class of Entity to which the Home is associated
 * @param <PType>
 *            Primary key of the class
 */
public abstract class AbstructHome<Entity, PType> extends AbstructHibernateHome<Entity, PType>
		implements PrimaryKey<PType> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param entity
	 * 
	 */

	public void persistEntity(Entity entity) {
		persists(entity);
	}

	/**
	 * 
	 * @param entity
	 */

	public void persistUsers(Users users) {
		persistUser(users);
	}

	/**
	 * Update existing entity by the new one
	 * 
	 * @param entity
	 */

	public void mergeEntity(Entity entity) {
		merge(entity);
	}

	public List<Entity> findAllUsers() {
		return findAll();
	}

	/**
	 * Search medicine from store table
	 * 
	 * @param drugName
	 * @return
	 * @throws MyStoreException
	 */

	public List<Store> findByDrugName(String drugName) throws MyStoreException {
		try {

			TypedQuery<Store> queryStore = entityManager.createNamedQuery("storeSearchByDrugName", Store.class);
			queryStore.setParameter("drugName", drugName);
			List<Store> queryResult = queryStore.getResultList();

			return queryResult;

		} catch (Exception e) {
			throw new MyStoreException("Medicine not found exception", e);
		}
	}

	/**
	 * Aggiunge un errore da mostrare all'utente e stampa sul log lo stacktrace dell'eccezione gestita.
	 * 
	 * @param codErrore
	 *            codice del messaggioche si trova in errors.properties
	 */
	public void addErrorMessage(String codErrore) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, codErrore, codErrore);
		log.error(codErrore);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

}
