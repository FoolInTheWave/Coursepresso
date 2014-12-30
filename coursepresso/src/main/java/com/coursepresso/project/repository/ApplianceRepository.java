package com.coursepresso.project.repository;

import com.coursepresso.project.entity.Appliance;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Caleb Miller
 */
@Repository
public interface ApplianceRepository extends CrudRepository<Appliance, Integer> {
  
}
