package com.can.tree.treeService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.can.tree.model.TreeObject;
import com.can.tree.treeRepository.TreeObjectRepository;

@Service
public class TreeObjectImpl implements TreeObjectService{

	 private TreeObjectRepository treeObjectRepository;
	 
	 @Autowired
	    public void setTreeObjectRepository(TreeObjectRepository treeObjectRepository) {
	        this.treeObjectRepository = treeObjectRepository;
	    }

	@Override
	public Optional<TreeObject> findOne(String id) {
		// TODO Auto-generated method stub
		return treeObjectRepository.findById(id);
	}

	@Override
	public Iterable<TreeObject> findAll() {
		// TODO Auto-generated method stub
		return treeObjectRepository.findAll();
	}

	@Override
	public Page<TreeObject> findByType(String type, Pageable pageable) {
		// TODO Auto-generated method stub
		return treeObjectRepository.findByType(type, pageable);
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return treeObjectRepository.count();
	}

	@Override
	public void delete(TreeObject treeObject) {
		// TODO Auto-generated method stub
		treeObjectRepository.delete(treeObject);
	}

	@Override
	public TreeObject save(TreeObject treeObject) {
		// TODO Auto-generated method stub
		return treeObjectRepository.save(treeObject);
	}
	 
	@Override
	public List<TreeObject> findBySalesId(String salesId){
		return treeObjectRepository.findBySalesId(salesId);
	}

}
