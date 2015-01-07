package com.coursepresso.project.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author Caleb Miller
 */
@Entity
@Table(name = "course_professor")
public class CourseProfessor implements Serializable {

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
  @JoinColumn(name = "course_section_id", referencedColumnName = "id")
  @ManyToOne(optional = false)
  private CourseSection courseSectionId;
  @JoinColumn(name = "professor_id", referencedColumnName = "id")
  @ManyToOne(optional = false)
  private Professor professorId;

  public CourseProfessor() {
  }

  public CourseProfessor(Integer id) {
    this.id = id;
  }

  public CourseProfessor(Integer id, Date updatedAt) {
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

  public CourseSection getCourseSectionId() {
    return courseSectionId;
  }

  public void setCourseSectionId(CourseSection courseSectionId) {
    this.courseSectionId = courseSectionId;
  }

  public Professor getProfessorId() {
    return professorId;
  }

  public void setProfessorId(Professor professorId) {
    this.professorId = professorId;
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
    if (!(object instanceof CourseProfessor)) {
      return false;
    }
    CourseProfessor other = (CourseProfessor) object;
    if ((this.id == null && other.id != null) || 
        (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "coursepresso.model.CourseProfessor[ id=" + id + " ]";
  }

}
