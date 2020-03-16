package com.BD.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.BD.model.User;

@Repository
public interface UserRepository extends MongoRepository<User,String>{

}
