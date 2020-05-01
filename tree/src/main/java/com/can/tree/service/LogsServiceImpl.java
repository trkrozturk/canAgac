package com.can.tree.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.can.tree.model.Logs;
import com.can.tree.repository.LogsRepository;
@Service
public class LogsServiceImpl implements LogsService{

	private LogsRepository logsRepository;
	@Autowired
	public void setLogsRepository(LogsRepository logsRepository) {
		this.logsRepository = logsRepository;
	}

	@Override
	public Optional<Logs> findOne(String id) {
		// TODO Auto-generated method stub
		return logsRepository.findById(id);
	}

	@Override
	public Iterable<Logs> findAll() {
		// TODO Auto-generated method stub
		return logsRepository.findAll();
	}

	@Override
	public Page<Logs> findByusername(String username, Pageable pageable) {
		// TODO Auto-generated method stub
		return logsRepository.findByUsername(username, pageable);
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return logsRepository.count();
	}

	@Override
	public void delete(Logs log) {
		// TODO Auto-generated method stub
		logsRepository.delete(log);
	}

	@Override
	public Logs save(Logs log) {
		// TODO Auto-generated method stub
		return logsRepository.save(log);
	}

	@Override
	public Page<Logs> findByAbout(String about, Pageable pageable) {
		// TODO Auto-generated method stub
		return logsRepository.findByAbout(about, pageable);
	}

}
