package com.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.app.model.Requests;

@Repository
public interface RequestsRepository extends MongoRepository<Requests,String> {}
