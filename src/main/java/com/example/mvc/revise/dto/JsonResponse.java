package com.example.mvc.revise.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.springframework.http.HttpStatus;

@XmlRootElement // Need this to set this class as a root of XML
//Without this you will get exception, see https://stackoverflow.com/questions/14057932/javax-xml-bind-jaxbexception-class-nor-any-of-its-super-class-is-known-to-t
// also see https://stackoverflow.com/questions/12288631/xmlseealso-and-xmlrootelement-names
@XmlSeeAlso({Employee.class}) 
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
