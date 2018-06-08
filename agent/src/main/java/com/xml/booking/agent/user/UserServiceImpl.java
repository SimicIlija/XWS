package com.xml.booking.agent.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xml.booking.agent.backendMain.ConverterXML;

import backendmain.wsdl.UserXML;

@Transactional(readOnly = true)
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ConverterXML converterXML;
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
	public void cloneDB(List<UserXML> usersXML) {
		userRepository.deleteAllInBatch();
		for(UserXML userXML : usersXML) {
			userRepository.save(new User(userXML));
		}
	}

	@Override
	@Transactional(readOnly = false)
	public User logIn(UserLogIn user) {
		try {
			converterXML.cloneDB();
		} catch (Exception e) {
			return null;
		}
		User existing = userRepository.findByEmail(user.getEmail());
		if(existing == null)
			return null;
		
		if(existing.getPassword().equals(user.getPassword()) && existing.getUserType().equals(UserType.AGENT))
			return existing;
		
		return null;
	}

}
