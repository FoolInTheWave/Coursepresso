package com.coursepresso.project.repository;

import com.coursepresso.project.entity.GroupPrivilege;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Caleb Miller
 */
@Repository
public interface GroupPrivilegeRepository extends CrudRepository<GroupPrivilege, Integer> {
  
}
