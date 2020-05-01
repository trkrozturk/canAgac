package com.can.tree.treeRepository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.can.tree.model.ExpectedPayments;
@Repository
public interface ExpectedPaymentsRepository extends ElasticsearchRepository<ExpectedPayments, String>{

	Page<ExpectedPayments> findById(String id, Pageable pageable);
	List<ExpectedPayments> findBySalesId(String salesId);
}
