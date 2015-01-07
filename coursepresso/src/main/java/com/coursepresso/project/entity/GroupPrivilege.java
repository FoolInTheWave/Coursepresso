package com.coursepresso.project.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author Caleb Miller
 */
@Entity
@Table(name = "group_privilege")
public class GroupPrivilege implements Serializable {

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
  @JoinColumn(name = "access_group_id", referencedColumnName = "id")
  @ManyToOne(optional = false)
  private AccessGroup accessGroupId;
  @JoinColumn(name = "privilege_id", referencedColumnName = "id")
  @ManyToOne(optional = false)
  private Privilege privilegeId;

  public GroupPrivilege() {
  }

  public GroupPrivilege(Integer id) {
    this.id = id;
  }

  public GroupPrivilege(Integer id, Date updatedAt) {
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

  public AccessGroup getAccessGroupId() {
    return accessGroupId;
  }

  public void setAccessGroupId(AccessGroup accessGroupId) {
    this.accessGroupId = accessGroupId;
  }

  public Privilege getPrivilegeId() {
    return privilegeId;
  }

  public void setPrivilegeId(Privilege privilegeId) {
    this.privilegeId = privilegeId;
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
    if (!(object instanceof GroupPrivilege)) {
      return false;
    }
    GroupPrivilege other = (GroupPrivilege) object;
    if ((this.id == null && other.id != null) || 
        (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "coursepresso.model.GroupPrivilege[ id=" + id + " ]";
  }

}
