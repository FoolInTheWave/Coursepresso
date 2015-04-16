package com.coursepresso.project.repository;

import com.coursepresso.project.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Caleb Miller
 */
@Repository
public interface UserRepository extends CrudRepository<User, String> {
  
}