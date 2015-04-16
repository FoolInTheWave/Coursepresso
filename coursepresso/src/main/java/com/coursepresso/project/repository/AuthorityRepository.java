package com.coursepresso.project.repository;

import com.coursepresso.project.entity.Authority;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Caleb Miller
 */
@Repository
public interface AuthorityRepository extends CrudRepository<Authority, Integer> {
  
}