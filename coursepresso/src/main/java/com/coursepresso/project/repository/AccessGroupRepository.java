package com.coursepresso.project.repository;

import com.coursepresso.project.entity.AccessGroup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Caleb Miller
 */
@Repository
public interface AccessGroupRepository extends CrudRepository<AccessGroup, Integer> {
  
}
