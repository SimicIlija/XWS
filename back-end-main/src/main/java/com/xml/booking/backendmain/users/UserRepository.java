package com.xml.booking.backendmain.users;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);
	
	User findByToken(String token);
	
	List<User> findByUserTypeIn(List<UserType> userTypes);
	
	List<User> findByDeleted(boolean deleted);

}
