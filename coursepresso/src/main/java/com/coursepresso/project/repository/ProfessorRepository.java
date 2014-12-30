package com.coursepresso.project.repository;

import com.coursepresso.project.entity.Professor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Caleb Miller
 */
@Repository
public interface ProfessorRepository extends CrudRepository<Professor, Integer> {
  
}
