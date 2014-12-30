package com.coursepresso.project.repository;

import com.coursepresso.project.entity.Privilege;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Caleb Miller
 */
@Repository
public interface PrivilegeRepository extends CrudRepository<Privilege, Integer> {
  
}
