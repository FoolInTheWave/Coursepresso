package com.coursepresso.project.repository;

import com.coursepresso.project.entity.Professor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Caleb Miller
 */
@Repository
public interface ProfessorRepository extends CrudRepository<Professor, Integer> {
  
  /**
   * Custom FIND method retrieves a Professor record from the database
   * 
   * @param id The id to match.
   * @return A Professor record as a Professor object.
   */
  @Query("SELECT p FROM Professor p WHERE p.id = (:id)")
  public Professor findById(@Param("id") Integer id);
  
}
