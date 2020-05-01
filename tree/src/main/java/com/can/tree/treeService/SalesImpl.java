package com.can.tree.treeService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.can.tree.model.Sales;
import com.can.tree.treeRepository.SalesRepository;

@Service
public class SalesImpl implements SalesService{
	
	private SalesRepository salesRepository;

	@Autowired
    public void setSalesRepository(SalesRepository salesRepository) {
        this.salesRepository = salesRepository;
    }

	@Override
	public Optional<Sales> findOne(String id) {
		// TODO Auto-generated method stub
		return salesRepository.findById(id);
	}

	@Override
	public Iterable<Sales> findAll() {
		// TODO Auto-generated method stub
		return salesRepository.findAll();
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return salesRepository.count();
	}

	@Override
	public void delete(Sales sales) {
		// TODO Auto-generated method stub
		salesRepository.delete(sales);
	}

	@Override
	public Sales save(Sales sales) {
		// TODO Auto-generated method stub
		return salesRepository.save(sales);
	}

	@Override
	public Page<Sales> findBySalesCode(String salesCode, Pageable pageable) {
		// TODO Auto-generated method stub
		return salesRepository.findBySalesCode(salesCode, pageable);
	}

	@Override
	public List<Sales> findByCustomerId(String customerId) {
		// TODO Auto-generated method stub
		return salesRepository.findByCustomerId(customerId);
	}
}
