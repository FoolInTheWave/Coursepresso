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
@Table(name = "course_section")
public class CourseSection implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Basic(optional = false)
  @Column(name = "id")
  private Integer id;
  @Basic(optional = false)
  @Column(name = "section_number")
  private int sectionNumber;
  @Basic(optional = false)
  @Column(name = "available")
  private int available;
  @Basic(optional = false)
  @Column(name = "capacity")
  private int capacity;
  @Basic(optional = false)
  @Column(name = "seats_available")
  private int seatsAvailable;
  @Basic(optional = false)
  @Column(name = "status")
  private String status;
  @Basic(optional = false)
  @Column(name = "student_count")
  private int studentCount;
  @Basic(optional = false)
  @Column(name = "type", columnDefinition = "CHAR")
  private String type;
  @Basic(optional = false)
  @Column(name = "start_date")
  @Temporal(TemporalType.DATE)
  private Date startDate;
  @Basic(optional = false)
  @Column(name = "last_date")
  @Temporal(TemporalType.DATE)
  private Date lastDate;
  @Basic(optional = false)
  @Column(name = "created_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;
  @Basic(optional = false)
  @Column(name = "updated_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedAt;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "courseSectionId")
  private Set<CourseProfessor> courseProfessorSet;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "courseSectionId")
  private Set<MeetingDay> meetingDaySet;
  @JoinColumn(name = "course_number", referencedColumnName = "course_number")
  @ManyToOne(optional = false)
  private Course courseNumber;
  @JoinColumn(name = "term", referencedColumnName = "term")
  @ManyToOne(optional = false)
  private Term term;

  public CourseSection() {
  }

  public CourseSection(Integer id) {
    this.id = id;
  }

  public CourseSection(Integer id, int sectionNumber, int available,
          int capacity, int seatsAvailable, String status, int studentCount,
          String type, Date startDate, Date lastDate, Date createdAt,
          Date updatedAt) {
    this.id = id;
    this.sectionNumber = sectionNumber;
    this.available = available;
    this.capacity = capacity;
    this.seatsAvailable = seatsAvailable;
    this.status = status;
    this.studentCount = studentCount;
    this.type = type;
    this.startDate = startDate;
    this.lastDate = lastDate;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public int getSectionNumber() {
    return sectionNumber;
  }

  public void setSectionNumber(int sectionNumber) {
    this.sectionNumber = sectionNumber;
  }

  public int getAvailable() {
    return available;
  }

  public void setAvailable(int available) {
    this.available = available;
  }

  public int getCapacity() {
    return capacity;
  }

  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }

  public int getSeatsAvailable() {
    return seatsAvailable;
  }

  public void setSeatsAvailable(int seatsAvailable) {
    this.seatsAvailable = seatsAvailable;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public int getStudentCount() {
    return studentCount;
  }

  public void setStudentCount(int studentCount) {
    this.studentCount = studentCount;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getLastDate() {
    return lastDate;
  }

  public void setLastDate(Date lastDate) {
    this.lastDate = lastDate;
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
  public Set<CourseProfessor> getCourseProfessorSet() {
    return courseProfessorSet;
  }

  public void setCourseProfessorSet(Set<CourseProfessor> courseProfessorSet) {
    this.courseProfessorSet = courseProfessorSet;
  }

  @XmlTransient
  public Set<MeetingDay> getMeetingDaySet() {
    return meetingDaySet;
  }

  public void setMeetingDaySet(Set<MeetingDay> meetingDaySet) {
    this.meetingDaySet = meetingDaySet;
  }

  public Course getCourseNumber() {
    return courseNumber;
  }

  public void setCourseNumber(Course courseNumber) {
    this.courseNumber = courseNumber;
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
    if (!(object instanceof CourseSection)) {
      return false;
    }
    CourseSection other = (CourseSection) object;
    if ((this.id == null && other.id != null) || 
        (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return String.format("CourseSection[id=%d, courseNumber='%s', sectionNumber=%d]", 
            id, courseNumber.getCourseNumber(), sectionNumber);
  }

}
