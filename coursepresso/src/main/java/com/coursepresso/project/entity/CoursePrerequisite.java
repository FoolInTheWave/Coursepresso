package com.coursepresso.project.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author Caleb Miller
 */
@Entity
@Table(name = "course_prerequisites")
public class CoursePrerequisite implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Basic(optional = false)
  @Column(name = "id")
  private Integer id;
  @Basic(optional = false)
  @Column(name = "updated_at", insertable = false, updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedAt;
  @JoinColumn(name = "course_number", referencedColumnName = "course_number")
  @ManyToOne(optional = false)
  private Course course;
  @JoinColumn(name = "prerequisite", referencedColumnName = "course_number")
  @ManyToOne(optional = false)
  private Course prerequisite;

  public CoursePrerequisite() {
  }

  public CoursePrerequisite(Integer id) {
    this.id = id;
  }

  public CoursePrerequisite(Integer id, Date updatedAt) {
    this.id = id;
    this.updatedAt = updatedAt;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  public Course getPrerequisite() {
    return prerequisite;
  }

  public void setPrerequisite(Course prerequisite) {
    this.prerequisite = prerequisite;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof CoursePrerequisite)) {
      return false;
    }
    CoursePrerequisite other = (CoursePrerequisite) object;
    if ((this.id == null && other.id != null) || 
        (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return id.toString();
  }

}
