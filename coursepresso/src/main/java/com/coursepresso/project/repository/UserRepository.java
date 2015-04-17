package com.coursepresso.project.repository;

import com.coursepresso.project.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Caleb Miller
 */
@Repository
public interface UserRepository extends CrudRepository<User, String> {

  /**
   * Custom FIND method retrieves a User record from the database with an
   * initialized list of Authority records mapped by a foreign key.
   *
   * @param username The username to match.
   * @return A User record as a User object.
   */
  @Query("SELECT u FROM User u LEFT JOIN FETCH u.authorityList WHERE u.username = (:username)")
  public User findByUsernameWithAuthorities(@Param("username") String username);

  /**
   * Custom FIND method retrieves all User records from the database with an
   * initialized list of Authority records mapped by a foreign key.
   *
   * @return All User records as a User List object.
   */
  @Query("SELECT u FROM User u LEFT JOIN FETCH u.authorityList")
  public List<User> findAllWithAuthorities();
}
