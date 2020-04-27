package com.app.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.app.model.User;

@Repository
public interface UserRepository extends MongoRepository<User,String>{}
