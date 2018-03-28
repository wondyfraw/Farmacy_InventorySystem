package org.farm.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesValidator("nExpireDateValidator")
public class NExpireDateValidator implements Validator {

	/**
	 * Class Logger. Use this log to write error messages, debug information
	 */
	protected Log log = LogFactory.getLog(NExpireDateValidator.class);

	/**
	 * @param context
	 *            FacesContext
	 * @param component
	 *            UIComponent
	 * @param value
	 *            Object
	 */
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

		UIComponent view = context.getViewRoot();

		// complete id of the component([form_id]:[id componente]:inputText)
		String componentId = component.getClientId();
		// id of the inputText to which the validator is hooked
		String inputTextId = component.getId();
		// id del componente inputText scritto da noi ([form_id]:[id
		// componente])
		String customComponentId = componentId.substring(0, componentId.indexOf(inputTextId));
		// il componente inputText scritto da noi
		UIComponent customComponent = (UIComponent) view.findComponent(customComponentId);
		// id del componente data_da da validare insieme a data_a
		String dataDaId = (String) customComponent.getAttributes().get("manDate");
		SimpleDateFormat dateFormat = new SimpleDateFormat(ConstantsSingleton.DATE_FORMAT_BEFORE_CONVERSION);

		if (dataDaId == null) {
			log.error("Non e' stato definito l'attributo \"data_da_id\" " + "per il validatore \"TwoDatesValidator\"");
		}

		/*
		 * Ottengo i due componenti data da validare
		 */
		HtmlInputText htmlInputText = (HtmlInputText) view.findComponent(dataDaId);
		Date dataDa = null;
		String dataDaString;
		if (htmlInputText != null) {
			if (htmlInputText.isLocalValueSet()) {
				dataDa = (Date) htmlInputText.getValue();
			} else {
				Object submittedValue = htmlInputText.getSubmittedValue();
				if (submittedValue != null) {
					dataDaString = (String) submittedValue;
					try {
						dataDa = dateFormat.parse(dataDaString);
					} catch (ParseException e) {
						String message = "Formato di data errato, inserire una data nel formato gg/mm/aaaa";
						FacesMessage msg = new FacesMessage(message);
						msg.setSeverity(FacesMessage.SEVERITY_ERROR);
						throw new ValidatorException(msg);
					}
				}
			}
		}
		Date dataA = (Date) value;

		if ((dataA == null) || (dataDa == null)) {
			/*
			 * Se non sono presenti entrambe le date la validazione ha successo. Ci sono 2 casi possibili: - le date
			 * potrebbero non essere tutte obbligatorie - la chiamata al validatore viene fatta diverse volte (on blur),
			 * non in tutte le chiamate entrambe le date sono definite Nel primo caso se ne occupano altri validatori,
			 * nel secondo caso bisogna evitare le chiamate in piu'.
			 */
			return;
		}

		// Controllo di validazione, dataDa deve essere precedente a dataA
		if (dataA.before(dataDa)) {
			String message = "La data fine \"Al\" " + "non può essere antecedente alla data inizio \"Dal\"";
			FacesMessage msg = new FacesMessage(message);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}

		// dataDa non puo' essere la stessa di dataA
		if (dataA.equals(dataDa)) {
			String message = "La data di fine periodo \"Al\" "
					+ "e di inizio periodo \"Dal\" non possono essere la stessa";
			FacesMessage msg = new FacesMessage(message);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}

		return;
	}

}
