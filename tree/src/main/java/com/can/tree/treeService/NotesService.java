package com.can.tree.treeService;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.can.tree.model.Notes;

public interface NotesService {

	Optional<Notes> findOne(String id);
	Page<Notes> findBySalesId(String salesId, Pageable pageable);
	Iterable<Notes> findAll();
	
	//Page<Measurement> findByType(String type, Pageable pageable);
	
//	Page<User> findByUsernameUsingCustomQuery(String name, Pageable pageable);
	
	long count();

    void delete(Notes note);

    Notes save(Notes note);
}
