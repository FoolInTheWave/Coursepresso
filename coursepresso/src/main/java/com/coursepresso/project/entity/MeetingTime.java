package com.coursepresso.project.entity;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author Caleb Miller
 */
@Entity
@Table(name = "meeting_time")
public class MeetingTime implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @Column(name = "meeting_time")
  @Temporal(TemporalType.TIME)
  private Date meetingTime;

  public MeetingTime() {
  }

  public MeetingTime(Date meetingTime) {
    this.meetingTime = meetingTime;
  }

  public Date getMeetingTime() {
    return meetingTime;
  }

  public void setMeetingTime(Date meetingTime) {
    this.meetingTime = meetingTime;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (meetingTime != null ? meetingTime.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof MeetingTime)) {
      return false;
    }
    MeetingTime other = (MeetingTime) object;
    if ((this.meetingTime == null && other.meetingTime != null) || 
        (this.meetingTime != null && !this.meetingTime.equals(other.meetingTime))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
    String theMeetingTime = dateFormat.format(meetingTime);
    return theMeetingTime;
  }

}
