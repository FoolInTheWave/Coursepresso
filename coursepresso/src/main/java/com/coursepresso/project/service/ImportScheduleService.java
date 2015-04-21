/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coursepresso.project.service;

import com.coursepresso.project.entity.Term;

/**
 *
 * @author Steve
 */
public interface ImportScheduleService {
  
  void importSchedule(Term term, String line);
}
