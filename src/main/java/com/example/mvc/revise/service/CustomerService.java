package com.example.mvc.revise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mvc.revise.dao.CustomerRepository;
import com.example.mvc.revise.model.Customer;

import java.util.Optional;

@Service
public class CustomerService {

	private CustomerRepository customerRepository;

	@Autowired
	public CustomerService(final CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public Customer createCustomer(final Customer customer) {
		return customerRepository.create(customer);
	}

	public Customer updateCustomer(final Customer customer) {
		return customerRepository.update(customer);
	}

	public Optional<Customer> findById(final Long customerId) {
		return customerRepository.findById(customerId);
	}
}
