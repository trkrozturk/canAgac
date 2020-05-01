package com.can.tree.treeRepository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.can.tree.model.OccuredPayments;
@Repository
public interface OccuredPaymentsRepository extends ElasticsearchRepository<OccuredPayments, String>{

	Page<OccuredPayments> findById(String id, Pageable pageable);
	List<OccuredPayments> findBySalesId(String salesId);

}
