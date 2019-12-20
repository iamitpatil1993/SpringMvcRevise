package com.example.mvc.revise.web.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerRestController.class);
	
	private CustomerService customerService;
	
	// We will use this RepresentationModelAssembler to map CustomerGetDtos
	private RepresentationModelAssembler<CustomerGetDto,CustomerGetDto> modelAssembler;

	@Autowired
	public CustomerRestController(CustomerService customerService, RepresentationModelAssembler<CustomerGetDto,CustomerGetDto> modelAssembler) {
		this.customerService = customerService;
		this.modelAssembler = modelAssembler;
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

	@GetMapping(path = "/customers/{customerId:^[1-9]\\d*$}")
	@ResponseStatus(HttpStatus.OK)
	public CustomerGetDto findById(final @PathVariable Long customerId) {
		final Customer customer = customerService.findById(customerId).get();
		final CustomerGetDto customerGetDto = Util.convertUsingModelMapper(customer, CustomerGetDto.class);

		return modelAssembler.toModel(customerGetDto);
	}

	@SuppressWarnings("unused")
	private CustomerGetDto convertToHateoasBasedDto(final Long customerId, final CustomerGetDto customerGetDto) {
		/*
		// This is manual link creation, if we do not pass relation, default link type will be self
		Link selfLink = new Link(ServletUriComponentsBuilder.fromCurrentRequest().build().toString());
		System.out.println("self link is = " + selfLink);
		// EntityModel is a container of both actual data and HATEOAS links
		EntityModel<CustomerGetDto> entityModel = new EntityModel<CustomerGetDto>(customerGetDto, selfLink);
		*/
		
		// This will create templated Link
		Link templatedSelfLink = new Link(ServletUriComponentsBuilder.fromCurrentContextPath().path("/customers/{customerId}").build().toString());
		
		// spring will automatically detect template variables using brackets and this link will be templated
		Assert.isTrue(templatedSelfLink.isTemplated(), "Link should be templated, as it has template variable customerId");;
		
		// customerId will be template variable detected by spring
		Assert.isTrue(templatedSelfLink.getVariableNames().contains("customerId"), "link must detect single template variable nammed customerId");
		
		// expand link, i.e provide template variables and get expanded link
		Link expandedTemplatedSelfLink = templatedSelfLink.expand(customerId);
		
		// Assert expanded string to check all variables got replaced
		Assert.isTrue(expandedTemplatedSelfLink.getHref().equals(ServletUriComponentsBuilder.fromCurrentContextPath().path("/customers/" + customerId).build().toString()), "Template expansion failed");

		// Create and add link to customers plural resource url using LinkRelation, we can add relation as a plain string as well
		Link customersLink = new Link(ServletUriComponentsBuilder.fromCurrentContextPath().path("/customers").build().toString(), LinkRelation.of("customers"));
		
		// Build entity model from data and links
		//EntityModel<CustomerGetDto> entityModel2 = new EntityModel<CustomerGetDto>(customerGetDto, expandedTemplatedSelfLink, customersLink);
		
		customerGetDto.add(expandedTemplatedSelfLink, customersLink);
		return customerGetDto;
	}
	
	@GetMapping(path = "/customers")
	@ResponseStatus(code = HttpStatus.OK)
	public CollectionModel<CustomerGetDto> getAll() {
		LOGGER.info("Inside getAll, this should not get logged to console since there is no implementation or binding for slf4j in classpath");
		Collection<Customer> customers = customerService.findAll();

		// convert Entity -> CustomerGetDto -> CustomerGetDto populated with links
		final List<CustomerGetDto> customersGetDtosWithLinks = customers.stream()
				.map(customer -> Util.convertUsingModelMapper(customer, CustomerGetDto.class))
				.map(modelAssembler::toModel)
				.collect(Collectors.toList());

		// Create link to self i.e this collection resource
		final Link customersSelfLink = new Link(
				ServletUriComponentsBuilder.fromCurrentContextPath().path("/customers").build().toString());

		// Create collection model with DTO collection and links to collection URL
		// (this) resource
		final CollectionModel<CustomerGetDto> collectionModel = new CollectionModel<>(customersGetDtosWithLinks,
				customersSelfLink);

		return collectionModel;
	}

	/**
	 * Populates CustomerGetDto with links using manual method references
	 * 
	 * @return CustomerGetDto with links to self and customers plural resource.
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	@SuppressWarnings("unused")
	private CustomerGetDto convertToHateoasBasedDtoUsingControllerMethodRef(final Long customerId,
			final CustomerGetDto customerGetDto) {
		// create link to singular resource using method reference
		Link customerSelfLink = linkTo(methodOn(CustomerRestController.class).findById(customerId)).withSelfRel();

		// create link to customer plural resource using method reference.
		Link customersLink = linkTo(methodOn(CustomerRestController.class).getAll()).withRel(LinkRelation.of("customers"));

		// add links to DTO
		return customerGetDto.add(customerSelfLink, customersLink);
	}
}
