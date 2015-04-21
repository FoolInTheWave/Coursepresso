package com.coursepresso.project.service;

import java.util.List;

/**
 *
 * @author Caleb Miller
 */
public interface ExportService {
  
  List<String> getTableNames();
  
  String exportAppliances();
  
  String exportAuthorities();
  
  String exportCoursePrerequisites();
  
  String exportCourseSections(String term);
  
  String exportCourses();
  
  String exportDepartments();
  
  String exportMeetingDays(String term);
  
  String exportProfessors();
  
  String exportRooms();
  
  String exportTerms();
  
  String exportUsers();
}
