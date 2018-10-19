package org.farm.fms.entity.ejb;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.farm.exception.MyUserException;

@FacesValidator(value = "checkUserNameExist")
public class CheckUserNameExist implements Validator {

	private static final Log log = LogFactory.getLog(CheckUserNameExist.class);

	private UsersEJB userEJB;

	public CheckUserNameExist() {
		try {
			InitialContext ic = new InitialContext();
			userEJB = (UsersEJB) ic.lookup("java:module/UsersEJB");

		} catch (NamingException e) {
			log.error(e);
		}

	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

		if (value != null) {
			String username = value.toString();
			try {
				if (userEJB.notExist(username)) {
					String message = "Email address does not exist";
					FacesMessage msg = new FacesMessage(message);
					msg.setSeverity(FacesMessage.SEVERITY_ERROR);
					context.addMessage(component.getClientId(), msg);
					throw new ValidatorException(msg);
				}
			} catch (MyUserException e) {
				log.error(e);
			}
		}
	}

}
