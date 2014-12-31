package com.coursepresso.project.repository;

import com.coursepresso.project.entity.Department;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Caleb Miller
 */
@Repository
public interface DepartmentRepository extends CrudRepository<Department, Integer> {
  
  /**
   * Custom SELECT method retrieves the name attribute from all Department 
   * records.
   * 
   * @return A List of name attributes as String objects.
   */
  @Query("SELECT name FROM Department")
  List<String> selectAllNames();
}
