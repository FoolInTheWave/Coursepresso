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
   * Custom SELECT method retrieves the roomNumber attribute from all 
   * Room records.
   * 
   * @return A List of roomNumber attributes as String objects.
   */
  @Query("SELECT roomNumber FROM Room")
  List<String> selectAllRoomNumbers();
}
