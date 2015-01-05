package com.coursepresso.project.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Caleb Miller
 */
@Entity
@Table(name = "term")
public class Term implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @Column(name = "term")
  private String term;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "term")
  private List<CourseSection> courseSectionList;

  public Term() {
  }

  public Term(String term) {
    this.term = term;
  }

  public String getTerm() {
    return term;
  }

  public void setTerm(String term) {
    this.term = term;
  }

  @XmlTransient
  public List<CourseSection> getCourseSectionList() {
    return courseSectionList;
  }

  public void setCourseSectionSet(List<CourseSection> courseSectionList) {
    this.courseSectionList = courseSectionList;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (term != null ? term.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Term)) {
      return false;
    }
    Term other = (Term) object;
    if ((this.term == null && other.term != null) || 
        (this.term != null && !this.term.equals(other.term))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return term;
  }

}
