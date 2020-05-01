package com.can.tree.service;

import java.util.List;
import java.util.Optional;

import com.can.tree.model.OccuredPayments;

public interface OccuredPaymentsService {

	Optional<OccuredPayments> findOne(String id);
	List<OccuredPayments> findBySalesId(String salesId);

	Iterable<OccuredPayments> findAll();
	
	//Page<OccuredPayments> findByusername(String username, Pageable pageable);
	
//	Page<User> findByUsernameUsingCustomQuery(String name, Pageable pageable);
	
	long count();

    void delete(OccuredPayments OccuredPayment);

    OccuredPayments save(OccuredPayments occuredPayment);
}
