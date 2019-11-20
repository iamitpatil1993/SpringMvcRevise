package com.example.mvc.revise.web.controller;

public class SpittleAlreadyExitstException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6438404434327974406L;

	public SpittleAlreadyExitstException(final String message) {
		super(message);
	}
	
	public SpittleAlreadyExitstException() {
	}
}
