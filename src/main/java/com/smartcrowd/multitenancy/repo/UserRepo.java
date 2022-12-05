package com.smartcrowd.multitenancy.repo;

import com.smartcrowd.multitenancy.entity.User;

import org.springframework.data.repository.CrudRepository;


public interface UserRepo extends CrudRepository<User, Integer> {

}


