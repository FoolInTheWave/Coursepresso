package com.coursepresso.project.helper;

/**
 *
 * @author Caleb Miller
 */
public enum Day {

  M("M", 2), T("T", 3), W("W", 4), TH("TH", 5), F("F", 6), S("S", 7), 
  SU("SU", 1);

  private String name;
  private int weight;

  Day(String name, int weight) {
    this.name = name;
    this.weight = weight;
  }

  public String getName() {
    return this.name;
  }
  
  public int getWeight() {
    return this.weight;
  }
}