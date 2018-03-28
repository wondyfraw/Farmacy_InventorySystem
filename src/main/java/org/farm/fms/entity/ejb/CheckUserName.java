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

@FacesValidator(value = "checkUserName")
public class CheckUserName implements Validator {

	private static final Log log = LogFactory.getLog(CheckUserName.class);

	private UsersEJB usersEJB;

	public CheckUserName() {
		try {
			InitialContext ic = new InitialContext();
			usersEJB = (UsersEJB) ic.lookup("java:module/UsersEJB");
		} catch (NamingException e) {

			log.error(e);
		}

	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

		if (value != null) {
			String username = value.toString();
			if (username.length() < 3) {
				String message = "The Username must have at least 3 characters";
				FacesMessage msg = new FacesMessage(message);
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				context.addMessage(component.getClientId(), msg);
				throw new ValidatorException(msg);
			}
		}
	}

}
