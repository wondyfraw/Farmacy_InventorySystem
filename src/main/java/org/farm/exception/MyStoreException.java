package org.farm.exception;

public class MyStoreException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyStoreException() {
		super();
	}

	public MyStoreException(String drugName) {
		super(drugName);
	}

	public MyStoreException(String drugName, Throwable arg1) {
		super(drugName, arg1);
	}

	public MyStoreException(Throwable arg) {
		super(arg);
	}

}
