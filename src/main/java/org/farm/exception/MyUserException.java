package org.farm.exception;

public class MyUserException extends Exception {

	private static final long serialVersionUID = -6859635123714255333L;

	public MyUserException() {
		super();
	}

	public MyUserException(String nameOrEmail) {
		super(nameOrEmail);
	}

	public MyUserException(String name, Throwable arg) {
		super(name, arg);
	}

	public MyUserException(Throwable arg) {
		super(arg);
	}
}
