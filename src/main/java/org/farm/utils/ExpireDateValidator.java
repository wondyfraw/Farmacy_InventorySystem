package org.farm.utils;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("expireDateValidator")
public class ExpireDateValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

		if (value == null)
			return;
		// Leave the null handling of manufacturyDate to required="true"
		Object manufacturyDateValue = component.getAttributes().get("manDate");
		if (manufacturyDateValue == null)
			return;

		Date manufactureDate = (Date) manufacturyDateValue;
		Date expireDate = (Date) value;
		if (expireDate.before(manufactureDate)) {
			String message = "ExpireDate must be greater than Manufactury date";
			FacesMessage msg = new FacesMessage(message);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(component.getClientId(), msg);
			throw new ValidatorException(msg);
		}
	}

}
