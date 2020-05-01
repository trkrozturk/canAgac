package com.can.tree.treeService;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.can.tree.model.TreeObject;

public interface TreeObjectService {

	Optional<TreeObject> findOne(String id);
	
	Iterable<TreeObject> findAll();
	
	Page<TreeObject> findByType(String type, Pageable pageable);
	
//	Page<User> findByUsernameUsingCustomQuery(String name, Pageable pageable);
	
	long count();

    void delete(TreeObject treeObject);

    TreeObject save(TreeObject treeObject);
	List<TreeObject> findBySalesId(String salesId);
}
