package org.farm.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.Formatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.tidy.Tidy;

public class Utils {
	public Utils() {

	}

	/**
	 * MD5 password hashing algorithms
	 * 
	 * @param message
	 * @return
	 */
	public static String encryptMD5(String message) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(message.getBytes());
			return String.format("%032x", new BigInteger(1, digest.digest()));
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * SHA1 password hashing algorithms
	 * 
	 * @param message
	 * @return
	 */
	public static String encryptSHA1(String message) {
		Formatter formatter = new Formatter();
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA1");
			digest.update(message.getBytes());

			for (byte b : digest.digest()) {
				formatter.format("%02x", b);
			}
			String encryptedString = formatter.toString();
			return encryptedString;
		} catch (Exception e) {
			return null;
		} finally {
			formatter.close();
		}
	}

	public static boolean compareSHA1(String plainMessage, String encryptedMessage) {
		String encryptedPlainMessage = encryptSHA1(plainMessage);

		if (encryptedPlainMessage != null && encryptedPlainMessage.equals(encryptedMessage))
			return encryptedPlainMessage.equals(encryptedMessage);
		else
			return false;
	}

	public static boolean compareMD5(String plainMessage, String encryptedMessage) {
		String encryptedPlainMessage = encryptMD5(plainMessage);
		if (encryptedPlainMessage != null && encryptedPlainMessage.equals(encryptedMessage))
			return encryptedPlainMessage.equals(encryptedMessage);
		else
			return false;
	}

	public static boolean isMailCorretta(String email) {

		Pattern p = Pattern.compile(
				"([A-Za-z0-9!#-'\\*\\+\\-/=\\?\\^_`\\{-~]+(\\.[A-Za-z0-9!#-'\\*\\+\\-/=\\?\\^_`\\{-~]+)*@[A-Za-z0-9!#-'\\*\\+\\-/=\\?\\^_`\\{-~]+(\\.[A-Za-z0-9!#-'\\*\\+\\-/=\\?\\^_`\\{-~]+)*)?");
		Matcher m = p.matcher(email);
		boolean matchFound = m.matches();
		if (!matchFound) {
			return false;
		}

		return true;
	}

	public static String htmltoXHTML(String html) {
		InputStream htmlIS = new ByteArrayInputStream(html.getBytes(Charset.forName("UTF-8")));
		ByteArrayOutputStream htmlOutput = new ByteArrayOutputStream();
		Tidy tidy = new Tidy(); // HTML parser and pretty printer.
		tidy.setXHTML(true); // true if tidy should output XHTML
		tidy.setInputEncoding("UTF-8");
		tidy.setOutputEncoding("UTF-8");
		tidy.setQuiet(true);
		tidy.setShowErrors(0);
		tidy.setShowWarnings(false);
		tidy.setPrintBodyOnly(true);
		tidy.parse(htmlIS, htmlOutput);

		return new String(htmlOutput.toByteArray(), Charset.forName("UTF-8"));
	}
}
