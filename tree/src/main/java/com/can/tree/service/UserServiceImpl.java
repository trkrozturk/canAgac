package com.can.tree.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.can.tree.model.User;
import com.can.tree.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService {
	
    private UserRepository userRepository;
    
    
//    public UserServiceImpl(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findOne(String id) {
        return userRepository.findById(id);
    }

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> findByUserName(String name, Pageable pageable) {
        return userRepository.findByUsername(name, pageable);
    }

//    @Override
//    public Page<User> findByUsernameUsingCustomQuery(String name, Pageable pageable) {
//        return userRepository.findByUsernameUsingCustomQuery(name, pageable);
//    }

    @Override
    public long count() {
        return userRepository.count();
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }
}