package com.coursepresso.project.repository;

import com.coursepresso.project.entity.Term;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Caleb Miller
 */
@Repository
public interface TermRepository extends CrudRepository<Term, String> {
  
    /**
   * Custom FIND method retrieves a Term record from the database with 
   * an initialized CourseSection list mapped by a foreign key.
   * 
   * @param term The term to match.
   * @return A Term record as a Term object.
   */
  @Query("SELECT t FROM Term t LEFT JOIN FETCH t.courseSectionList WHERE t.term = (:term)")
  public Term findByTermWithCourseSections(@Param("term") String term);
}
