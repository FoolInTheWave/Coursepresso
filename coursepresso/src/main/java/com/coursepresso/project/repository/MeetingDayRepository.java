package com.coursepresso.project.repository;

import com.coursepresso.project.entity.MeetingDay;
import com.coursepresso.project.entity.Term;
import java.util.List;
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

  /**
   * Custom FIND method retrieves a MeetingDay record from the database that
   * matches the specified term with an initialized Room object.
   *
   * @param id The meeting day to match.
   * @param term The term to match.
   * @return A List of MeetingDay objects.
   */
  @Query("SELECT m FROM MeetingDay m LEFT JOIN FETCH m.room WHERE m.id = (:id) AND m.term = (:term)")
  public MeetingDay findOneByTermWithRoom(@Param("id") Integer id ,@Param("term") Term term);

  /**
   * Custom FIND method retrieves MeetingDay records from the database that
   * match the specified term with an initialized Room object.
   *
   * @param term The term to match.
   * @return A List of MeetingDay objects.
   */
  @Query("SELECT m FROM MeetingDay m LEFT JOIN FETCH m.room WHERE m.term = (:term)")
  public List<MeetingDay> findAllByTermWithRoom(@Param("term") Term term);

  @Modifying
  @Query("delete from MeetingDay d where d.id = (:id)")
  @Override
  void delete(@Param("id") Integer id);
}
