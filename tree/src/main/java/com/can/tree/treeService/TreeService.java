package com.can.tree.treeService;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.can.tree.model.Tree;

public interface TreeService {

	Optional<Tree> findOne(String id);
	
	Iterable<Tree> findAll();
	
	Page<Tree> findByType(String type, Pageable pageable);
	Page<Tree> findByTypeLatin(String type, Pageable pageable);
	
//	Page<User> findByUsernameUsingCustomQuery(String name, Pageable pageable);
	
	long count();

    void delete(Tree tree);

    Tree save(Tree tree);
}
