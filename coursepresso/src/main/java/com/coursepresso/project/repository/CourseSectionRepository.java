package com.coursepresso.project.repository;

import java.util.List;
import com.coursepresso.project.entity.CourseSection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
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
  
  /**
   * Custom FIND method retrieves a Department record from the database with an
   * initialized list of Professor records mapped by a foreign key.
   * 
   * @param name The name to match.
   * @return A Department record as a Department object.
   */
  @Query("SELECT cs FROM CourseSection cs JOIN FETCH cs.meetingDayList WHERE cs.id = (:id)")
  public CourseSection findByIdWithMeetingDays(@Param("id") Integer id);
}
