package com.coursepresso.project.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author Caleb Miller
 */
@Entity
@Table(name = "meeting_day")
public class MeetingDay implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "id")
  private Integer id;
  @Basic(optional = false)
  @Column(name = "day")
  private String day;
  @Basic(optional = false)
  @Column(name = "start_time")
  @Temporal(TemporalType.TIME)
  private Date startTime;
  @Basic(optional = false)
  @Column(name = "end_time")
  @Temporal(TemporalType.TIME)
  private Date endTime;
  @Basic(optional = false)
  @Column(name = "updated_at", insertable = false, updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedAt;
  @JoinColumn(name = "course_section_id", referencedColumnName = "id")
  @ManyToOne(optional = false)
  private CourseSection courseSectionId;
  @JoinColumn(name = "room_number", referencedColumnName = "room_number")
  @ManyToOne(optional = false)
  private Room roomNumber;
  @JoinColumn(name = "term", referencedColumnName = "term")
  @ManyToOne(optional = false)
  private Term term;

  public MeetingDay() {
  }

  public MeetingDay(Integer id) {
    this.id = id;
  }

  public MeetingDay(Integer id, String day, Date startTime, Date endTime,
          Date updatedAt) {
    this.id = id;
    this.day = day;
    this.startTime = startTime;
    this.endTime = endTime;
    this.updatedAt = updatedAt;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getDay() {
    return day;
  }

  public void setDay(String day) {
    this.day = day;
  }

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
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

  public Room getRoomNumber() {
    return roomNumber;
  }

  public void setRoomNumber(Room roomNumber) {
    this.roomNumber = roomNumber;
  }
  
  public Term getTerm() {
    return term;
  }

  public void setTerm(Term term) {
    this.term = term;
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
    if (!(object instanceof MeetingDay)) {
      return false;
    }
    MeetingDay other = (MeetingDay) object;
    if ((this.id == null && other.id != null) || 
        (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "coursepresso.model.MeetingDay[ id=" + id + " ]";
  }

}
