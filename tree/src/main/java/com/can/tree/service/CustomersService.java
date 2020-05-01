package com.can.tree.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.can.tree.model.Customers;

public interface CustomersService {
Optional<Customers> findOne(String id);
	
	Iterable<Customers> findAll();
	
	Page<Customers> findByCustomersName(String name, Pageable pageable);
	
//	Page<User> findByUsernameUsingCustomQuery(String name, Pageable pageable);
	
	long count();

    void delete(Customers user);

    Customers save(Customers user);
}
