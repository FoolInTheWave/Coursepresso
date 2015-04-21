package com.coursepresso.project.entity;

import java.io.Serializable;

/**
 *
 * @author Steve Foco
 */
public class Conflict implements Serializable {

  private static final long serialVersionUID = 1L;
  private CourseSection courseSection;
  private String reason;

  public Conflict(CourseSection section, String reason) {
    this.courseSection = section;
    this.reason = reason;
  }

  public CourseSection getCourseSection() {
    return courseSection;
  }

  public void setCourseSection(CourseSection section) {
    this.courseSection = section;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (courseSection != null ? courseSection.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Conflict)) {
      return false;
    }
    Conflict other = (Conflict) object;
    if ((this.courseSection == null && other.courseSection != null)
        || (this.courseSection != null && !this.courseSection.equals(other.courseSection))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return courseSection.getId() + " " + reason;
  }
}
