package com.example.mvc.revise.web.controller;

public class SpittleNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4269076756968865435L;

	public SpittleNotFoundException() {
		super();
	}

	public SpittleNotFoundException(final String message) {
		super(message);
	}
}
