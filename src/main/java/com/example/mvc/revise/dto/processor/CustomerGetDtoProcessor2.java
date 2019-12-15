package com.example.mvc.revise.dto.processor;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.mvc.revise.dto.CustomerGetDto;

/**
 * We can have more than one {@link RepresentationModelProcessor}s on same
 * {@link RepresentationModel} type. Spring will call both. (Need to check
 * sequence) This is another {@link RepresentationModelProcessor} for
 * {@link CustomerGetDto}. Spring will call both.
 * 
 * @author amipatil
 *
 */
@Component
public class CustomerGetDtoProcessor2 implements RepresentationModelProcessor<CustomerGetDto> {

	/**
	 * Do anything you want with object.
	 */
	@Override
	public CustomerGetDto process(CustomerGetDto model) {

		Link link = new Link(ServletUriComponentsBuilder.fromCurrentContextPath().path("/fooBars").build().toString(),
				LinkRelation.of("fooBars"));

		model.add(link);
		return model;
	}

}
