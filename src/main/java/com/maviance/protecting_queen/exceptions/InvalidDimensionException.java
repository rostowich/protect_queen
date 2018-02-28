package com.maviance.protecting_queen.exceptions;


public class InvalidDimensionException extends Exception {

private static final long serialVersionUID = 1L;
	
	public InvalidDimensionException() {
		super();
	}

	public InvalidDimensionException(String errorMessage) {
		super(errorMessage);
	}
}
