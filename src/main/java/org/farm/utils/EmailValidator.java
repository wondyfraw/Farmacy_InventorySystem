package org.farm.utils;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class EmailValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object toValidate) throws ValidatorException {

		boolean isValid = true;
		String value = null;

		if ((context == null) || (component == null)) {
			throw new NullPointerException();
		}
		if (!(component instanceof UIInput)) {
			return;
		}
		if (null == toValidate) {
			return;
		}
		value = toValidate.toString();
		int atIndex = value.indexOf('@');
		if (atIndex < 0) {
			isValid = false;
		} else if (value.lastIndexOf('.') < atIndex) {
			isValid = false;
		}
		/*
		 * if ( !isValid ) { MessageFactory msg = new MessageFactory(); FacesMessage errMsg = new
		 * FacesMessage(msg.getMessage("errorEmailFormat")); throw new ValidatorException(errMsg); }
		 */
	}

}
