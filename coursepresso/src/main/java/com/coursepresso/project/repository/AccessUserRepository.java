package com.coursepresso.project.repository;

import com.coursepresso.project.entity.AccessUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Caleb Miller
 */
@Repository
public interface AccessUserRepository extends CrudRepository<AccessUser, Integer> {
  
}
