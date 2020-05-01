package com.can.tree.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.can.tree.model.User;


@Repository
public interface UserRepository extends ElasticsearchRepository<User, String>{

	Page<User> findByUsername(String name, Pageable pageable);
}
