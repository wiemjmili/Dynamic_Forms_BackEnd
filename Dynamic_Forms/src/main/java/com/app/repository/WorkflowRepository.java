package com.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.app.model.Workflow;

@Repository
public interface WorkflowRepository extends MongoRepository<Workflow,String>{

}
