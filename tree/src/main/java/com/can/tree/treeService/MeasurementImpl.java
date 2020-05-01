package com.can.tree.treeService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.can.tree.model.Measurement;
import com.can.tree.treeRepository.MeasurementRepository;

@Service
public class MeasurementImpl  implements MeasurementService{

	private MeasurementRepository measurementRepository;

	@Autowired
    public void setMeasurementRepository(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }
	@Override
	public Optional<Measurement> findOne(String id) {
		// TODO Auto-generated method stub
		return measurementRepository.findById(id);
	}

	@Override
	public Iterable<Measurement> findAll() {
		// TODO Auto-generated method stub
		return measurementRepository.findAll();
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return measurementRepository.count();
	}

	@Override
	public void delete(Measurement measurement) {
		// TODO Auto-generated method stub
		measurementRepository.delete(measurement);
	}

	@Override
	public Measurement save(Measurement measurement) {
		// TODO Auto-generated method stub
		return measurementRepository.save(measurement);
	}
	
}
