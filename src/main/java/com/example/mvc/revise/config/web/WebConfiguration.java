package com.example.mvc.revise.config.web;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
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
	
	/**
	 * Order of declaration of interceptors also defines there order of invocation.
	 * Actually their default oder is zero unless specified. Hence all interceptors
	 * has same order of HIGHEST, hence spring will invoke them based on their order
	 * of registration here. We can change their order using order() function. NOTE:
	 * If we do not define order using order() it will have 0 order hence will get
	 * called first.
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// using Order() we can change order of their invocation, hence order of
		// registration here will not matter any more
		registry.addInterceptor(new AuthHandlerInterceptor()).addPathPatterns("/**").order(2);
		registry.addInterceptor(new LatencyCalculatorInterceptor()).addPathPatterns("/**").order(1);
		registry.addInterceptor(new RequestLoggingInterceptor()).addPathPatterns("/**"); // Default order 0
	}
	
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.defaultContentType(MediaType.APPLICATION_JSON);
		configurer.favorPathExtension(false);
	}
}
