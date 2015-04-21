package com.coursepresso.project.service;

import com.coursepresso.project.entity.Term;

/**
 *
 * @author Steve
 */
public interface ImportScheduleService {
  
  void importSchedule(Term term, String line);
}
