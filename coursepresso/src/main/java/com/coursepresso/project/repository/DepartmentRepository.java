package com.coursepresso.project.repository;

import com.coursepresso.project.entity.Department;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Caleb Miller
 */
@Repository
public interface DepartmentRepository extends CrudRepository<Department, String> {
  
  /**
   * Custom FIND method retrieves a Department record from the database with an
   * initialized list of Course records mapped by a foreign key.
   * 
   * @param name The name to match.
   * @return A Department record as a Department object.
   */
  @Query("SELECT d FROM Department d JOIN FETCH d.courseList WHERE d.name = (:name)")
  public Department findByNameWithCourses(@Param("name") String name);
  
  /**
   * Custom FIND method retrieves a Department record from the database with an
   * initialized list of Professor records mapped by a foreign key.
   * 
   * @param name The name to match.
   * @return A Department record as a Department object.
   */
  @Query("SELECT d FROM Department d JOIN FETCH d.professorList WHERE d.name = (:name)")
  public Department findByNameWithProfessors(@Param("name") String name);
}
