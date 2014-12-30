package com.coursepresso.project.repository;

import com.coursepresso.project.entity.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Caleb Miller
 */
@Repository
public interface CourseRepository extends CrudRepository<Course, String>  {
  
}
