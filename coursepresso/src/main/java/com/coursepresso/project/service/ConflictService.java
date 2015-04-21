package com.coursepresso.project.service;

import com.coursepresso.project.entity.Conflict;
import com.coursepresso.project.entity.Term;
import java.util.List;

/**
 *
 * @author Caleb Miller
 */
public interface ConflictService {
  
  List<Conflict> getConflicts(Term term);
}
