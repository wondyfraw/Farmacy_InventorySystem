package org.farm.fms.etntity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "sales", schema = "myfms")
public class Sales implements Serializable {

	private static final long serialVersionUID = 966903096023682335L;

	private Integer idSales;
	private Dispensary idDispensary;
	private String drugName;
	private Integer quantity;
	private String weight;
	private String brand;
	private String batchNumber;
	private String unit;
	private Date registrationdate;
	private Double unitPrice;
	private Double totalPrice;

	public Sales() {
		// defuale constructor
	}

	@Id
	@SequenceGenerator(name = "sales_id_sales_seq", sequenceName = "sales_id_sales_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sales_id_sales_seq")
	@Column(name = "id_sales", unique = true, nullable = false)
	public Integer getIdSales() {
		return idSales;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_dispensary")
	public Dispensary getIdDispensary() {
		return idDispensary;
	}

	@Column(name = "drug_name", nullable = false, length = 255)
	public String getDrugName() {
		return drugName;
	}

	@Column(name = "quantity", nullable = false)
	public Integer getQuantity() {
		return quantity;
	}

	@Column(name = "weight", nullable = false)
	public String getWeight() {
		return weight;
	}

	@Column(name = "brand")
	public String getBrand() {
		return brand;
	}

	@Column(name = "batch_number")
	public String getBatchNumber() {
		return batchNumber;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "registration_date")
	public Date getRegistrationdate() {
		return registrationdate;
	}

	@Column(name = "unit_price", nullable = false)
	public Double getUnitPrice() {
		return unitPrice;
	}

	@Column(name = "total_price", nullable = false)
	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setIdSales(Integer idSales) {
		this.idSales = idSales;
	}

	public void setIdDispensary(Dispensary idDispensary) {
		this.idDispensary = idDispensary;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public void setRegistrationdate(Date registrationdate) {
		this.registrationdate = registrationdate;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

}
