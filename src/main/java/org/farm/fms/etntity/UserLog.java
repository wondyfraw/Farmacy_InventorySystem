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
@Table(name = "users_log", schema = "myfms")
public class UserLog implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idUserLog;
	private Users usersId;
	private String userName;
	private String userIp;
	private Date logoutTime;
	private Date loginTime;
	private int status;

	public UserLog() {
		// defualt contractor used by JPA
	}

	@Id
	@SequenceGenerator(name = "users_log_id_users_log_seq", sequenceName = "users_log_id_users_log_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_log_id_users_log_seq")
	@Column(name = "id_users_log", unique = true, nullable = false)
	public Integer getIdUserLog() {
		return idUserLog;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_user")
	public Users getUsersId() {
		return usersId;
	}

	@Column(name = "usename")
	public String getUserName() {
		return userName;
	}

	@Column(name = "user_ip")
	public String getUserIp() {
		return userIp;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "logout_time")
	public Date getLogoutTime() {
		return logoutTime;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "login_time")
	public Date getLoginTime() {
		return loginTime;
	}

	public int getStatus() {
		return status;
	}

	public void setIdUserLog(Integer idUserLog) {
		this.idUserLog = idUserLog;
	}

	public void setUsersId(Users usersId) {
		this.usersId = usersId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
