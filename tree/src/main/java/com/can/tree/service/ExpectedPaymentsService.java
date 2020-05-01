package com.can.tree.service;

import java.util.List;
import java.util.Optional;

import com.can.tree.model.ExpectedPayments;

public interface ExpectedPaymentsService {

	Optional<ExpectedPayments> findOne(String id);
	Iterable<ExpectedPayments> findAll();
	List<ExpectedPayments> findBySalesId(String salesId);
	//Page<ExpectedPayments> findByusername(String username, Pageable pageable);
	
//	Page<User> findByUsernameUsingCustomQuery(String name, Pageable pageable);
	
	long count();

    void delete(ExpectedPayments expectedPayment);

    ExpectedPayments save(ExpectedPayments expectedPayment);
}
