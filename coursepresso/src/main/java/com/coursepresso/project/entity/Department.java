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
@Table(name = "departments")
public class Department implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @Column(name = "name")
  private String name;
  @Basic(optional = false)
  @Column(name = "updated_at", insertable = false, updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedAt;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "department")
  private List<Professor> professorList;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "department")
  private List<Course> courseList;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "department")
  private List<CourseSection> courseSectionList;

  public Department() {
  }

  public Department(String name) {
    this.name = name;
  }

  public Department(String name, Date updatedAt) {
    this.name = name;
    this.updatedAt = updatedAt;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  @XmlTransient
  public List<Professor> getProfessorList() {
    return professorList;
  }

  public void setProfessorList(List<Professor> professorList) {
    this.professorList = professorList;
  }

  @XmlTransient
  public List<Course> getCourseList() {
    return courseList;
  }

  public void setCourseList(List<Course> courseList) {
    this.courseList = courseList;
  }
  
  @XmlTransient
  public List<CourseSection> getCourseSectionList() {
    return courseSectionList;
  }

  public void setCoursSectioneList(List<CourseSection> courseSectionList) {
    this.courseSectionList = courseSectionList;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (name != null ? name.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Department)) {
      return false;
    }
    Department other = (Department) object;
    if ((this.name == null && other.name != null) || 
        (this.name != null && !this.name.equals(other.name))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return name;
  }

}
