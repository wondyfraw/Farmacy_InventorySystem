package org.farm.entity.beans;

import java.io.InputStream;
import java.text.SimpleDateFormat;
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
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.farm.fms.entity.ejb.DispensaryEJB;
import org.farm.fms.entity.ejb.PurchaseEJB;
import org.farm.fms.entity.ejb.SalesEJB;
import org.farm.fms.entity.ejb.StoreEJB;
import org.farm.fms.etntity.Dispensary;
import org.farm.fms.etntity.Purchase;
import org.farm.fms.etntity.Sales;
import org.farm.fms.etntity.Store;
import org.farm.pojo.DispensaryPOJO;
import org.farm.pojo.Mapper;
import org.farm.pojo.MapperPOJO;
import org.farm.pojo.SalesFilterPOJO;
import org.farm.pojo.SalesPOJO;
import org.farm.utils.ConstantsSingleton;
import org.farm.utils.ManageCart;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean(name = "medicineRegistrationBean")
@ViewScoped
public class MedicineRegistrationBean extends AbstructSessionBean {

	Log log = LogFactory.getLog(MedicineRegistrationBean.class);

	private Purchase purchase;
	private Store store;
	private Store dispensaryDrug;
	private Store deleteDrug;
	private Store dispensaryCart;
	private List<String> packUnit;
	private List<String> units;
	private Map<String, List<String>> data;
	private String unitPack;
	private String boxPack;
	private List<Store> drugList;
	private List<Store> dispensary_drugList;
	private boolean showQuantityperStrip;
	private boolean showSaveButton;
	private boolean showDispensaryTable;
	private Integer quantity;

	private ManageCart manageCart;
	private DispensaryPOJO dispensaryPOJO;
	private Dispensary dispensary;

	private List<MapperPOJO> session_drugList;
	private List<MapperPOJO> mapperPOJOList;
	private List<MapperPOJO> purchaseDrug;
	private Mapper mapper;
	private MapperPOJO salesPOJO;
	private String salesQuantity;
	private String dose;
	private MapperPOJO sessionCart;
	private SalesPOJO salesMaperPOJO;
	private Sales sales;
	private List<MapperPOJO> filteredDrugs;
	private List<Sales> salesList;
	private String filter;
	private List<Sales> filteredSalesDrugs;
	private boolean showMessagePanel;
	private boolean showTabQuantityInStrip;
	private SalesFilterPOJO searchParmes;
	private SalesFilterPOJO purchaseSearchParmes;
	private List<Sales> searchSalesList;
	private FacesContext context = FacesContext.getCurrentInstance();
	private HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
	private HttpSession httpSession = request.getSession(false);
	private Sales voidSalesDrug;

	private Date reportDate;
	private List<Sales> reportList;
	private List<MapperPOJO> purchaseReportList;
	private String sampleDate;
	private String userType;
	private Double totalPrice;

	@EJB
	private StoreEJB storeEJB;

	@EJB
	private DispensaryEJB dispensaryEJB;

	@EJB
	private SalesEJB salesEJB;

	@EJB
	private PurchaseEJB purchaseEJB;

	@PostConstruct
	public void init() {
		store = new Store();
		purchase = new Purchase();
		packUnit = new ArrayList<String>();
		units = new ArrayList<String>();
		data = new HashMap<String, List<String>>();
		drugList = new ArrayList<Store>();
		dispensary_drugList = new ArrayList<Store>();
		manageCart = new ManageCart();
		dispensaryPOJO = new DispensaryPOJO();
		dispensary = new Dispensary();
		purchaseDrug = new ArrayList<MapperPOJO>();

		String dates = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		Date toDate = salesEJB.convertDate(dates);
		drugList = storeEJB.filterDrugByExpireDate(toDate);
		// drugList = storeEJB.findAll();

		packUnit = ConstantsSingleton.packType();
		units = ConstantsSingleton.packUnit();

		/*
		 * List<String> unit = new ArrayList<String>(); unit.add("Tablet"); unit.add("Capsule"); unit.add("Ampule");
		 * data.put("Strip", unit);
		 * 
		 * unit = new ArrayList<String>(); unit.add("Tablet"); unit.add("Capsule"); data.put("Cup", unit);
		 * 
		 * unit = new ArrayList<String>(); unit.add("Glove"); unit.add("Tooth soap"); unit.add("Condom");
		 * unit.add("Cream"); unit.add("IV Fload"); unit.add("IV Set"); unit.add("Injection needle");
		 * unit.add("Butterfly needle"); unit.add("Powder"); unit.add("Modis"); unit.add("Baby diaper");
		 * unit.add("Tooth Brash"); data.put("Piece", unit);
		 */

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

		//////////// Sales ====//////
		session_drugList = new ArrayList<MapperPOJO>();
		mapperPOJOList = new ArrayList<MapperPOJO>();
		mapper = new Mapper();
		salesMaperPOJO = new SalesPOJO();
		sales = new Sales();
		salesList = new ArrayList<Sales>();
		filteredSalesDrugs = new ArrayList<Sales>();

		searchParmes = (SalesFilterPOJO) httpSession.getAttribute("searchParmes");
		purchaseSearchParmes = (SalesFilterPOJO) httpSession.getAttribute("purchaseSearchParmes");

		if (searchParmes != null) {
			reportList = salesEJB.filterDrugsByCriteria(searchParmes);
		}

		if (purchaseSearchParmes != null) {
			purchaseReportList = storeEJB.filterDrugsByCriteria(purchaseSearchParmes);
		}

		if (searchParmes == null)
			searchParmes = new SalesFilterPOJO();

		if (purchaseSearchParmes == null)
			purchaseSearchParmes = new SalesFilterPOJO();

		reportDate = Calendar.getInstance().getTime();
		sampleDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());

		// dispensaryDrug = new MapperPOJO();

		// dispensaryList = dispensaryEJB.findAll();

		/*
		 * for (int i = 0; i < dispensaryList.size(); i++) { Dispensary dispensary = new Dispensary(); dispensary =
		 * dispensaryList.get(i); if (dispensary.getStore() != null) { Mapper mapper = new Mapper(); Store store = new
		 * Store(); MapperPOJO mapperPOJO = new MapperPOJO(); store =
		 * storeEJB.findById(dispensary.getStore().getStoreId()); mapperPOJO = mapper.mapToSales(dispensary, store); if
		 * (mapperPOJO != null) { mapperPOJOList.add(mapperPOJO);
		 * 
		 * } else log.error("[--Retrun value of MapperPOJO Object in mapToSales function is null--]"); } }
		 */
		mapperPOJOList = salesEJB.getAllDrugs(Calendar.getInstance().getTime());
		salesList = salesEJB.findSalesOrderByDate();

		showTabQuantityInStrip = true;
		userType = loginAuthenticationBean.getUserType();

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
		purchase.setStore(store);
		purchase.setQuantityInBox(store.getQuantityInBox());
		purchase.setTotalPrice(store.getTotalPrice());
		purchase.setRegistrationDate(date);
		purchaseEJB.persistEntity(purchase);
		log.info("Successfully Register Drugs " + store.getDrugName() + " " + store.getQuantityperBoxperUnit() + ""
				+ store.getUnit());
		showMessagePanel = true;
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successfully save drug!!"));
	}

	public void onPackUnitSelected() {
		if (unitPack != null) {
			if (unitPack.equals("Strip"))
				showTabQuantityInStrip = true;
			else {
				showTabQuantityInStrip = false;
			}
		}
	}

	// show only if pack type is box
	public void showQuantityPerPackPerUnit() {
		if (store.getPackType().equals("Cup") || store.getPackType().equals("Packed"))
			showTabQuantityInStrip = false;
		else
			showTabQuantityInStrip = true;
	}

	public List<String> selectDrugPackType() {
		if (store.getPackType() != null) {
			packUnit = ConstantsSingleton.boxPackElement(store.getPackType());
		}
		return packUnit;
	}

	//
	public List<String> selectDrugWithPackageUnit() {
		if (unitPack != null) {
			units = ConstantsSingleton.drugPackType(unitPack);
		}

		return units;
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

		if (deleteDrug.getStoreId() != null) {
			List<Dispensary> dispensaryList = new ArrayList<Dispensary>();
			dispensaryList = dispensaryEJB.findDispensaryByStoreId(deleteDrug.getStoreId());
			for (Dispensary dispensary : dispensaryList) {
				dispensaryEJB.removeById(dispensary.getIdDispensary());
			}
			storeEJB.removeById(deleteDrug.getStoreId());
		}
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
			showMessagePanel = true;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successfuly save grugs"));
		}
	}

	/**
	 * Temporarily store selected drug in the dispenser cart session finally save to dispenser table and update store
	 * table
	 */
	public void saveDrugToDispensary() {

		for (int i = 0; i < dispensary_drugList.size(); i++) {
			Store element = dispensary_drugList.get(i);
			String email = loginAuthenticationBean.getEmail();
			dispensary = dispensaryPOJO.mapDispensaryPOJO(element, email);

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

				/** if the drug selected is found in the dispensary list */
				if (dispensary_drugList.size() > 0 && found == false) {
					dispensaryDrug.setQuantityInBox(quantity);
					dispensary_drugList.add(dispensaryDrug);
				}

				// if dispensary list is empty
				if (dispensary_drugList.size() <= 0 && found == false) {
					List<Store> newList = new ArrayList<Store>();
					Store drug = new Store();
					newList = manageCart.mapData(drug, dispensaryDrug, quantity, newList);
					dispensary_drugList = newList;
				}

				showDispensaryTable = true;
			}
		}
	}

	// ==============Add drugs to session cart ===================////

	public void addToSalesSession() {

		totalPrice = 0.0;
		if (salesPOJO != null) {
			int index = 0;
			Integer dosVal = null;
			Integer quantityVal = null;
			if (this.salesQuantity != null) {
				quantityVal = Integer.parseInt(this.salesQuantity);
			}
			if (this.dose != null)
				dosVal = Integer.parseInt(this.dose);
			for (int i = 0; i < mapperPOJOList.size(); i++) {
				MapperPOJO element = mapperPOJOList.get(i);
				if (element.getDispensaryId() != null
						&& element.getDispensaryId().equals(salesPOJO.getDispensaryId())) {
					if (dose != null && element.getQuantityPerUnit() > (dosVal * quantityVal)) {
						index = 1;
						Integer newQuantity = element.getQuantityPerUnit() - (dosVal * quantityVal);
						if (element.getPackType().equals("Box")) {
							Integer newUnitPackQuantity = element.getTotalUnitPack()
									- (dosVal * quantityVal) / element.getQuantityPerUnitPack(); // quantity per pack
																									// unit
							mapperPOJOList.get(i).setTotalUnitPack(newUnitPackQuantity);
						}
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
								&& element.getDispensaryId().equals(salesPOJO.getDispensaryId())) {
							found = true;
							session_drugList = mapper.modifyQuantity(session_drugList, quantityVal, dosVal, j);
						}
					}
				}

				if (session_drugList.size() > 0 && found == false) {
					salesPOJO.setQuantityPerUnit(quantityVal * dosVal);
					if (salesPOJO.getPackType().equals("Box"))
						salesPOJO.setTotalUnitPack((quantityVal * dosVal) / salesPOJO.getQuantityPerUnitPack());
					session_drugList.add(salesPOJO);
				}

				if (session_drugList.size() <= 0 && found == false) {
					List<MapperPOJO> newMapperPOJOList = new ArrayList<MapperPOJO>();
					MapperPOJO mapperPOJO = new MapperPOJO();
					newMapperPOJOList = mapper.mapdata(mapperPOJO, salesPOJO, quantityVal, dosVal);
					if (newMapperPOJOList != null)
						session_drugList = newMapperPOJOList;
				}

				totalPrice = calculateTotalPrice(session_drugList);
				showDispensaryTable = true;
			}
		}
	}

	public void saveSalesDrug() {
		for (int i = 0; i < session_drugList.size(); i++) {
			String salesPerson = loginAuthenticationBean.getEmail();
			MapperPOJO element = session_drugList.get(i);
			Dispensary dispensarydrug = dispensaryEJB.findById(element.getDispensaryId());
			if (dispensarydrug != null) {
				sales = salesMaperPOJO.mapSalesPOJO(element, salesPerson, dispensarydrug);
				if (sales != null) {
					salesEJB.persistEntity(sales); // save drugs to sales table
					for (int j = 0; j < mapperPOJOList.size(); j++) {
						MapperPOJO salesDrug = mapperPOJOList.get(j);
						if (salesDrug.getDispensaryId() != null
								&& salesDrug.getDispensaryId().equals(element.getDispensaryId())) {
							Dispensary dispennsary = new Dispensary();
							dispennsary = dispensaryEJB.findById(salesDrug.getDispensaryId());
							if (dispennsary != null) {
								dispennsary.setQuantityPerUnit(
										dispennsary.getQuantityPerUnit() - (sales.getDose() * sales.getQuantity())); // modify
								// quantity(existingQuantity
								// -
								// salesQuan)
								dispensaryEJB.merge(dispennsary); // update dispensary table in the db
							}
						}
					}
				}
			}
			session_drugList.remove(i);
		}
		showDispensaryTable = false;
	}

	/**
	 * void sales drugs by removing drugs from sales table add to dispensery table
	 */
	public void voidSales() {
		// check invoice with sales id
		if (voidSalesDrug != null) {

			// fist delete from sales table
			salesEJB.removeById(voidSalesDrug.getIdSales());

			// update Dispenser table
			Dispensary dispensary = new Dispensary();
			dispensary = dispensaryEJB.findById(voidSalesDrug.getDispensary());
			if (dispensary.getIdDispensary() != null) {
				Date now = Calendar.getInstance().getTime();
				dispensary.setModifiedDate(now);
				dispensary.setQuantityPerUnit(
						dispensary.getQuantityPerUnit() + voidSalesDrug.getDose() * voidSalesDrug.getQuantity());
				dispensary.setTotalUnitPack(
						dispensary.getTotalUnitPack() + ((voidSalesDrug.getDose() * voidSalesDrug.getQuantity())
								/ dispensary.getQuantityPerPackPerUnit()));
				dispensaryEJB.merge(dispensary);
			}
			salesList.remove(voidSalesDrug);
		}
	}

	// function to delete drugs from sales session cart
	public void deleteFromSalesSessionCart() {
		totalPrice = 0.0;
		if (sessionCart != null) {
			for (int i = 0; i < mapperPOJOList.size(); i++) {
				MapperPOJO elemet = mapperPOJOList.get(i);
				if (elemet.getDispensaryId() != null
						&& elemet.getDispensaryId().equals(sessionCart.getDispensaryId())) {
					mapperPOJOList.get(i).setQuantityPerUnit(
							sessionCart.getQuantityPerUnit() + mapperPOJOList.get(i).getQuantityPerUnit());
				}
			}

			session_drugList.remove(sessionCart);
			totalPrice = calculateTotalPrice(session_drugList);

		}
		if (session_drugList.size() < 1) {
			showDispensaryTable = false;
		}
	}

	public Double calculateTotalPrice(List<MapperPOJO> salesList) {
		Double price = 0.0;
		for (MapperPOJO mapper : session_drugList) {
			price = price + mapper.getSalesPrice() * mapper.getQuantityInBox();
		}

		return price;
	}

	// filter drug based on the key
	public void searchSalesDrugByFilterKy() {
		if (this.filter == null || this.filter.length() < 2)
			filteredSalesDrugs = salesEJB.findAll();
		else {
			salesEJB.salesDrugByFilterCriteria(this.filter);
		}
	}

	// search sales by filter criteria for generating report
	public void searchSalesByFilterCriateria() {
		searchSalesList = salesEJB.filterDrugsByCriteria(searchParmes);
		httpSession.setAttribute("searchParmes", searchParmes);
		showDispensaryTable = true;
	}

	// search drugs by filter criteria
	public void searchPurchaseDrugsByFilterCriteria() {
		purchaseDrug = storeEJB.filterDrugsByCriteria(purchaseSearchParmes);
		httpSession.setAttribute("purchaseSearchParmes", purchaseSearchParmes);
		if (purchaseDrug.size() > 0)
			showDispensaryTable = true;
	}

	public StreamedContent downloadSalesReport() {
		String fileName = null;
		if (searchParmes != null) {
			if (searchParmes.getFromDate() != null)
				fileName = "sales_" + new SimpleDateFormat("yyyy-MM-dd").format(searchParmes.getFromDate())
						+ Calendar.getInstance().getTimeInMillis() + ".pdf";
			else if (searchParmes.getToDate() != null)
				fileName = "sales_" + new SimpleDateFormat("yyyy-MM-dd").format(searchParmes.getToDate())
						+ Calendar.getInstance().getTimeInMillis() + ".pdf";
			else if (searchParmes.getDrugName() != null)
				fileName = "sales_" + searchParmes.getDrugName() + Calendar.getInstance().getTimeInMillis() + ".pdf";
			else
				fileName = "sales" + Calendar.getInstance().getTimeInMillis() + ".pdf";
		}

		InputStream printStream = salesEJB.getPrintHtmlFile(searchParmes);
		StreamedContent result = new DefaultStreamedContent(printStream, "application/pdf", fileName);
		log.info("Downlaod sales report successfully " + fileName);
		return result;
	}

	// sales PDF report with java ////

	public StreamedContent generatePDF() {
		StreamedContent result = null;
		try {
			String fileName;
			if (searchParmes.getDrugName() != null)
				fileName = "sales_" + searchParmes.getDrugName() + Calendar.getInstance().getTimeInMillis() + ".pdf";
			else
				fileName = "sales_" + Calendar.getInstance().getTimeInMillis() + ".pdf";
			List<Sales> salesList = new ArrayList<Sales>();
			salesList = salesEJB.filterDrugsByCriteria(searchParmes);
			InputStream printStream = salesEJB.createPDF(salesList, searchParmes);
			result = new DefaultStreamedContent(printStream, "application/pdf", fileName);
			log.info("Downlaod sales report successfully " + fileName);
		} catch (Exception e) {
			log.error("Download sales report is failed");
		}
		return result;
	}

	/**
	 * 
	 * @return
	 */

	public StreamedContent generatePurchasedPDF() {
		StreamedContent content = null;
		try {
			String fileName;
			if (purchaseSearchParmes.getDrugName() != null)
				fileName = "purchased_" + purchaseSearchParmes.getDrugName() + Calendar.getInstance().getTimeInMillis()
						+ ".pdf";
			else
				fileName = "purchased_" + Calendar.getInstance().getTimeInMillis() + ".pdf";
			List<MapperPOJO> purchasedList = new ArrayList<MapperPOJO>();
			purchasedList = storeEJB.filterDrugsByCriteria(purchaseSearchParmes);
			InputStream printStream = storeEJB.createPDF(purchasedList, purchaseSearchParmes);
			content = new DefaultStreamedContent(printStream, "application/pdf", fileName);
		} catch (Exception e) {
			log.error("Fail to Generate Purchased drug PDF");
		}
		return content;
	}

	///// jasper sales report //////////////////
	public void printSalesReport() {

		try {
			ServletContext servletContesct = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
					.getContext();
			String realPath = servletContesct.getRealPath("/");
			String relativePath = "/WEB-INF/report/sales_report.jrxml";
			String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath(relativePath);
			salesEJB.generateSalesPDF(salesList, realPath);
		} catch (Exception e) {
			log.error("Generating Sales PDF report is fail " + e.getMessage());
		}
	}

	/*
	 * public void downloadTest() { if (reportList.size() > 0) { int a = 0; } }
	 */

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

	///// =========Sales================== /////////////////

	public List<MapperPOJO> getSession_drugList() {
		return session_drugList;
	}

	public void setSession_drugList(List<MapperPOJO> session_drugList) {
		this.session_drugList = session_drugList;
	}

	public List<MapperPOJO> getMapperPOJOList() {
		return mapperPOJOList;
	}

	public void setMapperPOJOList(List<MapperPOJO> mapperPOJOList) {
		this.mapperPOJOList = mapperPOJOList;
	}

	public Mapper getMapper() {
		return mapper;
	}

	public void setMapper(Mapper mapper) {
		this.mapper = mapper;
	}

	public MapperPOJO getSalesPOJO() {
		return salesPOJO;
	}

	public void setSalesPOJO(MapperPOJO salesPOJO) {
		this.salesPOJO = salesPOJO;
	}

	public String getSalesQuantity() {
		return salesQuantity;
	}

	public void setSalesQuantity(String salesQuantity) {
		this.salesQuantity = salesQuantity;
	}

	public String getDose() {
		return dose;
	}

	public void setDose(String dose) {
		this.dose = dose;
	}

	public MapperPOJO getSessionCart() {
		return sessionCart;
	}

	public void setSessionCart(MapperPOJO sessionCart) {
		this.sessionCart = sessionCart;
	}

	public List<MapperPOJO> getFilteredDrugs() {
		return filteredDrugs;
	}

	public void setFilteredDrugs(List<MapperPOJO> filteredDrugs) {
		this.filteredDrugs = filteredDrugs;
	}

	public List<Sales> getSalesList() {
		return salesList;
	}

	public void setSalesList(List<Sales> salesList) {
		this.salesList = salesList;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public List<Sales> getFilteredSalesDrugs() {
		return filteredSalesDrugs;
	}

	public void setFilteredSalesDrugs(List<Sales> filteredSalesDrugs) {
		this.filteredSalesDrugs = filteredSalesDrugs;
	}

	public boolean isShowMessagePanel() {
		return showMessagePanel;
	}

	public void setShowMessagePanel(boolean showMessagePanel) {
		this.showMessagePanel = showMessagePanel;
	}

	public boolean isShowTabQuantityInStrip() {
		return showTabQuantityInStrip;
	}

	public void setShowTabQuantityInStrip(boolean showTabQuantityInStrip) {
		this.showTabQuantityInStrip = showTabQuantityInStrip;
	}

	public SalesFilterPOJO getSearchParmes() {
		return searchParmes;
	}

	public void setSearchParmes(SalesFilterPOJO searchParmes) {
		this.searchParmes = searchParmes;
	}

	public List<Sales> getSearchSalesList() {
		return searchSalesList;
	}

	public void setSearchSalesList(List<Sales> searchSalesList) {
		this.searchSalesList = searchSalesList;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public List<Sales> getReportList() {
		return reportList;
	}

	public void setReportList(List<Sales> reportList) {
		this.reportList = reportList;
	}

	public String getSampleDate() {
		return sampleDate;
	}

	public void setSampleDate(String sampleDate) {
		this.sampleDate = sampleDate;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Sales getVoidSalesDrug() {
		return voidSalesDrug;
	}

	public void setVoidSalesDrug(Sales voidSalesDrug) {
		this.voidSalesDrug = voidSalesDrug;
	}

	public String getBoxPack() {
		return boxPack;
	}

	public void setBoxPack(String boxPack) {
		this.boxPack = boxPack;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<MapperPOJO> getPurchaseDrug() {
		return purchaseDrug;
	}

	public void setPurchaseDrug(List<MapperPOJO> purchaseDrug) {
		this.purchaseDrug = purchaseDrug;
	}

	public SalesFilterPOJO getPurchaseSearchParmes() {
		return purchaseSearchParmes;
	}

	public void setPurchaseSearchParmes(SalesFilterPOJO purchaseSearchParmes) {
		this.purchaseSearchParmes = purchaseSearchParmes;
	}

	public List<MapperPOJO> getPurchaseReportList() {
		return purchaseReportList;
	}

	public void setPurchaseReportList(List<MapperPOJO> purchaseReportList) {
		this.purchaseReportList = purchaseReportList;
	}

}
