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
@Table(name = "rooms")
public class Room implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @Column(name = "room_number")
  private String roomNumber;
  @Basic(optional = false)
  @Column(name = "building")
  private String building;
  @Basic(optional = false)
  @Column(name = "capacity")
  private int capacity;
  @Basic(optional = false)
  @Column(name = "type")
  private String type;
  @Basic(optional = false)
  @Column(name = "updated_at", insertable = false, updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedAt;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "room")
  private List<Appliance> applianceList;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "room")
  private List<MeetingDay> meetingDayList;

  public Room() {
  }

  public Room(String roomNumber) {
    this.roomNumber = roomNumber;
  }

  public Room(String roomNumber, String building, int capacity, String type,
          Date updatedAt) {
    this.roomNumber = roomNumber;
    this.building = building;
    this.capacity = capacity;
    this.type = type;
    this.updatedAt = updatedAt;
  }

  public String getRoomNumber() {
    return roomNumber;
  }

  public void setRoomNumber(String roomNumber) {
    this.roomNumber = roomNumber;
  }

  public String getBuilding() {
    return building;
  }

  public void setBuilding(String building) {
    this.building = building;
  }

  public int getCapacity() {
    return capacity;
  }

  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  @XmlTransient
  public List<Appliance> getApplianceList() {
    return applianceList;
  }

  public void setApplianceSet(List<Appliance> applianceList) {
    this.applianceList = applianceList;
  }

  @XmlTransient
  public List<MeetingDay> getMeetingDayList() {
    return meetingDayList;
  }

  public void setMeetingDaySet(List<MeetingDay> meetingDayList) {
    this.meetingDayList = meetingDayList;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (roomNumber != null ? roomNumber.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Room)) {
      return false;
    }
    Room other = (Room) object;
    if ((this.roomNumber == null && other.roomNumber != null) || 
        (this.roomNumber != null && !this.roomNumber.equals(other.roomNumber))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return roomNumber;
  }

}
