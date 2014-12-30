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
@Table(name = "room")
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
  @Column(name = "created_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;
  @Basic(optional = false)
  @Column(name = "updated_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedAt;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "roomNumber")
  private Set<Appliance> applianceSet;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "roomNumber")
  private Set<MeetingDay> meetingDaySet;

  public Room() {
  }

  public Room(String roomNumber) {
    this.roomNumber = roomNumber;
  }

  public Room(String roomNumber, String building, int capacity, String type,
          Date createdAt, Date updatedAt) {
    this.roomNumber = roomNumber;
    this.building = building;
    this.capacity = capacity;
    this.type = type;
    this.createdAt = createdAt;
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
  public Set<Appliance> getApplianceSet() {
    return applianceSet;
  }

  public void setApplianceSet(Set<Appliance> applianceSet) {
    this.applianceSet = applianceSet;
  }

  @XmlTransient
  public Set<MeetingDay> getMeetingDaySet() {
    return meetingDaySet;
  }

  public void setMeetingDaySet(Set<MeetingDay> meetingDaySet) {
    this.meetingDaySet = meetingDaySet;
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
    return "coursepresso.model.Room[ roomNumber=" + roomNumber + " ]";
  }

}
