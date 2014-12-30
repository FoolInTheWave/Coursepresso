package com.coursepresso.project.repository;

import com.coursepresso.project.entity.MeetingDay;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Caleb Miller
 */
@Repository
public interface MeetingDayRepository extends CrudRepository<MeetingDay, Integer> {
  
}
