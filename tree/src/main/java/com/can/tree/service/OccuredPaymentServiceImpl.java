package com.can.tree.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.can.tree.model.OccuredPayments;
import com.can.tree.treeRepository.OccuredPaymentsRepository;
@Service
public class OccuredPaymentServiceImpl implements OccuredPaymentsService{

	private OccuredPaymentsRepository occuredPaymentsRepository;
	@Autowired
	public void setOccuredPaymentsRepository(OccuredPaymentsRepository occuredPaymentsRepository) {
		this.occuredPaymentsRepository=occuredPaymentsRepository;
	}
	@Override
	public Optional<OccuredPayments> findOne(String id) {
		// TODO Auto-generated method stub
		return occuredPaymentsRepository.findById(id);
	}
	@Override
	public Iterable<OccuredPayments> findAll() {
		// TODO Auto-generated method stub
		return occuredPaymentsRepository.findAll();
	}
	@Override
	public long count() {
		// TODO Auto-generated method stub
		return occuredPaymentsRepository.count();
	}
	@Override
	public void delete(OccuredPayments occuredPayment) {
		// TODO Auto-generated method stub
		occuredPaymentsRepository.delete(occuredPayment);
	}
	@Override
	public OccuredPayments save(OccuredPayments occuredPayment) {
		// TODO Auto-generated method stub
		return occuredPaymentsRepository.save(occuredPayment);
	}
	@Override
	public List<OccuredPayments> findBySalesId(String salesId) {
		// TODO Auto-generated method stub
		return occuredPaymentsRepository.findBySalesId(salesId);
	}
}
