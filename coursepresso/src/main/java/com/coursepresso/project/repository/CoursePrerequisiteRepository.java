package com.coursepresso.project.repository;

import com.coursepresso.project.entity.CoursePrerequisite;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Caleb Miller
 */
@Repository
public interface CoursePrerequisiteRepository extends CrudRepository<CoursePrerequisite, Integer> {
  
}
