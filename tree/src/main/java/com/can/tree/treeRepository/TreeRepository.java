package com.can.tree.treeRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import com.can.tree.model.Tree;
@Repository
public interface TreeRepository extends ElasticsearchRepository<Tree, String>{

	Page<Tree> findByType(String type, Pageable pageable);
	Page<Tree> findByTypeLatin(String type, Pageable pageable);
	
}
