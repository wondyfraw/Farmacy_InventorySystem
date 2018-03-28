package org.farm.entity.beans;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.farm.exception.MyStoreException;
import org.farm.fms.entity.ejb.UsersEJB;
import org.farm.fms.etntity.Users;

@ManagedBean(name = "userRegistrationBean")
@ViewScoped
public class UserRegistrationBean {

	protected Log log = LogFactory.getLog(this.getClass());
	private Users users;
	private String passwordConfirm;
	private String userType;
	private List<Users> userList;

	@EJB
	private UsersEJB usersEJB;

	@PostConstruct
	public void init() {
		users = new Users();
		userList = new ArrayList<Users>();
		userList = usersEJB.findAllUsers();
	}

	public void registerUser() {
		Date now = Calendar.getInstance().getTime();
		if (users.getPassword() != null)
			System.out.println("Name = " + this.users.getFullName() + " Address= " + this.users.getAddress()
					+ "password= " + this.users.getPassword());
		if (users != null) {
			if (users.getFullName() != null && users.getAddress() != null && users.getEmail() != null
					&& users.getPassword() != null) {
				users.setRegistrationdate(now);
				usersEJB.register(users);

				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successfully register user "));
				// usersEJB.persistEntity(users);
			} else if (!this.passwordConfirm.equals(users.getPassword())) {

			} else {
				String message = "Required field can not be null";
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
			}
		}
	}

	public void validateSamePassword(FacesContext context, UIComponent toValidate, Object value)
			throws MyStoreException {
		String confirmPassword = (String) value;
		if (!confirmPassword.equals(users.getPassword())) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Passwords do not match!",
					"Passwords do not match!");
			context.addMessage(toValidate.getClientId(), message);
			throw new ValidatorException(message);
			// throw new MyStoreException("Passwords do not match!");
		}
	}

	public void validatePassword(FacesContext context, UIComponent toValidate, Object value) {
		String confirm = (String) value;
		UIInput passComp = (UIInput) toValidate.getAttributes().get("passwordComponent");
		String password = (String) passComp.getValue();
		if (!password.equals(confirm)) {
			FacesMessage message = new FacesMessage("Password and Confirm Password Should match");
			throw new ValidatorException(message);
		}
	}

	public void deleteUser() {
		String userId = null;
		Users users = new Users();
		Integer usersId;
		int index = 0;
		Map<String, String> map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		if ((map.get("id")) != null) {
			usersId = Integer.parseInt(StringUtils.defaultString(map.get("id"), userId));
			users = usersEJB.findById(usersId);

			if (users != null) {
				for (int i = 0; i < userList.size(); i++) {
					Users element = userList.get(i);
					if (element.getUsersId() != null && element.getUsersId().equals(usersId)) {
						index = i;
						break;
					}
				}
				userList.remove(index);
				usersEJB.removeById(usersId);
			}
		}
	}

	public void successRegistrationMessage() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Successful", "Successully register the user "));

	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public List<Users> getUserList() {
		return userList;
	}

	public void setUserList(List<Users> userList) {
		this.userList = userList;
	}

}
