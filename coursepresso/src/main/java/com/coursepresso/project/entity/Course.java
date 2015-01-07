package com.coursepresso.project.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Caleb Miller
 */
@Entity
@Table(name = "course")
public class Course implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @Column(name = "course_number")
  private String courseNumber;
  @Basic(optional = false)
  @Column(name = "title")
  private String title;
  @Basic(optional = false)
  @Column(name = "credits")
  private int credits;
  @Basic(optional = false)
  @Lob
  @Column(name = "description", columnDefinition="TEXT")
  private String description;
  @Basic(optional = false)
  @Column(name = "academic_level")
  private String academicLevel;
  @Basic(optional = false)
  @Column(name = "updated_at", insertable = false, updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedAt;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "courseNumber")
  private List<CoursePrerequisite> coursePrerequisiteList;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "prerequisite")
  private List<CoursePrerequisite> coursePrerequisiteList1;
  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "courseNumber")
  private List<CourseSection> courseSectionList;
  @JoinColumn(name = "department", referencedColumnName = "name")
  @ManyToOne(optional = false)
  private Department department;

  public Course() {
  }

  public Course(String courseNumber) {
    this.courseNumber = courseNumber;
  }

  public Course(String courseNumber, String title, int credits, 
          String description, String academicLevel, Date updatedAt) {
    this.courseNumber = courseNumber;
    this.title = title;
    this.credits = credits;
    this.description = description;
    this.academicLevel = academicLevel;
    this.updatedAt = updatedAt;
  }

  public String getCourseNumber() {
    return courseNumber;
  }

  public void setCourseNumber(String courseNumber) {
    this.courseNumber = courseNumber;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getCredits() {
    return credits;
  }

  public void setCredits(int credits) {
    this.credits = credits;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getAcademicLevel() {
    return academicLevel;
  }

  public void setAcademicLevel(String academicLevel) {
    this.academicLevel = academicLevel;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  @XmlTransient
  public List<CoursePrerequisite> getCoursePrerequisiteList() {
    return coursePrerequisiteList;
  }

  public void setCoursePrerequisiteList(List<CoursePrerequisite> coursePrerequisiteList) {
    this.coursePrerequisiteList = coursePrerequisiteList;
  }

  @XmlTransient
  public List<CoursePrerequisite> getCoursePrerequisiteList1() {
    return coursePrerequisiteList1;
  }

  public void setCoursePrerequisiteSet1(List<CoursePrerequisite> coursePrerequisiteList1) {
    this.coursePrerequisiteList1 = coursePrerequisiteList1;
  }

  @XmlTransient
  public List<CourseSection> getCourseSectionList() {
    return courseSectionList;
  }

  public void setCourseSectionSet(List<CourseSection> courseSectionList) {
    this.courseSectionList = courseSectionList;
  }

  public Department getDepartment() {
    return department;
  }

  public void setDepartment(Department department) {
    this.department = department;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (courseNumber != null ? courseNumber.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Course)) {
      return false;
    }
    Course other = (Course) object;
    if ((this.courseNumber == null && other.courseNumber != null) || 
        (this.courseNumber != null && !this.courseNumber.equals(other.courseNumber))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return courseNumber;
  }

}
