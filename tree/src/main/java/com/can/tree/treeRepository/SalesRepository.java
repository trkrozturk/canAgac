package com.can.tree.treeRepository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.can.tree.model.Sales;

@Repository
public interface SalesRepository extends ElasticsearchRepository<Sales, String>{
	Page<Sales> findById(String id, Pageable pageable);
	Page<Sales> findBySalesCode(String salesCode, Pageable pageable);
	List<Sales> findByCustomerId(String customerId);

}
