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
@Table(name = "department")
public class Department implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Basic(optional = false)
  @Column(name = "id")
  private Integer id;
  @Basic(optional = false)
  @Column(name = "name")
  private String name;
  @Basic(optional = false)
  @Column(name = "created_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;
  @Basic(optional = false)
  @Column(name = "updated_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedAt;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "department")
  private Set<AccessUser> accessUserSet;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "department")
  private Set<Professor> professorSet;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "department")
  private Set<Course> courseSet;

  public Department() {
  }

  public Department(Integer id) {
    this.id = id;
  }

  public Department(Integer id, String name, Date createdAt, Date updatedAt) {
    this.id = id;
    this.name = name;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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
  public Set<AccessUser> getAccessUserSet() {
    return accessUserSet;
  }

  public void setAccessUserSet(Set<AccessUser> accessUserSet) {
    this.accessUserSet = accessUserSet;
  }

  @XmlTransient
  public Set<Professor> getProfessorSet() {
    return professorSet;
  }

  public void setProfessorSet(Set<Professor> professorSet) {
    this.professorSet = professorSet;
  }

  @XmlTransient
  public Set<Course> getCourseSet() {
    return courseSet;
  }

  public void setCourseSet(Set<Course> courseSet) {
    this.courseSet = courseSet;
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
    if (!(object instanceof Department)) {
      return false;
    }
    Department other = (Department) object;
    if ((this.id == null && other.id != null) || 
        (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "coursepresso.model.Department[ id=" + id + " ]";
  }

}
