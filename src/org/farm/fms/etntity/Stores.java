package org.farm.fms.etntity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "store", schema = "myfms")
@NamedQueries(value = {
		@NamedQuery(name = "storeSearchByDrugName", query = "select drug from Stores drug WHERE drug.drugName = :drugName ORDER BY drug.expireDate desc") })
public class Stores implements Serializable {

	private static final long serialVersionUID = 6867314894042660887L;

	private Integer storeId;
	private String drugName;
	private Integer quantityInBox;
	private Integer quantityperBoxperUnit;
	private Integer quantityperUnitperTab;
	private String brand;
	private String bathcNumber;
	private Date registrationDate;
	private String weight;
	private Date manufacturingDate;
	private Date expireDate;
	private String unit;
	private Double unitPrice;
	private Double totalPrice;
	private Double salesPrice;

	public Stores() {
		// required by JPA
	}

	@Id
	@SequenceGenerator(name = "myfms.stor_id_Store_seq", sequenceName = "myfms.stor_id_Store_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myfms.stor_id_Store_seq")
	@Column(name = "id_store", unique = true, nullable = false)
	public Integer getStoreId() {
		return storeId;
	}

	@Column(name = "drugName", nullable = false, length = 255)
	public String getDrugName() {
		return drugName;
	}

	@Column(name = "quantity_in_box")
	public Integer getQuantityInBox() {
		return quantityInBox;
	}

	@Column(name = "quantity_per_box_per_Unit")
	public Integer getQuantityperBoxperUnit() {
		return quantityperBoxperUnit;
	}

	@Column(name = "quantity_per_unit_per_tab")
	public Integer getQuantityperUnitperTab() {
		return quantityperUnitperTab;
	}

	@Column(name = "brand")
	public String getBrand() {
		return brand;
	}

	@Column(name = "batch_number")
	public String getBathcNumber() {
		return bathcNumber;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "registration_date")
	public Date getRegistrationDate() {
		return registrationDate;
	}

	@Column(name = "weight", nullable = false)
	public String getWeight() {
		return weight;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "manufactury_date", nullable = false)
	public Date getManufacturingDate() {
		return manufacturingDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "expire_date", nullable = false)
	public Date getExpireDate() {
		return expireDate;
	}

	@Column(name = "unit", nullable = false)
	public String getUnit() {
		return unit;
	}

	@Column(name = "unit_price")
	public Double getUnitPrice() {
		return unitPrice;
	}

	@Column(name = "tatal_price")
	public Double getTotalPrice() {
		return totalPrice;
	}

	@Column(name = "sales_price")
	public Double getSalesPrice() {
		return salesPrice;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public void setQuantityInBox(Integer quantityInBox) {
		this.quantityInBox = quantityInBox;
	}

	public void setQuantityperBoxperUnit(Integer quantityperBoxperUnit) {
		this.quantityperBoxperUnit = quantityperBoxperUnit;
	}

	public void setQuantityperUnitperTab(Integer quantityperUnitperTab) {
		this.quantityperUnitperTab = quantityperUnitperTab;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public void setBathcNumber(String bathcNumber) {
		this.bathcNumber = bathcNumber;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public void setManufacturingDate(Date manufacturingDate) {
		this.manufacturingDate = manufacturingDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void setSalesPrice(Double salesPrice) {
		this.salesPrice = salesPrice;
	}

}
