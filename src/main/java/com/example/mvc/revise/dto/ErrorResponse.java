package com.example.mvc.revise.dto;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3194982280425721059L;

	private HttpStatus httpStatus;

	private String message;
}
