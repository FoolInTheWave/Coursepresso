package com.coursepresso.project.repository;

import com.coursepresso.project.entity.MeetingTime;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Caleb Miller
 */
@Repository
public interface MeetingTimeRepository extends CrudRepository<MeetingTime, Date> {
  
  /**
   * Custom SELECT method retrieves the meetingTime attribute from all 
   * MeetingTime records.
   * 
   * @return A List of meetingTime attributes as Date objects.
   */
  @Query("select meeting_time from meeting_time")
  List<Date> selectAllMeetingTimes();
}
