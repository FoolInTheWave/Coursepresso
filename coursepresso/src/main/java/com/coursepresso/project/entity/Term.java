package com.coursepresso.project.entity;

import java.io.Serializable;
import java.util.Set;
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
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Basic(optional = false)
  @Column(name = "id")
  private Integer id;
  @Basic(optional = false)
  @Column(name = "term")
  private String term;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "term")
  private Set<CourseSection> courseSectionSet;

  public Term() {
  }

  public Term(Integer id) {
    this.id = id;
  }

  public Term(Integer id, String term) {
    this.id = id;
    this.term = term;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTerm() {
    return term;
  }

  public void setTerm(String term) {
    this.term = term;
  }

  @XmlTransient
  public Set<CourseSection> getCourseSectionSet() {
    return courseSectionSet;
  }

  public void setCourseSectionSet(Set<CourseSection> courseSectionSet) {
    this.courseSectionSet = courseSectionSet;
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
    if (!(object instanceof Term)) {
      return false;
    }
    Term other = (Term) object;
    if ((this.id == null && other.id != null) || 
        (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "coursepresso.model.Term[ id=" + id + " ]";
  }

}
