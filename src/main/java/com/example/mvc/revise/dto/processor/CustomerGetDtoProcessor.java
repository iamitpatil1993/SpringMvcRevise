package com.example.mvc.revise.dto.processor;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.mvc.revise.dto.CustomerGetDto;
import com.example.mvc.revise.dto.assembler.CustomerModelAssembler;

/**
 * This processor will get applied on {@link CustomerGetDto} when
 * {@link CustomerModelAssembler} get applied.
 * 
 * Unlike {@link RepresentationModelAssembler}s spring will automatically call
 * these {@link RepresentationModelProcessor}s, and we do not need to manually
 * call them. Spring will automatically call this processor irrespective of how
 * we created {@link CustomerGetDto} i,e {@link RepresentationModel} i.e
 * manually or through {@link RepresentationModelAssembler}s
 * 
 * @author amipatil
 *
 */
@Component
public class CustomerGetDtoProcessor implements RepresentationModelProcessor<CustomerGetDto> {

	/**
	 * We can modify passed model whatever way we want. We can completely replace
	 * this object by returning completely new object
	 */
	@Override
	public CustomerGetDto process(CustomerGetDto model) {

		Link link = new Link(ServletUriComponentsBuilder.fromCurrentContextPath().path("/barFoo").build().toString(),
				LinkRelation.of("barFoos"));

		model.add(link);
		return model;
	}

}
