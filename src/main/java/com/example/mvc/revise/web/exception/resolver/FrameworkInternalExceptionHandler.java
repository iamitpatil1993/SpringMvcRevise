package com.example.mvc.revise.web.exception.resolver;

import org.springframework.beans.TypeMismatchException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.mvc.revise.dto.JsonResponse;

/**
 * This central ExceptionHandler class is to handle all spring's internal
 * exceptions occurred while controller's handler method mapping. Important
 * point here is {@link ResponseEntityExceptionHandler} class, this is
 * convinient base class for ExceltionHandler classes annotated
 * with @ControlerAdviec and provides way to provide custom handling for all
 * spring's internal exceptions (related to Web, Data binding). So, we do not
 * need to explicitly write Exception handler Internal exceptions separately all
 * will be here. Best thing is, {@link ResponseEntityExceptionHandler} will help
 * us to write handling for all possible exceptions, so there are very few
 * changes of missing particular Exception's Exceptionhandler
 * 
 * ExceptionHandler method becomes more clearer due to @RestControllerAdvice and
 * no need to annotate them with @ExceptionHandler and
 * @ResponseStatus, @ReponseBody
 * 
 * @author amipatil
 *
 */
@RestControllerAdvice
@Order(value = 1) // we should keep it's order high so that other Generic exception handler not
					// get executed before evaluating this.
public class FrameworkInternalExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * We get all relevant information for custom error handling.
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return handleException(ex, headers, status);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return handleException(ex, headers, status);
	}

	private ResponseEntity<Object> handleException(Exception ex, HttpHeaders headers, HttpStatus status) {
		return ResponseEntity.status(status).headers(headers)
				.body(new JsonResponse().setHttpStatus(status).setMessage(ex.getMessage()));
	}

	/**
	 * Similarly we can handle all exceptions here by overriding methods from base.
	 */
	
	
	/**
	 * Handle NoHandlerFoundException
	 */
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		return ResponseEntity.status(status).headers(headers)
				.body(new JsonResponse().setHttpStatus(status).setMessage(ex.getMessage()));
	}
	
	/**
	 * {@link TypeMismatchException} is thrown when spring fails to parse or there is parsing error in path variables.
	 * For example, path variable is declared as a integer but user sends non numeric value for that path variable.
	 */
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		return handleException(ex, headers, status);
	}
}
