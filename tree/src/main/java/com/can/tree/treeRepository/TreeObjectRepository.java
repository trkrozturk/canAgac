package com.can.tree.treeRepository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.can.tree.model.TreeObject;
@Repository
public interface TreeObjectRepository extends ElasticsearchRepository<TreeObject, String>{
	Page<TreeObject> findByType(String type, Pageable pageable);
	List<TreeObject> findBySalesId(String salesId);

}
