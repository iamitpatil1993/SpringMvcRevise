package com.example.mvc.revise.config.web;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.example.mvc.revise.config.RootConfiguration;
import com.example.mvc.revise.web.filter.LatencyLoggerFilter;
import com.example.mvc.revise.web.filter.RequestLoggerServletFilter;
import com.example.mvc.revise.web.filter.ResponseHeaderUpdateFilter;

/**
 * Three things that we need to configure are RootConfig class, WEebConfig class
 * and path on which Dispatcher servlet will accept requests, apart from that we
 * can configure many other details using this class.
 *
 */
public class DispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { RootConfiguration.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { WebConfiguration.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected Filter[] getServletFilters() {
		return new Filter[] { new LatencyLoggerFilter(), new RequestLoggerServletFilter(), new ResponseHeaderUpdateFilter()};
	}

	/**
	 * In order to user MultipartFile as a controller parameter we need to define
	 * multipart config here. If we are using javax Part and not MultipartFile then
	 * we do not need this configuration.
	 */
	@Override
	protected void customizeRegistration(Dynamic registration) {
		final MultipartConfigElement configElement = new MultipartConfigElement("/home/amipatil/files/temp");
		registration.setMultipartConfig(configElement);
		
		// we need to set this init parameter to ask spring not to handle handlerMapping case by sending 404, instead
		// create and send NoHandlerFoundException, which our custom @ExceptionHandler class can handle gracefully
		// see https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-container-config
		registration.setInitParameter("throwExceptionIfNoHandlerFound", "true");
	}
}
