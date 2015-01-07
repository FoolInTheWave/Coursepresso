package com.coursepresso.project.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author Caleb Miller
 */
@Entity
@Table(name = "appliance")
public class Appliance implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Basic(optional = false)
  @Column(name = "id")
  private Integer id;
  @Basic(optional = false)
  @Column(name = "type")
  private String type;
  @Basic(optional = false)
  @Column(name = "updated_at", insertable = false, updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedAt;
  @JoinColumn(name = "room_number", referencedColumnName = "room_number")
  @ManyToOne(optional = false)
  private Room roomNumber;

  public Appliance() {
  }

  public Appliance(Integer id) {
    this.id = id;
  }

  public Appliance(Integer id, String type, Date updatedAt) {
    this.id = id;
    this.type = type;
    this.updatedAt = updatedAt;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
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

  public Room getRoomNumber() {
    return roomNumber;
  }

  public void setRoomNumber(Room roomNumber) {
    this.roomNumber = roomNumber;
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
    if (!(object instanceof Appliance)) {
      return false;
    }
    Appliance other = (Appliance) object;
    if ((this.id == null && other.id != null) || 
        (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "coursepresso.model.Appliance[ id=" + id + " ]";
  }

}
