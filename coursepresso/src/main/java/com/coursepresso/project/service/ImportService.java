package com.coursepresso.project.service;

import com.coursepresso.project.entity.*;
import java.util.List;

/**
 *
 * @author Caleb Miller
 */
public interface ImportService {

  void importCourseSections(List<CourseSection> courseSections);
}
