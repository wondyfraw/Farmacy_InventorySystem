package org.farm.entity.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.farm.fms.etntity.Sales;

@ManagedBean(name = "invoiceReport")
@SessionScoped
public class InvoiceReport extends AbstructSessionBean {
	private List<Sales> salesSessionList;
	private List<Sales> tempList;
	private boolean showDispensaryTable;
	private Sales invoiceCart;

	@PostConstruct
	public void init() {
		tempList = new ArrayList<Sales>();
		showDispensaryTable = false;
	}

	public void addToInvoiceSession(Sales salesInvoice) {
		boolean found = false;
		if (salesInvoice != null) {
			for (int i = 0; i < tempList.size(); i++) {
				Sales sales = tempList.get(i);
				if (sales.getIdSales() != null && sales.getIdSales().equals(salesInvoice.getIdSales())) {
					found = true;
					tempList.get(i).setQuantity(sales.getQuantity() + salesInvoice.getQuantity());
				}
			}
			if (tempList.size() > 0 && found == false) {
				tempList.add(salesInvoice);
			}
			if (tempList.size() < 1 && found == false) {
				tempList.add(salesInvoice);
			}
			salesSessionList = tempList;
			showDispensaryTable = true;
		}
	}

	public List<Sales> getSalesSessionList() {
		return salesSessionList;
	}

	public void setSalesSessionList(List<Sales> salesSessionList) {
		this.salesSessionList = salesSessionList;
	}

	public List<Sales> getTempList() {
		return tempList;
	}

	public void setTempList(List<Sales> tempList) {
		this.tempList = tempList;
	}

	public boolean isShowDispensaryTable() {
		return showDispensaryTable;
	}

	public void setShowDispensaryTable(boolean showDispensaryTable) {
		this.showDispensaryTable = showDispensaryTable;
	}

	public Sales getInvoiceCart() {
		return invoiceCart;
	}

	public void setInvoiceCart(Sales invoiceCart) {
		this.invoiceCart = invoiceCart;
	}

}
