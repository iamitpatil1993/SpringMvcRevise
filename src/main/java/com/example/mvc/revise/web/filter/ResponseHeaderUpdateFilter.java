package com.example.mvc.revise.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.ContentCachingResponseWrapper;

/**
 * Spring by default do not writes content-lenght header to response, so we need
 * to handle this. This request filter is responsible for writing content-length
 * to response. It uses special class {@link ContentCachingResponseWrapper} for
 * that purpose. {@link ContentCachingResponseWrapper} is useful class and can
 * be used for many other purposes where actual response object needs to be
 * wrapped.
 * 
 * NOTE: If gzip-compression is enabled at server level in server.xml, then even
 * after this class, we don't get content-length header in response because,
 * here we set uncompressed body length, but body lenght changes after server
 * applies compression to response and it's responsibility to set content-length
 * header again. I have applied this filter to DispatcherServlet because
 * dispatcherServlet is front-controller and handles all request to application.
 * 
 * @author amipatil
 *
 */
public class ResponseHeaderUpdateFilter implements Filter {

	private static final Logger LOGGER = LoggerFactory.getLogger(ResponseHeaderUpdateFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		LOGGER.info("Initializing ResponseHeaderUpdateFilter");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		LOGGER.info("Inside ResponseHeaderUpdateFilter");

		// This class wraps actual HttpServletRequest and cache any data written to it
		// to itself.
		// So, create wrapper
		ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(
				(HttpServletResponse) response);

		// forward request and pass wrapper instead of actual object, so that controller
		// writes to wrapper and not actual response object
		chain.doFilter(request, responseWrapper);

		// now, copy cached response in ContentCachingResponseWrapper to actual response
		// object. And special thing is, this
		// also sets content-lenght header automatically.
		responseWrapper.copyBodyToResponse();
	}

	@Override
	public void destroy() {
		LOGGER.info("Cleaning ResponseHeaderUpdateFilter");
	}

}
