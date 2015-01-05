package com.coursepresso.project.repository;

import com.coursepresso.project.entity.MeetingTime;
import java.util.Date;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Caleb Miller
 */
@Repository
public interface MeetingTimeRepository extends CrudRepository<MeetingTime, Date> {
  
}
