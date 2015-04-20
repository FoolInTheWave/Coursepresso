package com.coursepresso.project.service;

import java.util.List;

/**
 *
 * @author Caleb Miller
 */
public interface ExportService {
  
  List<String> getTableNames();
  
  String exportCourseSections(String term);
}
