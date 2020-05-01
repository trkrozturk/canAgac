package com.can.tree.treeService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.can.tree.model.Notes;
import com.can.tree.treeRepository.NotesRepository;

@Service
public class NotesServiceImpl implements NotesService{
	private NotesRepository notesRepository;

	@Autowired
    public void setNotesRepository(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

	@Override
	public Optional<Notes> findOne(String id) {
		// TODO Auto-generated method stub
		return notesRepository.findById(id);
	}

	@Override
	public Iterable<Notes> findAll() {
		// TODO Auto-generated method stub
		return notesRepository.findAll();
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return notesRepository.count();
	}

	@Override
	public void delete(Notes note) {
		// TODO Auto-generated method stub
		notesRepository.delete(note);	
	}

	@Override
	public Notes save(Notes note) {
		// TODO Auto-generated method stub
		return notesRepository.save(note);
	}

	@Override
	public Page<Notes> findBySalesId(String salesId, Pageable pageable) {
		// TODO Auto-generated method stub
		return notesRepository.findBySalesId(salesId, pageable);
	}
}
