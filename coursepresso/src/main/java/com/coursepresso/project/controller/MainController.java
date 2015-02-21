package com.coursepresso.project.controller;

import com.coursepresso.project.entity.CourseSection;
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
  @Inject
  private MenuController menuController;
  @Inject
  private ScheduleSelectionController scheduleSelectionController;
  @Inject
  private ConflictController conflictController;
  @Inject
  private CourseSearchController courseSearchController;
  @Inject
  private SearchResultsController searchResultsController;
  @Inject
  private NewScheduleController newScheduleController;
  @Inject
  private EditCourseSectionController editCourseSectionController;
  
  public Parent getView() {
    return root;
  }
  
  public void showNewCourseSection() {
    newCourseSectionController.buildView();
    contentArea.setCenter(newCourseSectionController.getView());
  }
  
  public void showEditCourseSection(CourseSection cs) {
    editCourseSectionController.buildView(cs);
    contentArea.setCenter(editCourseSectionController.getView());
  }
  
  public void showLogin() {
    contentArea.setCenter(loginController.getView());
  }
  
  public void showMenu() {
    contentArea.setCenter(menuController.getView());
  }

  public void showScheduleSelection() {
    contentArea.setCenter(scheduleSelectionController.getView());
  }
  
  public void showConflict() {
    contentArea.setCenter(conflictController.getView());
  }
  
  public void showCourseSearch() {
    courseSearchController.buildView();
    contentArea.setCenter(courseSearchController.getView());
  }
  
  public void showSearchResults() {
    searchResultsController.buildView();
    contentArea.setCenter(searchResultsController.getView());
  }
  
  public void showNewSchedule() {
    newScheduleController.buildView();
    contentArea.setCenter(newScheduleController.getView());
  }
}
