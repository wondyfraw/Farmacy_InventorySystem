package org.farm.entity.beans;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.farm.fms.entity.ejb.DispensaryEJB;
import org.farm.fms.entity.ejb.StoreEJB;
import org.farm.fms.etntity.Dispensary;
import org.farm.fms.etntity.Store;
import org.farm.pojo.DispensaryPOJO;
import org.farm.utils.ConstantsSingleton;
import org.farm.utils.ManageCart;

@ManagedBean(name = "medicineRegistrationBean")
@ViewScoped
public class MedicineRegistrationBean extends AbstructSessionBean {

	Log log = LogFactory.getLog(MedicineRegistrationBean.class);

	private Store store;
	private Store dispensaryDrug;
	private Store deleteDrug;
	private Store dispensaryCart;
	private List<String> packUnit;
	private List<String> units;
	private Map<String, List<String>> data;
	private String unitPack;
	private List<Store> drugList;
	private List<Store> dispensary_drugList;
	private boolean showQuantityperStrip;
	private boolean showSaveButton;
	private boolean showDispensaryTable;
	private Integer quantity;

	private ManageCart manageCart;
	private DispensaryPOJO dispensaryPOJO;
	private Dispensary dispensary;

	@EJB
	private StoreEJB storeEJB;

	@EJB
	private DispensaryEJB dispensaryEJB;

	@PostConstruct
	public void init() {
		store = new Store();
		packUnit = new ArrayList<String>();
		units = new ArrayList<String>();
		data = new HashMap<String, List<String>>();
		drugList = new ArrayList<Store>();
		dispensary_drugList = new ArrayList<Store>();
		manageCart = new ManageCart();
		dispensaryPOJO = new DispensaryPOJO();
		dispensary = new Dispensary();

		drugList = storeEJB.findAll();

		packUnit = ConstantsSingleton.packType();
		units = ConstantsSingleton.packUnit();

		List<String> unit = new ArrayList<String>();
		unit.add("Tablet");
		unit.add("Capsule");
		unit.add("Ampule");
		data.put("Strip", unit);

		unit = new ArrayList<String>();
		unit.add("Tablet");
		unit.add("Capsule");
		data.put("Cup", unit);

		unit = new ArrayList<String>();
		unit.add("Glove");
		unit.add("Tooth soap");
		unit.add("Condom");
		unit.add("Cream");
		unit.add("IV Fload");
		unit.add("IV Set");
		unit.add("Injection needle");
		unit.add("Butterfly needle");
		unit.add("Powder");
		unit.add("Modis");
		unit.add("Baby diaper");
		unit.add("Tooth Brash");
		data.put("Piece", unit);

		Map<String, String> map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		if ((map.get("idDrug")) != null) {
			try {
				Integer id = Integer.parseInt(map.get("idDrug"));
				store = storeEJB.findById(Integer.parseInt(map.get("idDrug")));
				if (store != null) {
					unitPack = store.getPackUnit();
					this.showSaveButton = true;
				}
			} catch (Exception e) {
				storeEJB.addErrorMessage("data_error_loading");
			}

		}

	}

	/**
	 * Register new Medicin and save to store
	 */
	public void registerMedicin() {
		if (store == null)
			return;
		Date date = Calendar.getInstance().getTime();
		store.setRegistrationDate(date);
		store.setPackUnit(unitPack);
		storeEJB.persistEntity(store);
		log.info("Successfully Register Drugs " + store.getDrugName() + " " + store.getQuantityperBoxperUnit() + ""
				+ store.getUnit());
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successfully save drug"));
	}

	public void deleteDrugs() {

		String storeId = null;
		Store store = new Store();
		Integer drugId;
		int index = 0;
		Map<String, String> map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		if (map.get("id") != null) {
			drugId = Integer.parseInt(StringUtils.defaultString(map.get("id"), storeId));
			store = storeEJB.findById(drugId);

			if (store != null) {
				for (int i = 0; i < drugList.size(); i++) {
					Store element = drugList.get(i);
					if (element.getStoreId() != null && element.getStoreId().equals(drugId)) {
						index = i;
						break;
					}
				}

				drugList.remove(index);
				storeEJB.removeById(drugId);
			}
		}

	}

	/**
	 * Delete single drugs from the list
	 */
	public void deleteDrugInfo() {
		drugList.remove(deleteDrug);

		if (deleteDrug.getStoreId() != null)
			storeEJB.removeById(deleteDrug.getStoreId());
	}

	/**
	 * delete drug from dispensary session
	 */
	public void deleteFromDispensaryCart() {

		for (int i = 0; i < drugList.size(); i++) {
			Store cartdrug = drugList.get(i);
			if (cartdrug.getStoreId() != null && cartdrug.getStoreId().equals(dispensaryCart.getStoreId())) {
				cartdrug.setQuantityInBox(dispensaryCart.getQuantityInBox() + cartdrug.getQuantityInBox());
			}
		}
		dispensary_drugList.remove(dispensaryCart);
		if (dispensary_drugList.size() < 1)
			showDispensaryTable = false;

	}

	public void saveEditDrug() {
		Date now = Calendar.getInstance().getTime();

		if (store != null) {
			store.setModifyDate(now);
			store.setPackUnit(unitPack);
			storeEJB.mergeEntity(store);

			log.info("Successfully save modified drugs " + store.getDrugName() + " " + store.getBrand() + " "
					+ store.getBathcNumber() + " " + store.getBrand() + " " + store.getModifyDate());
		}
	}

	/**
	 * store selected drug in the dispensary cart session finally save to dispensary table and update store table
	 */
	public void saveDrugToDispensary() {

		for (int i = 0; i < dispensary_drugList.size(); i++) {
			Store element = dispensary_drugList.get(i);
			String email = loginAuthenticationBean.getEmail();
			dispensary = dispensaryPOJO.mapDispensaryPOJO(element, quantity, email);

			if (dispensary != null) {
				dispensaryEJB.persistEntity(dispensary);
				for (int j = 0; j < drugList.size(); j++) {
					Store drugUpdate = drugList.get(j); // update drug quantity in the store
					if (drugUpdate != null && drugUpdate.getStoreId().equals(element.getStoreId()))
						storeEJB.merge(drugUpdate);
				}
			}
		}
		showDispensaryTable = false;
		dispensary_drugList.removeAll(dispensary_drugList);
		log.info("---Successfully transfer Drugs from Store to Dispensary!! " + " Admin who make the transfer="
				+ loginAuthenticationBean.getEmail());
		/*
		 * System.out.println(loginAuthenticationBean.getEmail()); if (dispensary_drugList.size() > 0) {
		 * FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info",
		 * "PrimeFaces Rocks.")); }
		 */
	}

	/**
	 * add drug to dispensary list
	 */
	public void info() {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "PrimeFaces Rocks."));
	}

	public void addToDispensary() {

		if (dispensaryDrug != null) {
			int index = 0;
			for (int i = 0; i < drugList.size(); i++) {
				Store element = drugList.get(i);
				if (element.getStoreId() != null && element.getStoreId().equals(dispensaryDrug.getStoreId())) {
					if (quantity != null && element.getQuantityInBox() > this.quantity) {
						index = 1;
						Integer newQuantity = element.getQuantityInBox() - quantity;
						drugList.get(i).setQuantityInBox(newQuantity);
						break;
					}
				}
			}
			// update dispensary table
			boolean found = false;
			if (index == 1) {
				if (dispensary_drugList != null) {
					for (int j = 0; j < dispensary_drugList.size(); j++) {
						Store element = dispensary_drugList.get(j);
						if (element.getStoreId() != null && element.getStoreId().equals(dispensaryDrug.getStoreId())) {
							found = true;
							dispensary_drugList = manageCart.modifyQuantity(dispensary_drugList, quantity, j);
							// dispensary_drugList.get(j)
							// .setQuantityInBox(quantity + dispensary_drugList.get(j).getQuantityInBox());
						}
					}
				}
				if (dispensary_drugList == null || found == false) {
					List<Store> newList = new ArrayList<Store>();
					Store drug = new Store();
					newList = manageCart.mapData(drug, dispensaryDrug, quantity, newList);
					dispensary_drugList = newList;
				}

				showDispensaryTable = true;
			}
		}
	}

	public void showStripQuantity(ValueChangeEvent event) {
		if (event.getNewValue() != null) {
			if (event.getNewValue().equals("Cup") || event.getNewValue().equals("Packed"))
				showQuantityperStrip = true;
			// else
			// showQuantityperStrip = true;
		}
	}

	public void changePackUnit() {
		if (unitPack != null && !unitPack.equals(""))
			units = data.get(unitPack);
		else
			units = new ArrayList<String>();
	}

	public Date getMinData() {
		return new Date();
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public List<String> getPackUnit() {
		return packUnit;
	}

	public void setPackUnit(List<String> packUnit) {
		this.packUnit = packUnit;
	}

	public String getUnitPack() {
		return unitPack;
	}

	public void setUnitPack(String unitPack) {
		this.unitPack = unitPack;
	}

	public List<String> getUnits() {
		/*
		 * if (unitPack != null && !unitPack.equals("")) units = data.get(unitPack); else units = new
		 * ArrayList<String>();
		 */
		return units;
	}

	/**
	 * @param event
	 *            a change event when the value of the state changes.
	 */
	public void stateChangeListener(ValueChangeEvent event) {
		if (event.getNewValue() != unitPack) {
			unitPack = null;
		}
	}

	public void setUnits(List<String> units) {
		this.units = units;
	}

	public Map<String, List<String>> getData() {
		return data;
	}

	public void setData(Map<String, List<String>> data) {
		this.data = data;
	}

	public List<Store> getDrugList() {
		return drugList;
	}

	public void setDrugList(List<Store> drugList) {
		this.drugList = drugList;
	}

	public boolean isShowQuantityperStrip() {
		return showQuantityperStrip;
	}

	public void setShowQuantityperStrip(boolean showQuantityperStrip) {
		this.showQuantityperStrip = showQuantityperStrip;
	}

	public boolean isShowSaveButton() {
		return showSaveButton;
	}

	public void setShowSaveButton(boolean showSaveButton) {
		this.showSaveButton = showSaveButton;
	}

	public Store getDeleteDrug() {
		return deleteDrug;
	}

	public void setDeleteDrug(Store deleteDrug) {
		this.deleteDrug = deleteDrug;
	}

	public List<Store> getDispensary_drugList() {
		return dispensary_drugList;
	}

	public void setDispensary_drugList(List<Store> dispensary_drugList) {
		this.dispensary_drugList = dispensary_drugList;
	}

	public boolean isShowDispensaryTable() {
		return showDispensaryTable;
	}

	public void setShowDispensaryTable(boolean showDispensaryTable) {
		this.showDispensaryTable = showDispensaryTable;
	}

	public Store getDispensaryDrug() {
		return dispensaryDrug;
	}

	public void setDispensaryDrug(Store dispensaryDrug) {
		this.dispensaryDrug = dispensaryDrug;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Store getDispensaryCart() {
		return dispensaryCart;
	}

	public void setDispensaryCart(Store dispensaryCart) {
		this.dispensaryCart = dispensaryCart;
	}

}
