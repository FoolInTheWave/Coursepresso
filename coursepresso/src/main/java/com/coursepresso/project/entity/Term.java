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
@Table(name = "terms")
public class Term implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @Column(name = "term")
  private String term;
  @Basic(optional = false)
  @Column(name = "status")
  private String status;
  @Basic(optional = false)
  @Column(name = "updated_at", insertable = false, updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedAt;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "term")
  private List<CourseSection> courseSectionList;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "term")
  private List<MeetingDay> meetingDayList;

  public Term() {
  }

  public Term(String term, String status, Date updatedAt) {
    this.term = term;
    this.status = status;
    this.updatedAt = updatedAt;
  }

  public String getTerm() {
    return term;
  }

  public void setTerm(String term) {
    this.term = term;
  }
  
  public String getStatus() {
    return status;
  }
  
  public void setStatus(String status) {
    this.status = status;
  }
  
  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  @XmlTransient
  public List<CourseSection> getCourseSectionList() {
    return courseSectionList;
  }

  public void setCourseSectionList(List<CourseSection> courseSectionList) {
    this.courseSectionList = courseSectionList;
  }
  
  @XmlTransient
  public List<MeetingDay> getMeetingDayList() {
    return meetingDayList;
  }

  public void setMeetingDayList(List<MeetingDay> meetingDayList) {
    this.meetingDayList = meetingDayList;
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
