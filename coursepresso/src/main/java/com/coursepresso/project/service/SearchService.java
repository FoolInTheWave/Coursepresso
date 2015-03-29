package com.coursepresso.project.service;

import com.coursepresso.project.entity.CourseSection;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Caleb
 */
public interface SearchService {
  
  List<CourseSection> searchSections(Map<String, Object> params);
  
}
