package org.farm.entity.beans;

import java.io.IOException;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.farm.exception.MyUserException;
import org.farm.fms.entity.ejb.UsersEJB;
import org.farm.fms.etntity.Users;
import org.farm.utils.Utils;

@ManagedBean(name = "loginAuthenticationBean")
@SessionScoped
public class LoginAuthenticationBean {

	private static Log log = LogFactory.getLog(LoginAuthenticationBean.class);

	@EJB
	private UsersEJB userEJB;

	@ManagedProperty(value = "#{navigationBean}")
	private NavigationBean navigationBean;

	private Users usersFromDb;
	private String email;
	private String password;
	private String username;
	private String userType;
	private boolean loggedIn;

	public static final String REGISTER_USER = "/secure/registerUser.xhtml?faces-redirect=true";
	public static final String LOGIN = "/public/login1.xhtml";

	/*
	 * public LoginAuthenticationBean(String username) { this.username = username; getUser(); }
	 */

	public LoginAuthenticationBean() {

	}

	/*
	 * @PostConstruct public void init() {
	 * 
	 * FacesContext fc = FacesContext.getCurrentInstance(); ExternalContext ec = fc.getExternalContext(); username =
	 * ec.getUserPrincipal().getName(); }
	 * 
	 * private void getUser() { if (username == null) { FacesContext fc = FacesContext.getCurrentInstance();
	 * ExternalContext ec = fc.getExternalContext(); username = ec.getUserPrincipal().getName(); }
	 * 
	 * }
	 */

	/**
	 * Login operation.
	 * 
	 * @return
	 * @throws MyUserException
	 */
	public String doLogin() throws MyUserException {

		// Get username and password from database :)
		if (this.email != null) {
			usersFromDb = userEJB.searchUserByEmail(this.email);
			if (usersFromDb != null) {

				userType = usersFromDb.getUserType();
				log.info("Name = " + usersFromDb.getFullName() + " Email= " + usersFromDb.getEmail());
			}

			// Successful login
			if (usersFromDb.getEmail().equals(this.email)
					&& (Utils.compareSHA1(this.password, usersFromDb.getPassword()))) {

				loggedIn = true;
				String completePath = null;
				if (usersFromDb.getUserType().equals("Administrator"))
					completePath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()
							+ "/secure/mainAdmin.xhtml?faces-redirect=true";
				else {
					if (usersFromDb.getUserType().equals("User"))
						completePath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()
								+ "/secure/users/mainUser.xhtml?faces-redirect=true";
				}
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect(completePath);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return navigationBean.redirectToWelcome();
			}
		}
		// Set login ERROR
		// FacesMessage msg = new FacesMessage("Login error!", "ERROR MSG");
		// msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		// FacesContext.getCurrentInstance().addMessage(null, msg);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login authentication error!", "ERROR MSG"));

		// To to login page
		return navigationBean.toLogin();
	}

	/**
	 * Logout operation.
	 * 
	 * @return
	 */
	public String doLogout() {

		// Set the paremeter indicating that user is logged in to false
		loggedIn = false;
		this.usersFromDb = null;

		// Set logout message
		FacesMessage msg = new FacesMessage("Logout success!", "INFO MSG");
		msg.setSeverity(FacesMessage.SEVERITY_INFO);
		FacesContext.getCurrentInstance().addMessage(null, msg);

		String completePath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()
				+ "/public/login1.xhtml";
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(completePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return navigationBean.toLogin();
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Users getUsersFromDb() {
		return usersFromDb;
	}

	public void setUsersFromDb(Users usersFromDb) {
		this.usersFromDb = usersFromDb;
	}

	public NavigationBean getNavigationBean() {
		return navigationBean;
	}

	public void setNavigationBean(NavigationBean navigationBean) {
		this.navigationBean = navigationBean;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

}
