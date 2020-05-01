package com.can.tree.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.can.tree.model.Qr;
import com.can.tree.repository.QrRepository;
@Service
public class QrServiceImpl implements QrService{

    private QrRepository qrRepository;

    
    @Autowired
    public void setQrRepository(QrRepository qrRepository) {
        this.qrRepository = qrRepository;
    }
        
	@Override
	public Optional<Qr> findOne(String id) {
		// TODO Auto-generated method stub
		return qrRepository.findById(id);
	}

	@Override
	public Iterable<Qr> findAll() {
		// TODO Auto-generated method stub
		return qrRepository.findAll();
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return qrRepository.count();
	}

	@Override
	public Qr save(Qr qr) {
		// TODO Auto-generated method stub
		return qrRepository.save(qr);
	}

}
