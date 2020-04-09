package com.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.app.model.UserTask;

@Repository
public interface UserTaskRepository extends MongoRepository<UserTask,String> {

}
