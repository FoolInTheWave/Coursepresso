package com.coursepresso.project.repository;

import com.coursepresso.project.entity.Term;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Caleb Miller
 */
@Repository
public interface TermRepository extends CrudRepository<Term, String> {
  
}
