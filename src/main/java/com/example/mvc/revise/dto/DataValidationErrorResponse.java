package com.example.mvc.revise.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataValidationErrorResponse extends ErrorResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5607093232668745761L;
	private List<FieldValidationError> errors = new ArrayList<>();
	
	public DataValidationErrorResponse(HttpStatus httpStatus) {
		super(httpStatus, null);
	}
	
	public DataValidationErrorResponse(HttpStatus httpStatus, final String message) {
		super(httpStatus, message);
	}
	
	public DataValidationErrorResponse(HttpStatus httpStatus, final String message, final List<FieldValidationError> fieldErrors) {
		super(httpStatus, message);
		this.errors = fieldErrors;
	}

	public ErrorResponse addError(final FieldValidationError fieldError) {
		errors.add(fieldError);
		return this;
	}
}
