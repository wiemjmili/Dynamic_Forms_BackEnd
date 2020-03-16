package com.BD.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.BD.model.UserTask;

@Repository
public interface UserTaskRepository extends MongoRepository<UserTask,String> {

}
