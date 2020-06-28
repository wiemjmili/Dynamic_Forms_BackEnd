package com.app.repository;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.app.model.User;

@Repository
public interface UserRepository extends MongoRepository<User,String>{
	
	 User findByUsername(String username);
	 User findByName(String name);
	 Boolean existsByUsername(String username);
	 Boolean existsByEmail(String email);
}
