package com.can.tree.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.can.tree.model.ExpectedPayments;
import com.can.tree.treeRepository.ExpectedPaymentsRepository;
@Service
public class ExpectedPaymentServiceImpl implements ExpectedPaymentsService{

	private ExpectedPaymentsRepository expectedPaymentsRepository;
	@Autowired
	public void setExpectedPaymentsRepository(ExpectedPaymentsRepository expectedPaymentsRepository) {
		this.expectedPaymentsRepository=expectedPaymentsRepository;
	}
	@Override
	public Optional<ExpectedPayments> findOne(String id) {
		// TODO Auto-generated method stub
		return expectedPaymentsRepository.findById(id);
	}
	@Override
	public Iterable<ExpectedPayments> findAll() {
		// TODO Auto-generated method stub
		return expectedPaymentsRepository.findAll();
	}
	@Override
	public long count() {
		// TODO Auto-generated method stub
		return expectedPaymentsRepository.count();
	}
	@Override
	public void delete(ExpectedPayments expectedPayment) {
		// TODO Auto-generated method stub
		expectedPaymentsRepository.delete(expectedPayment);
	}
	@Override
	public ExpectedPayments save(ExpectedPayments expectedPayment) {
		// TODO Auto-generated method stub
		return expectedPaymentsRepository.save(expectedPayment);
	}
	@Override
	public List<ExpectedPayments> findBySalesId(String salesId) {
		// TODO Auto-generated method stub
		return expectedPaymentsRepository.findBySalesId(salesId);
	}
}
