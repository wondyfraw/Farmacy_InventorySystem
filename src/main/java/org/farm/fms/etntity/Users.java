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
@Table(name = "users", schema = "myfms")
@NamedQueries(value = {
		@NamedQuery(name = "searchUserByEmail", query = "select user from Users user WHERE user.email = :email") })
public class Users implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer usersId;
	private String fullName;
	private String address;
	private String email;
	private String password;
	private Date registrationdate;
	private Date updatedDate;
	private String userType;

	public Users() {
		// defualt constructor
	}

	@Id
	@SequenceGenerator(name = "myfms.users_id_user_seq", sequenceName = "myfms.users_id_user_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myfms.users_id_user_seq")
	@Column(name = "id_user", unique = true, nullable = false)
	public Integer getUsersId() {
		return usersId;
	}

	@Column(name = "full_name")
	public String getFullName() {
		return fullName;
	}

	@Column(name = "address")
	public String getAddress() {
		return address;
	}

	@Column(name = "email_address", nullable = false)
	public String getEmail() {
		return email;
	}

	@Column(name = "password", nullable = false)
	public String getPassword() {
		return password;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "registration_date")
	public Date getRegistrationdate() {
		return registrationdate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "updated_date")
	public Date getUpdatedDate() {
		return updatedDate;
	}

	@Column(name = "user_type")
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public void setUsersId(Integer usersId) {
		this.usersId = usersId;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRegistrationdate(Date registrationdate) {
		this.registrationdate = registrationdate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

}
