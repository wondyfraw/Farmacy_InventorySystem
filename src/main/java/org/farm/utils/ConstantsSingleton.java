package org.farm.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ConstantsSingleton {

	public static final Log log = LogFactory.getLog(ConstantsSingleton.class);

	public static final String DATE_FORMAT_AFTER_CONVERSION = "EEE MMM dd HH:mm:ss zzz yyyy";

	/**
	 *
	 * Define date formate in primefaces
	 */
	public static final String DATE_FORMAT_BEFORE_CONVERSION = "dd/MM/yyyy";

	public static Map<String, String> strip(String value) {
		Map<String, String> unit = new HashMap<String, String>();
		if (value.equals("Strip")) {
			unit.put("Tablet", "Tablet");
			unit.put("Capsule", "Capsule");
			unit.put("Ampule", "Ampule");
		} else if (value.equals("Cup")) {
			unit.put("Tablet", "Tablet");
			unit.put("Capsule", "Capsule");
		} else if (value.equals("Piece")) {
			unit.put("Glove", "Glove");
			unit.put("Tooth soap", "Tooth soap");
			unit.put("Condom", "Condom");
			unit.put("Cream", "Cream");
			unit.put("IV Fload", "IV Fload");
			unit.put("IV Set", "IV Set");
			unit.put("Injection needle", "Injection needle");
			unit.put("Butterfly needle", "Butterfly needle");
			unit.put("Powder", "Powder");
			unit.put("Modis", "Modis");
			unit.put("Baby diaper", "Baby diaper");
			unit.put("Tooth Brach", "Tooth Brash");
		}
		return unit;
	}

	public static List<String> packUnit() {
		List<String> packUnits = new ArrayList<String>();
		packUnits.add("Tablet");
		packUnits.add("Capsule");
		packUnits.add("Ampule");
		packUnits.add("Glove");
		packUnits.add("Tooth soap");
		packUnits.add("Condom");
		packUnits.add("Capsule");
		packUnits.add("Cream");
		packUnits.add("IV Fload");
		packUnits.add("IV Set");
		packUnits.add("Injection needle");
		packUnits.add("Butterfly needle");
		packUnits.add("Powder");
		packUnits.add("Modis");
		packUnits.add("Baby diaper");
		packUnits.add("Tooth Brach");
		return packUnits;
	}

	public static List<String> packType() {
		List<String> packUnit = new ArrayList<String>();
		packUnit.add("Strip");
		packUnit.add("Piece");
		packUnit.add("Cup");
		return packUnit;
	}
}
