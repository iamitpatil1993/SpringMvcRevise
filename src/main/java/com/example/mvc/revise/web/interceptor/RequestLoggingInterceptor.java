package com.example.mvc.revise.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

public class RequestLoggingInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String string = "Inside RequestLoggingInterceptor#preHandle with request URI :: %s, Method :: %s";
		System.out.println(String.format(string, request.getRequestURI(), request.getMethod()));
		return true;
	}

	/**
	 * By the time this posHandle is called, spring have used {@link HttpMessageConverter#write(Object, org.springframework.http.MediaType, org.springframework.http.HttpOutputMessage)}
	 * and response is written and commited to {@link HttpServletResponse}.
	 * Hence we can not add/update anything in {@link HttpServletResponse}
	 * 
	 * To add headers or update anything in {@link HttpServletResponse} we should consider using 
	 * {@link ResponseBodyAdvice}
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		String string = "Inside RequestLoggingInterceptor#postHandle with request URI :: %s, Method :: %s";
		System.out.println(String.format(string, request.getRequestURI(), request.getMethod()));
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		String string = "Inside RequestLoggingInterceptor#afterCompletion with request URI :: %s, Method :: %s";
		System.out.println(String.format(string, request.getRequestURI(), request.getMethod()));
	}
}
