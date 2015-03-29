package com.coursepresso.project.repository;

import java.util.List;
import com.coursepresso.project.entity.CourseSection;
import org.springframework.data.jpa.repository.Modifying;
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
   * Custom FIND method retrieves a CourseSection record from the database with 
   * an initialized list of MeetingDay records mapped by a foreign key.
   * 
   * @param id The id to match.
   * @return A CourseSection record as a CourseSection object.
   */
  @Query("SELECT cs FROM CourseSection cs LEFT JOIN FETCH cs.meetingDayList WHERE cs.id = (:id)")
  public CourseSection findByIdWithMeetingDays(@Param("id") Integer id);
  
  /**
   * Custom FIND method retrieves a CourseSection record from the database with 
   * an initialized Course record mapped by a foreign key.
   * 
   * @param id The id to match.
   * @return A CourseSection record as a CourseSection object.
   */
  @Query("SELECT cs FROM CourseSection cs JOIN FETCH cs.courseNumber WHERE cs.id = (:id)")
  public CourseSection findByIdWithCourse(@Param("id") Integer id);
  
  /**
   * Custom FIND method retrieves a CourseSection record from the database with 
   * an initialized Term record mapped by a foreign key.
   * 
   * @param id The id to match.
   * @return A CourseSection record as a CourseSection object.
   */
  @Query("SELECT cs FROM CourseSection cs JOIN FETCH cs.term WHERE cs.id = (:id)")
  public CourseSection findByIdWithTerm(@Param("id") Integer id);
  
  /**
   * Custom FIND method retrieves a CourseSection record from the database with 
   * an initialized Department record mapped by a foreign key.
   * 
   * @param id The id to match.
   * @return A CourseSection record as a CourseSection object.
   */
  @Query("SELECT cs FROM CourseSection cs JOIN FETCH cs.department WHERE cs.id = (:id)")
  public CourseSection findByIdWithDepartment(@Param("id") Integer id);
  
  /**
   * Custom FIND method retrieves a CourseSection record from the database with 
   * an initialized Professor record mapped by a foreign key.
   * 
   * @param id The id to match.
   * @return A CourseSection record as a CourseSection object.
   */
  @Query("SELECT cs FROM CourseSection cs JOIN FETCH cs.professorId WHERE cs.id = (:id)")
  public CourseSection findByIdWithProfessor(@Param("id") Integer id);
  
  @Modifying
  @Query("delete from CourseSection cs where cs.id = (:id)")
  @Override
  void delete(@Param("id") Integer id);
}
