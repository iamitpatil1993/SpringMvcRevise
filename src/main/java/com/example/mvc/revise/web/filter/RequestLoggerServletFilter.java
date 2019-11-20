package com.example.mvc.revise.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestLoggerServletFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("RequestLoggerServletFilter.init");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		final HttpServletRequest httpRequest = (HttpServletRequest) request;

		System.out.println("Inside doFilter, for url ::" + httpRequest.getRequestURL());

		HttpServletResponse httpResp = (HttpServletResponse) response;
		httpResp.addHeader("customHeader", "customHeaderValue");

		chain.doFilter(request, response);

		System.out.println("Back Inside doFilter for url :: " + httpRequest.getRequestURL());
	}

	@Override
	public void destroy() {
		System.out.println("RequestLoggerServletFilter.destroy");
	}
}
