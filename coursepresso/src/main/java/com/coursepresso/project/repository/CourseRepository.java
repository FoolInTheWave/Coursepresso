package com.coursepresso.project.repository;

import com.coursepresso.project.entity.Course;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Caleb Miller
 */
@Repository
public interface CourseRepository extends CrudRepository<Course, String>  {
  
  /**
   * Custom FIND method retrieves a Course record from the database with an
   * initialized list of CourseSection records mapped by a foreign key.
   * 
   * @param courseNumber The courseNumber to match.
   * @return A Course record as a Course object.
   */
  @Query("SELECT c FROM Course c LEFT JOIN FETCH c.courseSectionList WHERE c.courseNumber = (:courseNumber)")
  public Course findByCourseNumberWithCourseSections(@Param("courseNumber") String courseNumber);
}
