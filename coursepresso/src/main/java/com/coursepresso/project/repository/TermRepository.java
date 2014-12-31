package com.coursepresso.project.repository;

import com.coursepresso.project.entity.Term;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Caleb Miller
 */
@Repository
public interface TermRepository extends CrudRepository<Term, Integer> {
  
  /**
   * Custom SELECT method retrieves the term attribute from all Term 
   * records.
   * 
   * @return A List of term attributes as String objects.
   */
  @Query("SELECT term FROM Term")
  List<String> selectAllTerms();
}
