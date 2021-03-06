package com.example.mvc.revise.config.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.hateoas.server.mvc.TypeConstrainedMappingJackson2HttpMessageConverter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.example.mvc.revise.config.support.RequestBodyToEntityProcessor;
import com.example.mvc.revise.web.interceptor.AuthHandlerInterceptor;
import com.example.mvc.revise.web.interceptor.LatencyCalculatorInterceptor;
import com.example.mvc.revise.web.interceptor.RequestLoggingInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;

@EnableWebMvc
@ComponentScan(basePackages = { "com.example.mvc.revise.web", "com.example.mvc.revise.dto.assembler", "com.example.mvc.revise.dto.processor" })
@Configuration
//We need to enable HAL based HATEOAS
// This enables TypeConstrainedMappingJackson2HttpMessageConverter and hence if Accept header is 'application/hal+json' this MessageConveter is get used over 
// MappingJackson2HttpMessageConverter to convert response.
// This configuration allows us to change response structure (based in HypermediaType standard) in single line change
@EnableHypermediaSupport(type = {HypermediaType.HAL})
@PropertySource(value = {"classpath:app.properties"})
public class WebConfiguration implements WebMvcConfigurer {

	private List<HttpMessageConverter<?>> messageConvertersForBodyProcessor = new ArrayList<>(2);
	
	@Autowired
	private ModelMapper modelMapper;
	
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
		// We can ask spring to throw NoHandlerFoundException if no handler found by request using HandlerMapping.
		// In that case spring will not send http 404 instead, will throw NoHandlerFoundException exception and 
		// we can handle this exception as we want using @ExceptionHandler
		// but in order to above work we need to disable default servlet that handles static resource serving.
		// this makes sense because, spring checks if we have handler mapping if not then it checks static resource for which 
		// it delegates to default servlet in container, which then checks that no static resource found by given url then
		// it sends 404 response. So, to avoid this we need to disable default servlet, which will disable static resources serving
		// in application, so we should use this solution to handle handler mapping not found only when we have pure REST project
		// and do not need default servlet to handle static resources, otherwise we need to check alternative solution for this.
		// refer https://stackoverflow.com/questions/34935785/setting-throwexceptionifnohandlerfound-has-no-effect-in-spring-4-2
		// refer search for throwExceptionIfNoHandlerFound in https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-container-config
		
		
		//configurer.enable();
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
	
	/**
	 * This allows us to configure content-negotiation not only in case of ContentNegotiatingViewResolver way but also
	 * HttpMessageConverter way also.
	 */
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		// this enables sending mime type required in query parameter 'format', NOTE: we need to send file extension as a value to 
		// query parameter for example, json, xml are acceptable values.
		configurer.favorParameter(true);
		
		// Set JSON as a default HttpMessageConverter to be used
		configurer.defaultContentType(MediaType.APPLICATION_JSON);
		
		configurer.ignoreUnknownPathExtensions(false);
		
		configurer.favorPathExtension(false);
	}
	
	/**
	 * This method replaces already/default registered message converters with list
	 * provided by us. So, beware and you should consider consequences of this.
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		// Since we have not added anything to list, all existing/default converter will
		// remain as it is.
		// if we add anything to converters list, existing list will get compltetely
		// replaced by what we add.
		// Nothing to add other than one already registered
	}
	
	/**
	 * Used to add HttpMessageConverters to existing list without replacing list.
	 */
	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		// Add new converters here on top of existing one without replacing existing
		// HttpMessageConverter

		// Updating MappingJackson2HttpMessageConverter to use custom ObjectMapper with
		// Dateformat, which will be application to entire
		// application while serialization and deserialization. No need to use
		// @JsonFormat in pojo to define date format, unless want to override.
		// This way we can customize any existing HttpMessageConverter
		// I did not received TypeConstrainedMappingJackson2HttpMessageConverter in this
		// converters list (don't know why), so could not customize it's ObjectMapper here by just
		// changing filter
		converters.stream().filter(converter -> (converter instanceof MappingJackson2HttpMessageConverter) || converter instanceof TypeConstrainedMappingJackson2HttpMessageConverter)
		.forEach(converter -> {
			MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = (MappingJackson2HttpMessageConverter) converter;
			jackson2HttpMessageConverter.setObjectMapper(objectMapper());
		});
		
		// collect message converters for custom HandlerMethodArgumentResolver (RequestBodyToEntityProcessor) 
		converters.stream().forEach(messageConvertersForBodyProcessor::add);
	}
	
	/**
	 * Add custom {@link HandlerMethodArgumentResolver}, so that spring use this as
	 * a one of strategy implementation for {@link HandlerMethodArgumentResolver}.
	 */
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		final RequestBodyToEntityProcessor bodyToEntityProcessor = new RequestBodyToEntityProcessor(
				messageConvertersForBodyProcessor, modelMapper);
		resolvers.add(bodyToEntityProcessor);
	}
	
	/**
	 * This is how we can configure global CORS policy which will be applicable to entire application.
	 * Any fine grained configuration at controller method level will add  up to this global configuration, except single 
	 * value configuration values like maxAge
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("http://localhost:8088").allowedHeaders("customHeader");
	}
	
	/**
	 * Declare customized {@link ObjectMapper} in WebApplicationContext so that this
	 * get picked up while creating beans of type Jackon message converter. Best
	 * part is {@link TypeConstrainedMappingJackson2HttpMessageConverter} picks this
	 * up, but {@link MappingJackson2HttpMessageConverter} does not.
	 * {@link MappingJackson2HttpMessageConverter} is created using default
	 * constructor by some internal code, so this object mapper not get passed to
	 * it.
	 * 
	 * Hence this will work for
	 * {@link TypeConstrainedMappingJackson2HttpMessageConverter} but not for
	 * {@link MappingJackson2HttpMessageConverter} therefore we need to keep above
	 * customization code (overriden extendMessageConverters()) as it for
	 * {@link MappingJackson2HttpMessageConverter}
	 * 
	 * @return
	 */
	@Bean
	public ObjectMapper objectMapper() {
		// we can avoid creating ObjectMapper like this, and use
		// Jackson2ObjectMapperBuilder for simple building
		/*
		 * ObjectMapper mapper = new ObjectMapper();
		 * mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		 * mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true);
		 */

		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
		builder.indentOutput(true).dateFormat(new SimpleDateFormat("yyyy-MM-dd")).failOnUnknownProperties(false);

		return builder.build();
	}

}
