package com.example.mvc.revise.config.web;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.example.mvc.revise.web.interceptor.AuthHandlerInterceptor;
import com.example.mvc.revise.web.interceptor.LatencyCalculatorInterceptor;
import com.example.mvc.revise.web.interceptor.RequestLoggingInterceptor;

@EnableWebMvc
@ComponentScan(basePackages = { "com.example.mvc.revise.web" })
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

	@Bean
	public ViewResolver jspViewResolver() {
		InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
		internalResourceViewResolver.setPrefix("/WEB-INF/views/");
		internalResourceViewResolver.setSuffix(".jsp");
		internalResourceViewResolver.setExposeContextBeansAsAttributes(true);
		internalResourceViewResolver.setViewClass(JstlView.class);

		return internalResourceViewResolver;
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/files/upload").setViewName("fileUpload");
	}

	/**
	 * We need to define either CommonsMultipartResolver or StandardServletMultipartResolver to enable support
	 * for Multipart requests in web MVC/REST
	 * @return
	 * @throws IOException
	 */
	@Bean
	public MultipartResolver apacheCommonsMultipartResolver() throws IOException {
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setMaxUploadSizePerFile(-1); // Default is -1 means no limit
		commonsMultipartResolver.setUploadTempDir(new FileSystemResource("/home/amipatil/files/temp"));
		commonsMultipartResolver.setMaxUploadSize(-1); // Default is -1 means no limit

		return commonsMultipartResolver;
	}

	@Bean
	public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter() {
		return new MappingJackson2HttpMessageConverter();
	}

	@Bean
	public RequestMappingHandlerAdapter mappingHandlerAdapter(final MappingJackson2HttpMessageConverter converter) {
		RequestMappingHandlerAdapter requestMappingHandlerAdapter = new RequestMappingHandlerAdapter();
		requestMappingHandlerAdapter.setMessageConverters(Arrays.asList(converter));
		return requestMappingHandlerAdapter;
	}

	/**
	 * Order of declaration of interceptors also defines there order of invocation.
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new AuthHandlerInterceptor()).addPathPatterns("/**");
		registry.addInterceptor(new LatencyCalculatorInterceptor()).addPathPatterns("/**");
		registry.addInterceptor(new RequestLoggingInterceptor()).addPathPatterns("/**");
	}
}
