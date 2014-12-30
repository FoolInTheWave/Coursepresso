package com.coursepresso.project.repository;

import java.util.List;
import com.coursepresso.project.entity.CourseSection;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Caleb Miller
 */
@Repository
public interface CourseSectionRepository extends CrudRepository<CourseSection, Integer> {
  
  /**
   * Custom FIND method retrieves CourseSection records from the database with a
   * matching section number.
   * 
   * @param sectionNumber The section number to match.
   * @return A List of CourseSection records as CourseSection objects.
   */
  List<CourseSection> findBySectionNumber(int sectionNumber);
}
