package com.can.tree.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.can.tree.model.Logs;

public interface LogsService {

	Optional<Logs> findOne(String id);
	Page<Logs> findByAbout(String about, Pageable pageable);
	Iterable<Logs> findAll();
	
	Page<Logs> findByusername(String username, Pageable pageable);
	
//	Page<User> findByUsernameUsingCustomQuery(String name, Pageable pageable);
	
	long count();

    void delete(Logs log);

    Logs save(Logs log);
}
