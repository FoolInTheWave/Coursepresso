package com.coursepresso.project.repository;

import com.coursepresso.project.entity.Authority;
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
public interface AuthorityRepository extends CrudRepository<Authority, Integer> {

  @Modifying
  @Query("delete from Authority a where a.id = (:id)")
  @Override
  void delete(@Param("id") Integer id);
}
