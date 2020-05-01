package com.can.tree.treeService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.can.tree.model.Tree;
import com.can.tree.treeRepository.TreeRepository;

@Service
public class TreeServiceImpl implements TreeService{

	private TreeRepository treeRepository;
	
	@Autowired
    public void setTreeRepository(TreeRepository treeRepository) {
        this.treeRepository = treeRepository;
    }
	
	@Override
	public Optional<Tree> findOne(String id) {
		// TODO Auto-generated method stub
		return treeRepository.findById(id);
	}

	@Override
	public Iterable<Tree> findAll() {
		// TODO Auto-generated method stub
		return treeRepository.findAll();
	}

	@Override
	public Page<Tree> findByType(String type, Pageable pageable) {
		// TODO Auto-generated method stub
		return treeRepository.findByType(type, pageable);
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return treeRepository.count();
	}

	@Override
	public void delete(Tree tree) {
		// TODO Auto-generated method stub
		treeRepository.delete(tree);
	}

	@Override
	public Tree save(Tree tree) {
		// TODO Auto-generated method stub
		return treeRepository.save(tree);
	}

	@Override
	public Page<Tree> findByTypeLatin(String type, Pageable pageable) {
		// TODO Auto-generated method stub
		return treeRepository.findByTypeLatin(type, pageable);
	}

}
