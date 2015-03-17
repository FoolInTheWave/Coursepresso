package com.coursepresso.project.service;

/**
 *
 * @author Caleb Miller
 */
public interface SecurityService {

  void login(String userName, String password);
  
  void logout();
}
