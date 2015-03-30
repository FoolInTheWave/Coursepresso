/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coursepresso.project.entity;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Steve
 */
public class Conflict {
  private final SimpleStringProperty course;
  private final SimpleStringProperty sectionNum;
  private final SimpleStringProperty lineNo;
  private final SimpleStringProperty reason;
  
  public Conflict(String course, String sectionNum, String lineNo, String reason) {
    this.course = new SimpleStringProperty(course);
    this.sectionNum = new SimpleStringProperty(sectionNum);
    this.lineNo = new SimpleStringProperty(lineNo);
    this.reason = new SimpleStringProperty("Conflicts with line #: " + reason);
  }
  
  public String getCourse() {
    return course.get();
  }
  
  public String getSectionNum() {
    return sectionNum.get();
  }
  
  public String getlineNo() {
    return lineNo.get();
  }
  
  public String getreason() {
    return reason.get();
  }
  
  public void setCourse(String course) {
    this.course.set(course);
  }
  
  public void setSectionNum(String sectionNum) {
    this.sectionNum.set(sectionNum);
  }
  
  public void setlineNo(String lineNo) {
    this.lineNo.set(lineNo);
  }
  
  public void setReason(String reason) {
    this.reason.set("Conflicts with line #: " + reason);
  }
  
  @Override
  public String toString() {
    return lineNo + " " + course + " " + sectionNum + " " + reason;
  }
}
