package com.example.mvc.revise.web.exception.resolver;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.mvc.revise.dto.DataValidationErrorResponse;
import com.example.mvc.revise.dto.FieldValidationError;
import com.example.mvc.revise.dto.JsonResponse;
import com.example.mvc.revise.web.controller.RequestBodyValidationException;
import com.example.mvc.revise.web.controller.ResouseNotFoundException;

@RestControllerAdvice // This is @ControllerAdvice + @ResponseBody, so we do not need to use
// @ResponseBody on exception handler methods.
@Order(value = 1)
public class RestGlobalExceptionHandler {

	/**
	 * Exception handler can be implemented in exactly similar way as a controller
	 * method. NOTE: We do not need to add @ResponseBody here
	 */
	@ExceptionHandler(value = ResouseNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public JsonResponse resoureNotFoundExceptionHandler(ResouseNotFoundException exception) {
		return new JsonResponse().setHttpStatus(HttpStatus.NOT_FOUND).setMessage(exception.getMessage());
	}

	@ExceptionHandler(RequestBodyValidationException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ResponseEntity<DataValidationErrorResponse> requestBodyValidationExceptionHandler(
			final RequestBodyValidationException exception) {
		final BodyBuilder responseBuilder = ResponseEntity.badRequest();

		final DataValidationErrorResponse dataValidationErrorResponse = new DataValidationErrorResponse(
				HttpStatus.BAD_REQUEST, "Request body validation failed");

		if (exception.getMessage() != null && !exception.getMessage().isEmpty()) {
			dataValidationErrorResponse.setMessage(exception.getMessage());
		}

		if (exception.getErrors() != null && exception.getErrors().hasErrors()) {
			final List<FieldValidationError> fieldErrors = exception.getErrors().getFieldErrors().stream()
					.map(fieldError -> new FieldValidationError(fieldError.getObjectName(), fieldError.getField(),
							fieldError.getDefaultMessage()))
					.collect(Collectors.toList());
			dataValidationErrorResponse.setErrors(fieldErrors);
		}
		return responseBuilder.body(dataValidationErrorResponse);
	}
}
