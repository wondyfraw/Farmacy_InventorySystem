package org.farm.entity.beans;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.farm.fms.entity.ejb.StoreEJB;
import org.farm.fms.etntity.Store;

@ManagedBean(name = "editDrugsBean")
@ViewScoped
public class EditDrugsBean {

	Log log = LogFactory.getLog(EditDrugsBean.class);

	private Store store;

	@EJB
	private StoreEJB storeEJB;

	@PostConstruct
	public void init() {
		store = new Store();
		String storeId = null;
		Integer drugId;
		Map<String, String> map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

		if (map.get("idDrug") != null) {
			drugId = Integer.parseInt(StringUtils.defaultString(map.get("idDrug"), storeId));
			store = storeEJB.findById(drugId);
		}

	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}
}
