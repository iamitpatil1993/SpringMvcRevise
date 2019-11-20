package com.example.mvc.revise.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * we can declare these HandlerInterceptro to be spring bean if we want to
 * inject other beans here and do some more powerful things, or we can just
 * instantiate them in web config class before registering them.
 * 
 * @author amipatil
 *
 */
public class AuthHandlerInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		System.out.println("Inside AuthHandlerInterceptor#preHandler");

		/**
		 * This is how we can send response from Interceptor without forwarding request
		 * to next interceptor or controller. We just need to set reponse on our own to
		 * HttpServletResponse object and need to return false to signal spring web
		 * container that we have handled response here // and do not wish to forward
		 * request ahead in chain.
		 * 
		 */
		/*
		 * String authHeader = request.getHeader("Authorization"); if (authHeader ==
		 * null) { response.setStatus(403); return false; }
		 */
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
		System.out.println("Inside AuthHandlerInterceptor#postHandler");
	}

}
