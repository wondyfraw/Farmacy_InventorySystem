package org.farm.pojo;

import java.io.Serializable;
import java.util.Date;

public class MapperPOJO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3267350695461307045L;
	// from store
	private Integer storeId;
	private String drugName;
	private String brand;
	private String bathcNumber;
	private String weight;
	private Date manufacturingDate;
	private Date expireDate;
	private String unit;
	private Date registrationDate;
	private Double salesPrice;
	private String packUnit;
	private String packType;
	private Double totalPrice;
	private Double unitPrice;

	// from dispensary
	private Integer dispensaryId;
	private Integer quantityInBox; // quantity_in_box
	private Integer quantityPerUnit; // total drug quantity
	private Integer quantityPerUnitPack; // quantity_per_unit
	private Integer quantityPerPack; // quantity_per_pack
	private Integer totalUnitPack;

	// from inputbox
	private Integer dose;

	public MapperPOJO() {

	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getBathcNumber() {
		return bathcNumber;
	}

	public void setBathcNumber(String bathcNumber) {
		this.bathcNumber = bathcNumber;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public Date getManufacturingDate() {
		return manufacturingDate;
	}

	public void setManufacturingDate(Date manufacturingDate) {
		this.manufacturingDate = manufacturingDate;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Double getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(Double salesPrice) {
		this.salesPrice = salesPrice;
	}

	public String getPackUnit() {
		return packUnit;
	}

	public void setPackUnit(String packUnit) {
		this.packUnit = packUnit;
	}

	public Integer getQuantityInBox() {
		return quantityInBox;
	}

	public void setQuantityInBox(Integer quantityInBox) {
		this.quantityInBox = quantityInBox;
	}

	public Integer getQuantityPerUnit() {
		return quantityPerUnit;
	}

	public void setQuantityPerUnit(Integer quantityPerUnit) {
		this.quantityPerUnit = quantityPerUnit;
	}

	public Integer getQuantityPerUnitPack() {
		return quantityPerUnitPack;
	}

	public void setQuantityPerUnitPack(Integer quantityPerUnitPack) {
		this.quantityPerUnitPack = quantityPerUnitPack;
	}

	public Integer getQuantityPerPack() {
		return quantityPerPack;
	}

	public void setQuantityPerPack(Integer quantityPerPack) {
		this.quantityPerPack = quantityPerPack;
	}

	public Integer getDispensaryId() {
		return dispensaryId;
	}

	public void setDispensaryId(Integer dispensaryId) {
		this.dispensaryId = dispensaryId;
	}

	public Integer getDose() {
		return dose;
	}

	public void setDose(Integer dose) {
		this.dose = dose;
	}

	public String getPackType() {
		return packType;
	}

	public void setPackType(String packType) {
		this.packType = packType;
	}

	public Integer getTotalUnitPack() {
		return totalUnitPack;
	}

	public void setTotalUnitPack(Integer totalUnitPack) {
		this.totalUnitPack = totalUnitPack;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

}
