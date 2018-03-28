package org.farm.convertor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.farm.fms.entity.ejb.StoreEJB;

@FacesConverter("packTypeConvertor")
public class PackTypeConvertor implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value.trim().equals(""))
			return null;
		else {
			InitialContext ic;
			StoreEJB storeEJB = null;

			try {
				ic = new InitialContext();
				storeEJB = (StoreEJB) ic.lookup("java:module/StoreEJB");

			} catch (NamingException e) {

				return null;
			}
			return value;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null || value.equals(""))
			return null;
		else
			return String.valueOf(value);
	}

}
