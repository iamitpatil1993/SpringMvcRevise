package com.example.mvc.revise.web.controller;

import org.springframework.validation.Errors;

import lombok.Getter;

@Getter
public class RequestBodyValidationException extends RuntimeException {

	private static final long serialVersionUID = 3651146811329154358L;

	private Errors errors;

	public RequestBodyValidationException() {
		// Nothing to do here
	}

	public RequestBodyValidationException(final Errors errors) {
		this.errors = errors;
	}

	public RequestBodyValidationException(final String message) {
		super(message);
	}

	public RequestBodyValidationException(final String message, final Errors errors) {
		super(message);
		this.errors = errors;
	}

}
