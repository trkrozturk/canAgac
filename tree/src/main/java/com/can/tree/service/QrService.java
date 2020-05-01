package com.can.tree.service;

import java.util.Optional;


import com.can.tree.model.Qr;

public interface QrService {
	Optional<Qr> findOne(String id);
	
	Iterable<Qr> findAll();
	
	
	long count();


    Qr save(Qr qr);
}
