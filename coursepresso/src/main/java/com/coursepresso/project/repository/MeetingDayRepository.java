package com.coursepresso.project.repository;

import com.coursepresso.project.entity.MeetingDay;
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
public interface MeetingDayRepository extends CrudRepository<MeetingDay, Integer> {

  @Modifying
  @Query("delete from MeetingDay d where d.id = (:id)")
  @Override
  void delete(@Param("id") Integer id);
}
