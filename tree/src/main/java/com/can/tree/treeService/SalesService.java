package com.can.tree.treeService;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.can.tree.model.Sales;

public interface SalesService {
	
	Optional<Sales> findOne(String id);
	List<Sales> findByCustomerId(String customerId);
	Iterable<Sales> findAll();
	Page<Sales> findBySalesCode(String salesCode, Pageable pageable);
//	Page<Measurement> findByType(String type, Pageable pageable);
	
//	Page<User> findByUsernameUsingCustomQuery(String name, Pageable pageable);
	
	long count();

    void delete(Sales sales);

    Sales save(Sales sales);
}
