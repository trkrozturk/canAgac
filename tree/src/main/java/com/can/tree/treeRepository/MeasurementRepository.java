package com.can.tree.treeRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.can.tree.model.Measurement;

@Repository
public interface MeasurementRepository extends ElasticsearchRepository<Measurement, String>{

	Page<Measurement> findById(String id, Pageable pageable);

}
