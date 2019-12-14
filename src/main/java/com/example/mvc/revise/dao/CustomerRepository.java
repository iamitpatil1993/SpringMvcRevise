package com.example.mvc.revise.dao;

import java.util.Calendar;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.mvc.revise.model.Customer;

@Repository
public class CustomerRepository {

	public Customer create(final Customer customer) {
		customer.setId(Double.valueOf(Math.random() * 100).longValue());
		customer.setCreatedOn(Calendar.getInstance());
		customer.setUpdatedOn(Calendar.getInstance());
		customer.setIsDeleted(false);
		customer.setIsActive(true);
		return customer;
	}

	public Customer update(final Customer customer) {
		customer.setUpdatedOn(Calendar.getInstance());
		return customer;
	}

	public Optional<Customer> findById(Long customerId) {
		Customer customer = new Customer();
		customer.setId(Double.valueOf(Math.random() * 100).longValue());
		customer.setFName("Amit");
		customer.setLName("Patil");
		customer.setCreatedOn(Calendar.getInstance());
		customer.setUpdatedOn(Calendar.getInstance());
		customer.setIsDeleted(false);
		customer.setIsActive(true);
		return Optional.of(customer);
	}
}
