package com.coursepresso.project.service;

import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author Caleb Miller
 */
public interface SecurityService {

  void login(String userName, String password);
  
  void logout();
  
  void createUser(UserDetails user);
}
