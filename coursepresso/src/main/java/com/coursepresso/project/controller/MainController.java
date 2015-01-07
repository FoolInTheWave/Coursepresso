package com.coursepresso.project.controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import javax.inject.Inject;

public class MainController {

  @FXML
  private Parent root;
  @FXML
  private BorderPane contentArea;

  @Inject
  private NewCourseSectionController newCourseSectionController;
  @Inject
  private LoginController loginController;

  public Parent getView() {
    return root;
  }
  
  public void showNewCourseSection() {
    newCourseSectionController.buildView();
    contentArea.setCenter(newCourseSectionController.getView());
  }
  
  public void showLogin() {
    contentArea.setCenter(loginController.getView());
  }
}
