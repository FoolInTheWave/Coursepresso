package com.coursepresso.project.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
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
  @Column(name = "created_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;
  @Basic(optional = false)
  @Column(name = "updated_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedAt;
  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "courseNumber")
  private Set<CoursePrerequisite> coursePrerequisiteSet;
  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "prerequisite")
  private Set<CoursePrerequisite> coursePrerequisiteSet1;
  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "courseNumber")
  private Set<CourseSection> courseSectionSet;
  @JoinColumn(name = "department", referencedColumnName = "name")
  @ManyToOne(optional = false)
  private Department department;

  public Course() {
  }

  public Course(String courseNumber) {
    this.courseNumber = courseNumber;
  }

  public Course(String courseNumber, String title, int credits, 
          String description, String academicLevel, Date createdAt, 
          Date updatedAt) {
    this.courseNumber = courseNumber;
    this.title = title;
    this.credits = credits;
    this.description = description;
    this.academicLevel = academicLevel;
    this.createdAt = createdAt;
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

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  @XmlTransient
  public Set<CoursePrerequisite> getCoursePrerequisiteSet() {
    return coursePrerequisiteSet;
  }

  public void setCoursePrerequisiteSet(Set<CoursePrerequisite> coursePrerequisiteSet) {
    this.coursePrerequisiteSet = coursePrerequisiteSet;
  }

  @XmlTransient
  public Set<CoursePrerequisite> getCoursePrerequisiteSet1() {
    return coursePrerequisiteSet1;
  }

  public void setCoursePrerequisiteSet1(Set<CoursePrerequisite> coursePrerequisiteSet1) {
    this.coursePrerequisiteSet1 = coursePrerequisiteSet1;
  }

  @XmlTransient
  public Set<CourseSection> getCourseSectionSet() {
    return courseSectionSet;
  }

  public void setCourseSectionSet(Set<CourseSection> courseSectionSet) {
    this.courseSectionSet = courseSectionSet;
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
    return "coursepresso.model.Course[ courseNumber=" + courseNumber + " ]";
  }

}
