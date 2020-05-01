package com.can.tree.treeService;

import java.util.Optional;


import com.can.tree.model.Measurement;

public interface MeasurementService {
	Optional<Measurement> findOne(String id);
	
	Iterable<Measurement> findAll();
	
	//Page<Measurement> findByType(String type, Pageable pageable);
	
//	Page<User> findByUsernameUsingCustomQuery(String name, Pageable pageable);
	
	long count();

    void delete(Measurement measurement);

    Measurement save(Measurement measurement);
}
