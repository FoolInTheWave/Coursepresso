package com.coursepresso.project.entity;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Steve Foco
 */
public class Conflict {
  private CourseSection section;
  private final SimpleStringProperty reason;
  
  public Conflict(CourseSection section, String reason) {
    this.section = section;
    this.reason = new SimpleStringProperty("Conflicts with line #: " + reason);
  }
  
  public CourseSection getSection() {
    return section;
  }
  
  public String getReason() {
    return reason.get();
  }
  
  public void setCourse1(CourseSection section1) {
    this.section = section1;
  }
  
  public void setReason(String reason) {
    this.reason.set("Conflicts with line #: " + reason);
  }
  
  @Override
  public String toString() {
    return section.getId() + " " + section.getCourse().getTitle()
            + " " + section.getSectionNumber() + " " + reason;
  }
}
