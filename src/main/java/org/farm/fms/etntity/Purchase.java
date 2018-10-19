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
@Table(name = "purchase", schema = "myfms")
public class Purchase implements Serializable {

	private static final long serialVersionUID = -3179810734635752244L;

	private Integer purchaseId;
	private Integer quantityInBox;
	private Date registrationDate;
	private Double totalPrice;
	private Store store;

	public Purchase() {

	}

	@Id
	@SequenceGenerator(name = "myfms.purchase_id_purchase_seq", sequenceName = "myfms.purchase_id_purchase_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myfms.purchase_id_purchase_seq")
	@Column(name = "id_purchase", unique = true, nullable = false)
	public Integer getPurchaseId() {
		return purchaseId;
	}

	@Column(name = "quantity_in_box")
	public Integer getQuantityInBox() {
		return quantityInBox;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "registration_date")
	public Date getRegistrationDate() {
		return registrationDate;
	}

	@Column(name = "tatal_price")
	public Double getTotalPrice() {
		return totalPrice;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_store")
	public Store getStore() {
		return store;
	}

	public void setPurchaseId(Integer purchaseId) {
		this.purchaseId = purchaseId;
	}

	public void setQuantityInBox(Integer quantityInBox) {
		this.quantityInBox = quantityInBox;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void setStore(Store sore) {
		this.store = sore;
	}

}
