package com.can.tree.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.can.tree.model.Customers;

@Repository
public interface CustomersRepository  extends ElasticsearchRepository<Customers, String>{

	Page<Customers> findByCustomerName(String name, Pageable pageable);
}
