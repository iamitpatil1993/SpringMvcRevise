package com.example.mvc.revise.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

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
