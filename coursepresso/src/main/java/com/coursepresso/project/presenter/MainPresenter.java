package com.coursepresso.project.presenter;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import javax.inject.Inject;

public class MainPresenter {

  @FXML
  private Parent root;
  @FXML
  private BorderPane contentArea;

  @Inject
  private NewCourseSectionPresenter newCourseSectionPresenter;

  public Parent getView() {
    return root;
  }
  
  public void showNewCourseSection() {
    newCourseSectionPresenter.buildView();
    contentArea.setCenter(newCourseSectionPresenter.getView());
  }
}
