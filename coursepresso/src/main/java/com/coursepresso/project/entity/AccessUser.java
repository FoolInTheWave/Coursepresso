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
@Table(name = "access_user")
public class AccessUser implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Basic(optional = false)
  @Column(name = "id")
  private Integer id;
  @Basic(optional = false)
  @Column(name = "username")
  private String username;
  @Basic(optional = false)
  @Column(name = "email")
  private String email;
  @Basic(optional = false)
  @Column(name = "password")
  private String password;
  @Basic(optional = false)
  @Column(name = "first_name")
  private String firstName;
  @Basic(optional = false)
  @Column(name = "last_name")
  private String lastName;
  @Basic(optional = false)
  @Column(name = "updated_at", insertable = false, updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedAt;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "accessUserId")
  private List<UserGroup> userGroupList;
  @JoinColumn(name = "department", referencedColumnName = "name")
  @ManyToOne(optional = false)
  private Department department;

  public AccessUser() {
  }

  public AccessUser(Integer id) {
    this.id = id;
  }

  public AccessUser(Integer id, String username, String email, String password,
          String firstName, String lastName, Date updatedAt) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.updatedAt = updatedAt;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  @XmlTransient
  public List<UserGroup> getUserGroupList() {
    return userGroupList;
  }

  public void setUserGroupList(List<UserGroup> userGroupList) {
    this.userGroupList = userGroupList;
  }

  public Department getDepartment() {
    return department;
  }

  public void setDepartment(Department department) {
    this.department = department;
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
    if (!(object instanceof AccessUser)) {
      return false;
    }
    AccessUser other = (AccessUser) object;
    if ((this.id == null && other.id != null) || 
        (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "coursepresso.model.AccessUser[ id=" + id + " ]";
  }

}
