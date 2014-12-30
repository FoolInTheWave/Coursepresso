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
  private NewCourseSectionController newCourseSectionPresenter;

  public Parent getView() {
    return root;
  }
  
  public void showNewCourseSection() {
    newCourseSectionPresenter.buildView();
    contentArea.setCenter(newCourseSectionPresenter.getView());
  }
}
