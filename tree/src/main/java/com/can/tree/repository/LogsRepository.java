package com.can.tree.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.can.tree.model.Logs;

@Repository
public interface LogsRepository extends ElasticsearchRepository<Logs, String>{
	Page<Logs> findByUsername(String username, Pageable pageable);
	Page<Logs> findByAbout(String about, Pageable pageable);
}
