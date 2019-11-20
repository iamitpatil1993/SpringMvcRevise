package com.example.mvc.revise.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.util.StopWatch;

public class LatencyLoggerFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("LatencyLoggerFilter.init");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		final StopWatch stopWatch = new StopWatch();

		System.out.println("LatencyLoggerFilter.doFilter");
		stopWatch.start();
		chain.doFilter(request, response);
		stopWatch.stop();

		System.out.println("stopWatch.getLastTaskTimeMillis() = " + stopWatch.getLastTaskTimeMillis());
	}

	@Override
	public void destroy() {
		System.out.println("LatencyLoggerFilter.destroy");
	}
}
