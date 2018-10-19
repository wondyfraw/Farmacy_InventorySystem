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
import org.farm.exception.MyUserException;
import org.farm.fms.entity.ejb.UsersEJB;
import org.farm.fms.etntity.Users;
import org.farm.utils.Utils;
import org.primefaces.context.RequestContext;

/**
 * 
 * @author wonde
 * @since 2018
 *
 */

@ManagedBean(name = "userRegistrationBean")
@ViewScoped
public class UserRegistrationBean extends AbstructSessionBean {

	protected Log log = LogFactory.getLog(this.getClass());
	private Users users;
	private String passwordConfirm;
	private String userType;
	private List<Users> userList;
	private boolean showPassword;
	private Integer userId;
	private boolean showMessage;
	private String newPassword;
	private String email;

	@EJB
	private UsersEJB usersEJB;

	@PostConstruct
	public void init() {
		users = new Users();
		userList = new ArrayList<Users>();
		showPassword = true;
		userList = usersEJB.findAllUsers();

		Map<String, String> map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

		if ((map.get("idUser")) != null) {
			userId = Integer.parseInt(map.get("idUser"));
			users = usersEJB.findById(userId);
			showPassword = false;
		}
	}

	/**
	 * make new user registration
	 */
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

	public void registerUserNew() {
		Date now = Calendar.getInstance().getTime();
		if (users != null) {
			if (users.getPassword() != null) {
				if (users.getUsersId() != null) {
					users.setUpdatedDate(now);
					users.setPassword(Utils.encryptSHA1(users.getPassword()));
					usersEJB.mergeEntity(users);
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage("Successfully edit user profile"));
				} else {
					users.setRegistrationdate(now);
					usersEJB.register(users);
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successfully register user "));
				}
				showMessage = true;
				RequestContext.getCurrentInstance().addCallbackParam("success", true);
				log.info("Successfully register user " + "Name = " + this.users.getFullName() + " Address= "
						+ this.users.getAddress() + "password= " + this.users.getPassword());

			}
		}
	}

	public void updatePassword() {
		Date now = Calendar.getInstance().getTime();
		users.setUpdatedDate(now);
		users.setEmail(loginAuthenticationBean.getEmail());
		users.setAddress(loginAuthenticationBean.getUsersFromDb().getAddress());
		users.setFullName(loginAuthenticationBean.getUsersFromDb().getFullName());
		users.setRegistrationdate(loginAuthenticationBean.getUsersFromDb().getRegistrationdate());
		users.setUserType(loginAuthenticationBean.getUsersFromDb().getUserType());
		users.setUsersId(loginAuthenticationBean.getUsersFromDb().getUsersId());
		users.setPassword(Utils.encryptSHA1(users.getPassword()));
		usersEJB.mergeEntity(users);

		log.info("Successfully update the password!!");
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successfully Update the password"));
		this.showMessage = true;

	}

	public void resetPassword() {
		Date now = Calendar.getInstance().getTime();
		try {
			Users searchedUser = usersEJB.searchUserByEmail(users.getEmail());
			if (searchedUser != null) {
				searchedUser.setPassword(Utils.encryptSHA1(users.getPassword()));
				searchedUser.setUpdatedDate(now);
				usersEJB.mergeEntity(searchedUser);
			}
		} catch (MyUserException e) {
			log.error("Reset password is failed becuase user name or email address is wrong");
			e.printStackTrace();
		}
	}

	/**
	 * Save edited user info
	 */

	public void saveUserEdit() {
		Date updateDate = Calendar.getInstance().getTime();
		if (users != null) {
			users.setUpdatedDate(updateDate);
			usersEJB.merge(users);
		}

	}

	/**
	 * 
	 * @param context
	 * @param toValidate
	 * @param value
	 * @throws MyStoreException
	 * 
	 *             check password validation
	 */

	public void validateSamePassword(FacesContext context, UIComponent toValidate, Object value)
			throws MyStoreException {
		String confirmPassword = (String) value;
		if (!confirmPassword.equals(users.getPassword())) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Passwords do not match!",
					"Passwords do not match!");
			context.addMessage(toValidate.getClientId(), message);
			log.error("Confirm Password =" + passwordConfirm + "  do not match with the first password "
					+ users.getPassword());
			throw new ValidatorException(message);
			// throw new MyStoreException("Passwords do not match!");
		}
	}

	/**
	 * 
	 * @param context
	 * @param toValidate
	 * @param value
	 * @throws MyStoreException
	 * 
	 * @check old password to the save saved password before make any password change
	 * @author Wonde
	 */
	public void validateOldPassword(FacesContext context, UIComponent toValidate, Object value)
			throws MyStoreException {
		String confirmPassword = (String) value;
		if (!confirmPassword.equals(loginAuthenticationBean.getPassword())) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Inserted Passwords do not match with old Password!", "Passwords do not match!");
			context.addMessage(toValidate.getClientId(), message);
			log.error("Confirm Password =" + passwordConfirm + "  do not match with the first password "
					+ users.getPassword());
			throw new ValidatorException(message);
			// throw new MyStoreException("Passwords do not match!");
		}
	}

	/**
	 * 
	 * @param context
	 * @param toValidate
	 * @param value
	 */
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
		showMessage = true;
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage("Successully register the user, you can login now!!"));
		RequestContext.getCurrentInstance().addCallbackParam("success", true);
		// FacesContext context = FacesContext.getCurrentInstance();
		// context.addMessage(null, new FacesMessage("Successful", "Successully register the user "));

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

	public boolean isShowPassword() {
		return showPassword;
	}

	public void setShowPassword(boolean showPassword) {
		this.showPassword = showPassword;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public boolean isShowMessage() {
		return showMessage;
	}

	public void setShowMessage(boolean showMessage) {
		this.showMessage = showMessage;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
