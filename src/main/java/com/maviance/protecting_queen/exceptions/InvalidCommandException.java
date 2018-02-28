package com.maviance.protecting_queen.exceptions;


public class InvalidCommandException extends Exception {

private static final long serialVersionUID = 1L;

	public InvalidCommandException() {
		super();
	}

	public InvalidCommandException(String errorMessage) {
		super(errorMessage);
	}
}
