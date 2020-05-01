package com.can.tree.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.can.tree.model.Qr;

@Repository
public interface QrRepository extends ElasticsearchRepository<Qr, String>{

	Iterable<Qr> findAll();

}
