package com.example.mvc.revise.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.Provider;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.example.mvc.revise.dto.CustomerGetDto;
import com.example.mvc.revise.dto.CustomerPostDto;
import com.example.mvc.revise.dto.CustomerUpdateDto;
import com.example.mvc.revise.model.Customer;

/**
 * Never include [by mistake] web components in this root configuration. I had
 * issue in component scan here, and HandlerInterceptors I declared were part of
 * both web and root config, hence they were getting called two times for each
 * request. So, selectively include packages for Root context or selectively
 * exclude web packages from root context.
 * 
 * @author amipatil
 *
 */
@Configuration
@ComponentScan(basePackages = { "com.example.mvc.revise.dao", "com.example.mvc.revise.service",
		"com.example.mvc.revise.util" }, excludeFilters = {
				@ComponentScan.Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class) })
public class RootConfiguration {

	/**
	 * {@link ModelMapper} should be singleton in application so that, we can define
	 * {@link TypeMap}, {@link PropertyMap}, {@link Converter}, {@link Provider} and
	 * all other configurations and mappings at single place and entire application
	 * will share same instance and configuration.
	 * 
	 * @return Fully configured/customized {@link ModelMapper}
	 */
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();

		// All custom mapping between objects will go here
		TypeMap<CustomerPostDto, Customer> custmerTyprMapper = modelMapper.createTypeMap(CustomerPostDto.class,
				Customer.class);
		custmerTyprMapper.addMapping(CustomerPostDto::getFirstName, Customer::setFName);
		custmerTyprMapper.addMapping(CustomerPostDto::getLastName, Customer::setLName);

		TypeMap<Customer, CustomerGetDto> customerDtoTyeMap = modelMapper.createTypeMap(Customer.class,
				CustomerGetDto.class);
		customerDtoTyeMap.<String>addMapping(Customer::getFName, CustomerGetDto::setFistName);
		customerDtoTyeMap.<String>addMapping(Customer::getLName, CustomerGetDto::setLastName);

		TypeMap<CustomerUpdateDto, Customer> custmerUpdateDtoTyprMapper = modelMapper
				.createTypeMap(CustomerUpdateDto.class, Customer.class);
		custmerUpdateDtoTyprMapper.addMapping(CustomerUpdateDto::getFirstName, Customer::setFName);
		custmerUpdateDtoTyprMapper.addMapping(CustomerUpdateDto::getLastName, Customer::setLName);

		return modelMapper;
	}
}
