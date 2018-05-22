package com.xml.booking.backendmain.lodging;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class LodgingServiceImpl implements LodgingService{

	@Autowired
	private LodgingRepository lodgingRepository;
	
	@Override
	public Lodging findOne(Long id) {
		try {
			return lodgingRepository.findById(id).get();
		
		}catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Lodging> findAll() {
		return lodgingRepository.findAll();
	}

	@Override
	@Transactional(readOnly = false)
	public Lodging add(Lodging lodging) {
		return lodgingRepository.save(lodging);
	}

	@Override
	@Transactional(readOnly = false)
	public Lodging delete(Lodging lodging) {
		// TODO Auto-generated method stub
		return null;
	}

}
