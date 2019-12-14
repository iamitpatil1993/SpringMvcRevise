package com.example.mvc.revise.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mvc.revise.dao.CustomerRepository;
import com.example.mvc.revise.model.Customer;

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

	public Collection<Customer> findAll() {
		List<Customer> customers = new ArrayList<>(10);
		for (int i = 0; i < 10; i++) {
			customers.add(findById(i + 1l).get());
		}
		return customers;
	}
}
