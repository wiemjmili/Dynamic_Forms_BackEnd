package com.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.app.model.Role;

@Repository
public interface RoleRepository extends MongoRepository<Role,String>{}
