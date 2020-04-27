package com.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.app.model.Form;

@Repository
public interface FormRepository extends MongoRepository<Form,String> {}
