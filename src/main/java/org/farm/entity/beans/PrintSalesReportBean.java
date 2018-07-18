package org.farm.entity.beans;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.farm.fms.entity.ejb.SalesEJB;
import org.farm.fms.etntity.Sales;
import org.farm.pojo.SalesFilterPOJO;

@ManagedBean(name = "printSalesReportBean")
@ViewScoped
public class PrintSalesReportBean {

	private List<Sales> reportList;
	private SalesFilterPOJO sales;

	@EJB
	private SalesEJB salesEJB;

	@PostConstruct
	public void init() {

		sales = new SalesFilterPOJO();
		// Map<String, String> map = FacesContext.getCurrentInstance().getExternalContext().getInitParameterMap();
		Map<String, String> map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String fDate = map.get("fDate");
		String tDate = map.get("tDate");
		String dName = map.get("dname");
		if (!fDate.equals("null"))
			sales.setFromDate(salesEJB.convertDate(fDate));
		if (!tDate.equals("null"))
			sales.setToDate(salesEJB.convertDate(tDate));
		if (!dName.equals("null"))
			sales.setDrugName(dName);
		/*
		 * if(!map.get("fDate").equals("")){ String[] parm = map.get("fDate").split(","); int index = parm.length;
		 * if(index >=1){ sales.setFromDate(salesEJB.convertDate(parm[0])); } if(index >=2)
		 * sales.setToDate(salesEJB.convertDate(parm[1]));
		 * 
		 * if(index >= 3) sales.setDrugName(parm[2]); }
		 */

		reportList = salesEJB.filterDrugsByCriteria(sales);
	}

	public List<Sales> getReportList() {
		return reportList;
	}

	public void setReportList(List<Sales> reportList) {
		this.reportList = reportList;
	}

	public SalesFilterPOJO getSales() {
		return sales;
	}

	public void setSales(SalesFilterPOJO sales) {
		this.sales = sales;
	}

}
