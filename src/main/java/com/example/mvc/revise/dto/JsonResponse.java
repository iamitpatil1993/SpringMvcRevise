package com.example.mvc.revise.dto;

import org.springframework.http.HttpStatus;

public class JsonResponse {
	
	private HttpStatus httpStatus;
	
	private String message;
	
	private Object data;
	
	private String origin;

	public String getMessage() {
		return message;
	}

	public JsonResponse setMessage(String message) {
		this.message = message;
		return this;
	}

	public Object getData() {
		return data;
	}

	public JsonResponse setData(Object data) {
		this.data = data;
		return this;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public JsonResponse setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
		return this;
	}

	public String getOrigin() {
		return origin;
	}

	public JsonResponse setOrigin(String origin) {
		this.origin = origin;
		return this;
	}

	

}
