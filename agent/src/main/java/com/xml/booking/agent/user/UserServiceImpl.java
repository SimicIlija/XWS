package com.xml.booking.agent.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User findOne(Long id) {
		return userRepository.findOne(id);
	}

	@Override
	public User findOne(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	@Transactional(readOnly = false)
	public void updateDatabase(List<User> users) {
		userRepository.deleteAll();
		userRepository.save(users);
	}

	@Override
	public User logIn(UserLogIn user) {
		User existing = userRepository.findByEmail(user.getEmail());
		if(existing == null)
			return null;
		
		if(existing.getPassword().equals(user.getPassword()) && existing.getUserType().equals(UserType.AGENT))
			return existing;
		
		return null;
	}

}
