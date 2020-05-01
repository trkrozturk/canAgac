package com.can.tree.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.can.tree.model.Customers;
import com.can.tree.repository.CustomersRepository;

@Service
public class CustomerServiceImpl implements CustomersService{

	private CustomersRepository customersRepository;
	
	@Autowired
	public void setCustomerRepository(CustomersRepository customersRepository) {
		this.customersRepository = customersRepository;
	}

	@Override
	public Optional<Customers> findOne(String id) {
		// TODO Auto-generated method stub
		return customersRepository.findById(id);
	}

	@Override
	public Iterable<Customers> findAll() {
		// TODO Auto-generated method stub
		return customersRepository.findAll();
	}

	@Override
	public Page<Customers> findByCustomersName(String name, Pageable pageable) {
		// TODO Auto-generated method stub
		return customersRepository.findByCustomerName(name, pageable);
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return customersRepository.count();
	}

	@Override
	public void delete(Customers user) {
		// TODO Auto-generated method stub
		customersRepository.delete(user);
	}

	@Override
	public Customers save(Customers user) {
		// TODO Auto-generated method stub
		return customersRepository.save(user);
	}
}
