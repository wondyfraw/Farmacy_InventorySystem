package org.farm.fms.etntity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "farmacist", schema = "myfms")
public class Farmacist implements Serializable {

	private static final long serialVersionUID = -4542674271969371492L;

	private Integer idFarmacist;
	private String specilization;
	private String farmacistName;
	private String address;
	private Double farmacistFee;
	private String phoneNumber;
	private String emailAddress;
	private String password;
	private Date creationDate;
	private Date unpdatedDate;

	public Farmacist() {
		// defualt constructor
	}

	@Id
	@SequenceGenerator(name = "farmacist_id_farmacist_seq", sequenceName = "farmacist_id_farmacist_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "farmacist_id_farmacist_seq")
	@Column(name = "id_farmacist", unique = true, nullable = false)
	public Integer getIdFarmacist() {
		return idFarmacist;
	}

	@Column(name = "specilization")
	public String getSpecilization() {
		return specilization;
	}

	@Column(name = "farmacist_name")
	public String getFarmacistName() {
		return farmacistName;
	}

	@Column(name = "address")
	public String getAddress() {
		return address;
	}

	@Column(name = "farmacist_fee")
	public Double getFarmacistFee() {
		return farmacistFee;
	}

	@Column(name = "phone_number")
	public String getPhoneNumber() {
		return phoneNumber;
	}

	@Column(name = "email_address")
	public String getEmailAddress() {
		return emailAddress;
	}

	@Column(name = "password")
	public String getPassword() {
		return password;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "creation_date")
	public Date getCreationDate() {
		return creationDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "updated_date")
	public Date getUnpdatedDate() {
		return unpdatedDate;
	}

	public void setIdFarmacist(Integer idFarmacist) {
		this.idFarmacist = idFarmacist;
	}

	public void setSpecilization(String specilization) {
		this.specilization = specilization;
	}

	public void setFarmacistName(String farmacistName) {
		this.farmacistName = farmacistName;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setFarmacistFee(Double farmacistFee) {
		this.farmacistFee = farmacistFee;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public void setUnpdatedDate(Date unpdatedDate) {
		this.unpdatedDate = unpdatedDate;
	}

}
