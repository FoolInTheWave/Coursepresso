package com.coursepresso.project.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author Caleb Miller
 */
@Entity
@Table(name = "user_group")
public class UserGroup implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Basic(optional = false)
  @Column(name = "id")
  private Integer id;
  @Basic(optional = false)
  @Column(name = "created_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;
  @Basic(optional = false)
  @Column(name = "updated_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedAt;
  @JoinColumn(name = "access_group_id", referencedColumnName = "id")
  @ManyToOne(optional = false)
  private AccessGroup accessGroupId;
  @JoinColumn(name = "access_user_id", referencedColumnName = "id")
  @ManyToOne(optional = false)
  private AccessUser accessUserId;

  public UserGroup() {
  }

  public UserGroup(Integer id) {
    this.id = id;
  }

  public UserGroup(Integer id, Date createdAt, Date updatedAt) {
    this.id = id;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
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

  public AccessGroup getAccessGroupId() {
    return accessGroupId;
  }

  public void setAccessGroupId(AccessGroup accessGroupId) {
    this.accessGroupId = accessGroupId;
  }

  public AccessUser getAccessUserId() {
    return accessUserId;
  }

  public void setAccessUserId(AccessUser accessUserId) {
    this.accessUserId = accessUserId;
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
    if (!(object instanceof UserGroup)) {
      return false;
    }
    UserGroup other = (UserGroup) object;
    if ((this.id == null && other.id != null) || 
        (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "coursepresso.model.UserGroup[ id=" + id + " ]";
  }

}
