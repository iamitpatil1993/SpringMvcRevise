package com.example.mvc.revise.dao;

import java.util.Calendar;

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
}
