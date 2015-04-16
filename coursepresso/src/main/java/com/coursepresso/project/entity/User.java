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
@Table(name = "users")
public class User implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @Column(name = "username")
  private String username;
  @Basic(optional = false)
  @Column(name = "password")
  private String password;
  @Basic(optional = false)
  @Column(name = "firstname")
  private String firstname;
  @Basic(optional = false)
  @Column(name = "lastname")
  private String lastname;
  @Basic(optional = false)
  @Column(name = "email")
  private String email;
  @Basic(optional = false)
  @Column(name = "enabled")
  private boolean enabled;
  @Basic(optional = false)
  @Column(name = "updated_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedAt;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "username")
  private List<Authority> authorityList;

  public User() {
  }

  public User(String username) {
    this.username = username;
  }

  public User(String username, String password, String firstname, String lastname, 
      String email, boolean enabled, Date updatedAt) {
    this.username = username;
    this.password = password;
    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email;
    this.enabled = enabled;
    this.updatedAt = updatedAt;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  @XmlTransient
  public List<Authority> getAuthorityList() {
    return authorityList;
  }

  public void setAuthorityList(List<Authority> authorityList) {
    this.authorityList = authorityList;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (username != null ? username.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof User)) {
      return false;
    }
    User other = (User) object;
    if ((this.username == null && other.username != null) || 
        (this.username != null && !this.username.equals(other.username))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return username;
  }
  
}
