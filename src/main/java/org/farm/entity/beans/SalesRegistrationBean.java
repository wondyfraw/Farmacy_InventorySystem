package org.farm.entity.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.farm.fms.entity.ejb.DispensaryEJB;
import org.farm.fms.entity.ejb.SalesEJB;
import org.farm.fms.entity.ejb.StoreEJB;
import org.farm.fms.etntity.Dispensary;
import org.farm.fms.etntity.Store;
import org.farm.pojo.Mapper;
import org.farm.pojo.MapperPOJO;

@ManagedBean(name = "salesRegistrationBean")
@ViewScoped
public class SalesRegistrationBean extends AbstructSessionBean {

	Log log = LogFactory.getLog(SalesRegistrationBean.class);

	private List<Store> drugList;
	private List<MapperPOJO> session_drugList;
	private List<Dispensary> dispensaryList;
	private List<MapperPOJO> mapperPOJOList;
	private List<MapperPOJO> filteredDrugs;
	private boolean showDispensaryTable;
	private MapperPOJO dispensaryDrug;
	private MapperPOJO sessionCart;
	private Mapper mapper;

	private Integer quantity;
	private Double unitPrice;
	private Double totalPrice;
	private Integer dose;

	@EJB
	private StoreEJB storeEJB;

	@EJB
	private SalesEJB salsesEJB;

	@EJB
	private DispensaryEJB dispensaryEJB;

	@PostConstruct
	public void init() {
		drugList = new ArrayList<Store>();
		session_drugList = new ArrayList<MapperPOJO>();
		dispensaryList = new ArrayList<Dispensary>();
		mapperPOJOList = new ArrayList<MapperPOJO>();
		mapper = new Mapper();
		// dispensaryDrug = new MapperPOJO();

		dispensaryList = dispensaryEJB.findAll();
		for (int i = 0; i < dispensaryList.size(); i++) {
			Dispensary dispensary = new Dispensary();
			dispensary = dispensaryList.get(i);
			if (dispensary.getStore() != null) {
				Mapper mapper = new Mapper();
				Store store = new Store();
				MapperPOJO mapperPOJO = new MapperPOJO();
				store = storeEJB.findById(dispensary.getStore().getStoreId());
				mapperPOJO = mapper.mapToSales(dispensary, store);
				if (mapperPOJO != null)
					mapperPOJOList.add(mapperPOJO);
				else
					log.error("[--Retrun value of MapperPOJO Object in mapToSales function is null--]");
			}
		}
	}

	// addToDispensary
	public void addToSalesSession() {

		if (dispensaryDrug != null) {
			int index = 0;
			for (int i = 0; i < mapperPOJOList.size(); i++) {
				MapperPOJO element = mapperPOJOList.get(i);
				if (element.getDispensaryId() != null
						&& element.getDispensaryId().equals(dispensaryDrug.getDispensaryId())) {
					if (dose != null && element.getQuantityPerUnit() > (this.dose * this.quantity)) {
						index = 1;
						Integer newQuantity = element.getQuantityPerUnit() - (this.dose * this.quantity);
						mapperPOJOList.get(i).setQuantityPerUnit(newQuantity);
					}
				}
			}

			// update sales table
			boolean found = false;
			if (index == 1) {
				if (session_drugList != null) {
					for (int j = 0; j < session_drugList.size(); j++) {
						MapperPOJO element = session_drugList.get(j);
						if (element.getDispensaryId() != null
								&& element.getDispensaryId().equals(dispensaryDrug.getDispensaryId())) {
							found = true;
							session_drugList = mapper.modifyQuantity(session_drugList, quantity, dose, j);
						}
					}
				}

				if (session_drugList.size() <= 0 || found == false) {
					List<MapperPOJO> newMapperPOJOList = new ArrayList<MapperPOJO>();
					MapperPOJO mapperPOJO = new MapperPOJO();
					newMapperPOJOList = mapper.mapdata(mapperPOJO, dispensaryDrug, quantity, dose);
					if (newMapperPOJOList != null)
						session_drugList = newMapperPOJOList;
				}
				showDispensaryTable = true;
			}
		}
	}

	// saveDrugToDispensary
	public void saveDrugToSales() {

	}

	public void deleteDrugInfo() {

	}

	// deleteFromDispensaryCart
	public void deleteFromSalesCart() {

	}

	public List<Store> getDrugList() {
		return drugList;
	}

	public void setDrugList(List<Store> drugList) {
		this.drugList = drugList;
	}

	public List<MapperPOJO> getSession_drugList() {
		return session_drugList;
	}

	public void setSession_drugList(List<MapperPOJO> session_drugList) {
		this.session_drugList = session_drugList;
	}

	public boolean isShowDispensaryTable() {
		return showDispensaryTable;
	}

	public void setShowDispensaryTable(boolean showDispensaryTable) {
		this.showDispensaryTable = showDispensaryTable;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getDose() {
		return dose;
	}

	public void setDose(Integer dose) {
		this.dose = dose;
	}

	public MapperPOJO getDispensaryDrug() {
		return dispensaryDrug;
	}

	public void setDispensaryDrug(MapperPOJO dispensaryDrug) {
		this.dispensaryDrug = dispensaryDrug;
	}

	public List<Dispensary> getDispensaryList() {
		return dispensaryList;
	}

	public void setDispensaryList(List<Dispensary> dispensaryList) {
		this.dispensaryList = dispensaryList;
	}

	public List<MapperPOJO> getMapperPOJOList() {
		return mapperPOJOList;
	}

	public void setMapperPOJOList(List<MapperPOJO> mapperPOJOList) {
		this.mapperPOJOList = mapperPOJOList;
	}

	public MapperPOJO getSessionCart() {
		return sessionCart;
	}

	public void setSessionCart(MapperPOJO sessionCart) {
		this.sessionCart = sessionCart;
	}

	public Mapper getMapper() {
		return mapper;
	}

	public void setMapper(Mapper mapper) {
		this.mapper = mapper;
	}

	public List<MapperPOJO> getFilteredDrugs() {
		return filteredDrugs;
	}

	public void setFilteredDrugs(List<MapperPOJO> filteredDrugs) {
		this.filteredDrugs = filteredDrugs;
	}

}
