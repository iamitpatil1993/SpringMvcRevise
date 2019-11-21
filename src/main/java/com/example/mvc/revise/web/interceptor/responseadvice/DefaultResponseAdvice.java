package com.example.mvc.revise.web.interceptor.responseadvice;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.example.mvc.revise.dto.JsonResponse;

/**
 * This is ResponseBodyAdvice for Json/Xml converters. Will add origin reference
 * in response body and add custom headers. This advice get called before
 * {@link HttpMessageConverter#write(Object, MediaType, org.springframework.http.HttpOutputMessage)}
 * get called. Hence response is not commited yet and we can change any aspect
 * of response object. After this stage spring calls
 * {@link HttpMessageConverter#write(Object, MediaType, org.springframework.http.HttpOutputMessage)}
 * and after that response get commited and hence we can't change any part of
 * response in HandlerInterceptor or Servlet Filter.
 * 
 * @author amipatil
 *
 */
@RestControllerAdvice
public class DefaultResponseAdvice implements ResponseBodyAdvice<Object> {

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		System.out.println("Inside DefaultResponseAdvice#supports");

		/**
		 * We can have condition based on MethodParameter i.e
		 * ResponseEntity.class.isAssignableFrom(returnType.getParameterType());, but we
		 * will get method parameter type as ResponseEntity which is return type from
		 * most of controller methods. But issue is this Advice will work in most of
		 * cases but it will not work in case of REST API methods which are returning
		 * File i.e Spring resource for example ResponseEntity<Resource>. It will fail
		 * if beforeBodyWrite() has logic to update/change response object. Hence simple
		 * way to support all controller methods which returns JSON/XML is to use second
		 * argument of this method HttpMessageConverter. If it is converter configured
		 * for JSON/XML then we are sure that we will be able to alter body in
		 * beforeBodyWrite and it will not fail, also in case of File download,
		 * HttpMessageConverter will be different which will be
		 * ResourceHttpMessageConverter hence this method will return false saying this
		 * advice do not support Resource response body types.
		 * 
		 */
		return MappingJackson2HttpMessageConverter.class.isAssignableFrom(converterType);
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {
		// We can alter response here
		if (body instanceof JsonResponse) {
			JsonResponse jsonResponse = (JsonResponse) body;
			jsonResponse.setOrigin(request.getURI().toString());
		}
		// We can add/update headers here
		response.getHeaders().add("customHeader", "customHeader");
		return body;
	}

}
