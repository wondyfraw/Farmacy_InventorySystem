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
	private Store storeId;
	private Integer quantityInBox;
	private Integer quantityPerUnit;
	private Integer quantityPerTab;
	private Date dispensaryDate;

	public Dispensary() {
		// defualt constructor
	}

	@Id
	@SequenceGenerator(name = "dispensary_id_dispensary_seq", sequenceName = "dispensary_id_dispensary_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dispensary_id_dispensary_seq")
	@Column(name = "id_dispensary", unique = true, nullable = false)
	public Integer getIdDispensary() {
		return idDispensary;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_store")
	public Store getStoreId() {
		return storeId;
	}

	@Column(name = "quantity_in_box")
	public Integer getQuantityInBox() {
		return quantityInBox;
	}

	@Column(name = "quantity_per_unit")
	public Integer getQuantityPerUnit() {
		return quantityPerUnit;
	}

	@Column(name = "quantity_per_tab")
	public Integer getQuantityPerTab() {
		return quantityPerTab;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "dispensary_date")
	public Date getDispensaryDate() {
		return dispensaryDate;
	}

	public void setDispensaryDate(Date dispensaryDate) {
		this.dispensaryDate = dispensaryDate;
	}

	public void setIdDispensary(Integer idDispensary) {
		this.idDispensary = idDispensary;
	}

	public void setStoreId(Store storeId) {
		this.storeId = storeId;
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

}
