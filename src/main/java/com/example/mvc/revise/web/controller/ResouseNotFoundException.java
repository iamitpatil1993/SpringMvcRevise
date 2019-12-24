package com.example.mvc.revise.web.controller;

public class ResouseNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResouseNotFoundException() {
	}

	public ResouseNotFoundException(final String message) {
		super(message);
	}
}
