package org.farm.pojo;

import java.util.Date;

public class SalesFilterPOJO implements IDTO {
	private static final long serialVersionUID = 3709466949877043364L;

	private Date fromDate;
	private Date toDate;
	private String drugName;
	private Date expireDate;
	private boolean onlyExpired;

	public SalesFilterPOJO() {

	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public boolean isOnlyExpired() {
		return onlyExpired;
	}

	public void setOnlyExpired(boolean onlyExpired) {
		this.onlyExpired = onlyExpired;
	}

}
