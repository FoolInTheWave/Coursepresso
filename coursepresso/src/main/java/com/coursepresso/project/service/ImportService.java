package com.coursepresso.project.service;

import com.coursepresso.project.entity.*;
import java.util.List;

/**
 *
 * @author Caleb Miller
 */
public interface ImportService {

  void importAppliances(List<Appliance> appliances);

  void importAuthorities(List<Authority> authorities);

  void importCourses(List<Course> courses);

  void importCoursePrerequisites(List<CoursePrerequisite> prerequisites);

  void importCourseSections(List<CourseSection> courseSections);

  void importMeetingDays(List<MeetingDay> meetingDays);

  void importProfessors(List<Professor> professors);

  void importUsers(List<User> users);
}
