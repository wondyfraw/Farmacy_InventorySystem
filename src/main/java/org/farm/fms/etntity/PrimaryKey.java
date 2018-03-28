package org.farm.fms.etntity;

import java.io.Serializable;

public interface PrimaryKey<PType> extends Serializable {

	/**
	 * 
	 * @return
	 */

	PType getId();

	/**
	 * Setta l'inidice della tabella
	 * 
	 * @param id
	 *            PType
	 */
	void setId(PType id);

}
