package com.can.tree.treeRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.can.tree.model.Notes;

public interface NotesRepository  extends ElasticsearchRepository<Notes, String>{
	Page<Notes> findById(String id, Pageable pageable);
	Page<Notes> findBySalesId(String salesId, Pageable pageable);

}
