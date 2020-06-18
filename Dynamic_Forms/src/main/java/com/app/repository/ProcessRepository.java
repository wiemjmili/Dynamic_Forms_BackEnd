package com.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.app.model.Process;

@Repository
public interface ProcessRepository extends MongoRepository<Process,String> {}