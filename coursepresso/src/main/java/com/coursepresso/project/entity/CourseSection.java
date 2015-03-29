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
@Table(name = "course_sections")
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
  private boolean available;
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
  @Column(name = "end_date")
  @Temporal(TemporalType.DATE)
  private Date endDate;
  @Basic(optional = false)
  @Column(name = "updated_at", insertable = false, updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedAt;
  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "courseSectionId")
  private List<MeetingDay> meetingDayList;
  @JoinColumn(name = "course_number", referencedColumnName = "course_number")
  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  private Course courseNumber;
  @JoinColumn(name = "term", referencedColumnName = "term")
  @ManyToOne(optional = false)
  private Term term;
  @JoinColumn(name = "department", referencedColumnName = "name")
  @ManyToOne(optional = false)
  private Department department;
  @JoinColumn(name = "professor_id", referencedColumnName = "id")
  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  private Professor professorId;

  public CourseSection() {
  }

  public CourseSection(Integer id) {
    this.id = id;
  }

  public CourseSection(Integer id, int sectionNumber, boolean available,
          int capacity, int seatsAvailable, String status, int studentCount,
          String type, Date startDate, Date endDate, Date updatedAt) {
    this.id = id;
    this.sectionNumber = sectionNumber;
    this.available = available;
    this.capacity = capacity;
    this.seatsAvailable = seatsAvailable;
    this.status = status;
    this.studentCount = studentCount;
    this.type = type;
    this.startDate = startDate;
    this.endDate = endDate;
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

  public boolean getAvailable() {
    return available;
  }

  public void setAvailable(boolean available) {
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

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  @XmlTransient
  public List<MeetingDay> getMeetingDayList() {
    return meetingDayList;
  }

  public void setMeetingDayList(List<MeetingDay> meetingDayList) {
    this.meetingDayList = meetingDayList;
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
  
  public Department getDepartment() {
    return department;
  }

  public void setDepartment(Department department) {
    this.department = department;
  }
  
  public Professor getProfessorId() {
    return professorId;
  }

  public void setProfessorId(Professor professorId) {
    this.professorId = professorId;
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
    return id.toString();
  }

}
