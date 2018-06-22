package com.xml.booking.backendmain.users;

import java.util.Arrays;
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
		if(id == null)
			return null;
		return userRepository.findById(id).get();
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	@Transactional(readOnly = false)
	public User register(User user) {
		User existing = userRepository.findByEmail(user.getEmail());
		if(existing == null)
			return userRepository.save(user);
		
		return null;
	}
	
	@Override
	@Transactional(readOnly = false)
	public User confirm(String token) {
		User existing = userRepository.findByToken(token);
		if(existing == null)
			return null;
		
		return userRepository.save(existing);
	}

	@Override
	public User logIn(User user) {
		User existing = userRepository.findByEmail(user.getEmail());
		if(existing == null)
			return null;
		
		if(existing.getPassword().equals(user.getPassword()) )
			return existing;
		
		return null;
	}

	@Override
	@Transactional(readOnly = false)
	public User edit(Long id, User user) {
		User existing = findOne(id);
		if(existing == null)
			return null;

		user.setId(id);
		user.setEmail(existing.getEmail());
		user.setUserType(existing.getUserType());
		
		
		return userRepository.save(user);
	}

	@Override
	@Transactional(readOnly = false)
	public User delete(Long id) {
		User user= findOne(id);
		if(user == null)
			return null;
		try {
			userRepository.delete(user);
		}catch(Exception e){
			System.out.println("Could not delete User element");
			return null;
		}
		return user;
	}
	
	@Override
	@Transactional(readOnly = false)
	public User block_unblock(Long id) {
		User user= findOne(id);
		if(user == null)
			return null;
		if(user.isBlocked())
			user.setBlocked(false);
		else 
			user.setBlocked(true);
		try {
			userRepository.save(user);
		}catch(Exception e){
			System.out.println("Could not block_ublock User");
			return null;
		}
		return user;
	}

	@Override
	public List<User> findAllNotAdmin() {
		return userRepository.findByUserTypeIn(Arrays.asList(UserType.AGENT, UserType.VISITOR));
	}


}
