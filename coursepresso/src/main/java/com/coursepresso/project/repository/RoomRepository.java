package com.coursepresso.project.repository;

import com.coursepresso.project.entity.Room;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Caleb Miller
 */
@Repository
public interface RoomRepository extends CrudRepository<Room, String> {

  /**
   * Custom GET method retrieves all Room records from the database with the
   * MeetingDay list initialized.
   *
   * @return A List of Room objects.
   */
  @Query("SELECT r FROM Room r LEFT JOIN FETCH r.meetingDayList")
  public List<Room> getRoomsWithMeetingDays();
}
