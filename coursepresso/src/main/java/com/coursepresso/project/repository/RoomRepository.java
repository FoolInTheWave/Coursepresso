package com.coursepresso.project.repository;

import com.coursepresso.project.entity.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Caleb Miller
 */
@Repository
public interface RoomRepository extends CrudRepository<Room, String> {
  
  /**
   * Custom FIND method retrieves a Department record from the database
   * 
   * @param roomNumber The room number to match.
   * @return A Department record as a Department object.
   */
  @Query("SELECT r FROM Room r WHERE r.roomNumber = (:roomNumber)")
  public Room findByRoomNumber(@Param("roomNumber") String roomNumber);
}
