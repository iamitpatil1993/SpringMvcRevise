package com.example.mvc.revise.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Order of invocation of multiple HandlerInteceptor depends on order in which
 * we have registered them to registry in web
 * {@link WebMvcConfigurer#addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry)}
 * 
 * @author amipatil
 *
 */
public class LatencyCalculatorInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("Inside LatencyCalculatorInterceptor#preHandle");
		request.setAttribute("startTime", System.currentTimeMillis());
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("Inside LatencyCalculatorInterceptor#postHandle");
		Long startTime = (Long) request.getAttribute("startTime");
		System.out.println("Time take :: " + (System.currentTimeMillis() - startTime));
	}

}
