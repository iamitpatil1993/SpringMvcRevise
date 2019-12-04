package com.example.mvc.revise.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import com.example.mvc.revise.config.support.DTO;
import com.example.mvc.revise.config.support.RequestBodyToEntityProcessor;
import com.example.mvc.revise.dto.CustomerGetDto;
import com.example.mvc.revise.dto.CustomerPostDto;
import com.example.mvc.revise.dto.JsonResponse;
import com.example.mvc.revise.model.Customer;
import com.example.mvc.revise.service.CustomerService;
import com.example.mvc.revise.util.Util;

@RestController
public class CustomerRestController {

	private CustomerService customerService;

	@Autowired
	public CustomerRestController(CustomerService customerService) {
		this.customerService = customerService;
	}

	/**
	 * This is how we will annotate body parameter type with {@link DTO} and out
	 * custom {@link HandlerMethodArgumentResolver} implementation
	 * {@link RequestBodyToEntityProcessor} will get used
	 * 
	 * @param customer Entity type tow which we want to convert incoming request DTO
	 * @return {@link JsonResponse} containing {@link CustomerGetDto}
	 */
	@PostMapping(path = "/customers")
	@ResponseStatus(code = HttpStatus.CREATED)
	public JsonResponse post(@DTO(type = CustomerPostDto.class) Customer customer) {
		Customer savedCustomer = customerService.createCustomer(customer);
		CustomerGetDto customerGetDto = Util.convertUsingModelMapper(savedCustomer, CustomerGetDto.class);

		return new JsonResponse().setData(customerGetDto).setHttpStatus(HttpStatus.CREATED).setMessage("Created");
	}

	@PutMapping(path = "/customers/{customerId:^[1-9]\\d*$}")
	@ResponseStatus(code = HttpStatus.OK)
	public JsonResponse update(@DTO(type = CustomerPostDto.class) Customer customer,
			final @PathVariable Long customerId) {
		customer.setId(customerId);
		Customer updatedCustomer = customerService.updateCustomer(customer);
		CustomerGetDto customerGetDto = Util.convertUsingModelMapper(updatedCustomer, CustomerGetDto.class);

		return new JsonResponse().setData(customerGetDto).setHttpStatus(HttpStatus.OK).setMessage("Updated");
	}

}
