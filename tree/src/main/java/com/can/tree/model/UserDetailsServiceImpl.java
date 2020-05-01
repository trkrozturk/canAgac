package com.can.tree.model;

import static java.util.Collections.emptyList;

import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.can.tree.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.can.tree.model.User user = userRepository.findByUsername(username, PageRequest.of(0, 1)).getContent().get(0);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        User userDetails = new User(user.getUsername(), user.getPassword(), emptyList());
        return userDetails;
    }
}
