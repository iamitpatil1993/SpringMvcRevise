package com.example.mvc.revise.config.support;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

/**
 * This is custom implementation of {@link HandlerMethodArgumentResolver} which
 * will handle conversion of request body to Entity type if request body
 * parameter is annotated with {@link DTO}
 * 
 * Spring has different strategies i.e implementations of
 * {@link HandlerMethodArgumentResolver} and spring uses one of all existing
 * strategy implementations based on
 * {@link HandlerMethodArgumentResolver#supportsParameter(MethodParameter)}. So,
 * here we are checking that if parameter is annotated with {@link DTO} then
 * spring will use this strategy to parse body into DTO and then converting DTO
 * in Entity type using {@link ModelMapper}
 * 
 * @author amipatil
 *
 */
public class RequestBodyToEntityProcessor extends RequestResponseBodyMethodProcessor {

	private ModelMapper modelMapper;

	/**
	 * In order to parse request body and covert it into appropriate DTO before
	 * converting into Entity, we need to provide super class with list of
	 * {@link HttpMessageConverter}, which it will use to parse the body.
	 * 
	 * @param httpMessageConverters
	 * @param mapper
	 */
	public RequestBodyToEntityProcessor(final List<HttpMessageConverter<?>> httpMessageConverters,
			final ModelMapper mapper) {
		super(httpMessageConverters); // <- Important
		this.modelMapper = mapper;
	}

	/**
	 * This method decides whether this implementation of
	 * {@link HandlerMethodArgumentResolver} should be used by spring or not. If
	 * method parameter is annotated with DTO this this strategy will be used
	 */
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(DTO.class);
	}

	/**
	 * Once request body is parsed and DTO is buit from body using
	 * readWithMessageConverters, this method convert generated DTO into Entity type
	 * (parameter type) using {@link ModelMapper}
	 */
	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		Object argument = super.resolveArgument(parameter, mavContainer, webRequest, binderFactory);

		return modelMapper.map(argument, parameter.getParameterType());
	}

	/**
	 * This method actually parse request body and convert into DTO type. Hence we
	 * are taking request body type from {@link DTO#type()}
	 */
	@Override
	protected <T> Object readWithMessageConverters(HttpInputMessage inputMessage, MethodParameter parameter,
			Type targetType) throws IOException, HttpMediaTypeNotSupportedException, HttpMessageNotReadableException {
		DTO dtoAnnotationType = parameter.getParameterAnnotation(DTO.class);
		Class<?> requestBodyDtoTypeClass = dtoAnnotationType.type();
		if (dtoAnnotationType != null && requestBodyDtoTypeClass != null) {
			return super.readWithMessageConverters(inputMessage, parameter, requestBodyDtoTypeClass);
		}
		throw new RuntimeException("Could not find request body parameter type while parsing message body");
	}

}
