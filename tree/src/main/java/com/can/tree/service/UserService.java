package com.can.tree.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.can.tree.model.User;

public interface UserService {

	
	Optional<User> findOne(String id);
	
	Iterable<User> findAll();
	
	Page<User> findByUserName(String name, Pageable pageable);
	
//	Page<User> findByUsernameUsingCustomQuery(String name, Pageable pageable);
	
	long count();

    void delete(User user);

	User save(User user);
	
}
