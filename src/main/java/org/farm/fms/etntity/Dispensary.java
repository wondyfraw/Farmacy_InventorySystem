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
@Table(name = "dispensary", schema = "myfms")

public class Dispensary implements Serializable {

	private static final long serialVersionUID = -6235475466960061883L;

	private Integer idDispensary;
	private Store store;
	private Integer quantityInBox; // quantity_in_box
	private Integer quantityPerUnit; // total drug quantity
	private Integer quantityPerTab; // quantity_per_unit
	private Integer quantityPerPack; // quantity_per_pack
	private Date dispensaryDate;
	private String adminName;

	public Dispensary() {
		// defualt constructor
	}

	@Id
	@SequenceGenerator(name = "myfms.dispensary_id_dispensary_seq", sequenceName = "myfms.dispensary_id_dispensary_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myfms.dispensary_id_dispensary_seq")
	@Column(name = "id_dispensary", unique = true, nullable = false)
	public Integer getIdDispensary() {
		return idDispensary;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_store")
	public Store getStore() {
		return store;
	}

	@Column(name = "quantity_in_box")
	public Integer getQuantityInBox() {
		return quantityInBox;
	}

	@Column(name = "quantity_per_unit")
	public Integer getQuantityPerUnit() {
		return quantityPerUnit;
	}

	@Column(name = "quantity_per_pack_unit")
	public Integer getQuantityPerTab() {
		return quantityPerTab;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "dispensary_date")
	public Date getDispensaryDate() {
		return dispensaryDate;
	}

	@Column(name = "admin_name")
	public String getAdminName() {
		return adminName;
	}

	@Column(name = "quantity_per_pack")
	public Integer getQuantityPerPack() {
		return quantityPerPack;
	}

	public void setDispensaryDate(Date dispensaryDate) {
		this.dispensaryDate = dispensaryDate;
	}

	public void setIdDispensary(Integer idDispensary) {
		this.idDispensary = idDispensary;
	}

	public void setStore(Store storeId) {
		this.store = storeId;
	}

	public void setQuantityInBox(Integer quantityInBox) {
		this.quantityInBox = quantityInBox;
	}

	public void setQuantityPerUnit(Integer quantityPerUnit) {
		this.quantityPerUnit = quantityPerUnit;
	}

	public void setQuantityPerTab(Integer quantityPerTab) {
		this.quantityPerTab = quantityPerTab;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public void setQuantityPerPack(Integer quantityPerPack) {
		this.quantityPerPack = quantityPerPack;
	}

}
