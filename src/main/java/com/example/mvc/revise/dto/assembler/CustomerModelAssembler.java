package com.example.mvc.revise.dto.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.mvc.revise.dto.CustomerGetDto;
import com.example.mvc.revise.web.controller.CustomerRestController;

/**
 * This class takes the responsibility of creating RepresentationModel from
 * DTO/entities. This helps us to centralize this logic so that we can reuse
 * same assembler to build HATEOAS representation of particular resource in
 * different resources.
 * 
 * This will help us to add one link relation here, and this will reflect in all
 * API responses which uses this {@link RepresentationModelAssembler} Also, if
 * some link changes, we can change here at single place and will reflect in all
 * APIs.
 * 
 * NOTE: These model mappers are need not to be spring beans and used as spring
 * beans, we can use them as as simple classes as well.
 * 
 * @author amipatil
 *
 */
@Component
public class CustomerModelAssembler implements RepresentationModelAssembler<CustomerGetDto, CustomerGetDto> {

	@Override
	public CustomerGetDto toModel(CustomerGetDto entity) {
		final Long customerId = entity.getId();

		// create link to singular resource using method reference
		final Link customerSelfLink = linkTo(methodOn(CustomerRestController.class).findById(customerId)).withSelfRel();

		// create link to customer plural resource using method reference.
		final Link customersLink = linkTo(methodOn(CustomerRestController.class).getAll())
				.withRel(LinkRelation.of("customers"));

		// add links to DTO
		return entity.add(customerSelfLink, customersLink);
	}

}
