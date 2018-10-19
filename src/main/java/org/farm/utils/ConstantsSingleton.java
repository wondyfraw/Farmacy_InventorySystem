package org.farm.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ConstantsSingleton {

	public static final Log log = LogFactory.getLog(ConstantsSingleton.class);

	public static final String DATE_FORMAT_AFTER_CONVERSION = "EEE MMM dd HH:mm:ss zzz yyyy";

	public static final String BASE_DOMIAN = "http://localhost:20000/";
	public static final String CONTENT_APP = "Test";
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

	public static List<String> boxPackElement(String pack) {
		List<String> boxPackContant = new ArrayList<String>();
		if (pack != null) {
			if (pack.equals("Box")) {
				boxPackContant.add("Strip");
				boxPackContant.add("Piece");
				boxPackContant.add("Cup");
				boxPackContant.add("Bottle");
			}
			if (pack.equals("Cup")) {
				boxPackContant.add("Cup");
			}

			if (pack.equals("Packed")) {
				boxPackContant.add("Piece");
			}
		}
		return boxPackContant;
	}

	public static List<String> drugPackType(String drugPack) {
		List<String> drugPackList = new ArrayList<String>();
		if (drugPack != null) {
			if (drugPack.equals("Strip")) {
				drugPackList.add("Tablet");
				drugPackList.add("Capsule");
				drugPackList.add("Ampule");
			}
			if (drugPack.equals("Cup")) {
				drugPackList.add("Tablet");
				drugPackList.add("Capsule");
			}
			if (drugPack.equals("Piece")) {
				drugPackList.add("Glove");
				drugPackList.add("Tooth soap");
				drugPackList.add("Condom");
				drugPackList.add("Cream");
				drugPackList.add("IV Flood");
				drugPackList.add("IV Set");
				drugPackList.add("Injection needle");
				drugPackList.add("Betterfly needle");
				drugPackList.add("Powder");
				drugPackList.add("Modis");
				drugPackList.add("Baby diaper");
				drugPackList.add("Tooth Brash");
				drugPackList.add("Body lotion");
			}
			if (drugPack.equals("Bottle")) {
				drugPackList.add("Sirup");
				drugPackList.add("Eye drop");
			}
		}
		return drugPackList;
	}

	public static Date getDateRage(int days) {
		long today = System.currentTimeMillis();
		long nDays = days * 24 * 60 * 60 * 1000;
		long nDaysAgo = today - nDays;
		Date nDaysAgoDate = new Date(nDaysAgo);
		return nDaysAgoDate;
	}
}
