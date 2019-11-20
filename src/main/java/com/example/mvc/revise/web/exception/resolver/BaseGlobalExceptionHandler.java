package com.example.mvc.revise.web.exception.resolver;

import javax.servlet.http.HttpServletRequest;

public class BaseGlobalExceptionHandler {
	
	protected void logRequest(final HttpServletRequest httpServletRequest) { 
	
		String formatedString = String.format("Resource URI :: %s, METHOD :: %s", httpServletRequest.getRequestURL(), httpServletRequest.getMethod());
		System.out.println(formatedString);
	}

}
