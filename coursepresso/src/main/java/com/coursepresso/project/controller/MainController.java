package com.coursepresso.project.controller;

import com.coursepresso.project.entity.CourseSection;
import com.coursepresso.project.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.inject.Inject;
import javax.swing.JOptionPane;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;

public class MainController {

  @FXML
  private Parent root;
  @FXML
  private BorderPane contentArea;
  @FXML
  private MenuItem logoutMnu;
  @FXML
  private MenuItem aboutMnu;
  @FXML
  private MenuItem closeMnu;

  @Inject
  private NewCourseSectionController newCourseSectionController;
  @Inject
  private LoginController loginController;
  @Inject
  private AdminMenuController adminMenuController;
  @Inject
  private UserMenuController userMenuController;
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

  @FXML
  private void logoutMnuClick(ActionEvent event) {
    loginController.logout();
  }

  @FXML
  private void aboutMnuClick(ActionEvent event) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Information Dialog");
    alert.setHeaderText("Look, an Information Dialog");
    alert.setContentText("I have a great message for you!");

    alert.showAndWait();
  }

  @FXML
  private void closeMnuClick(ActionEvent event) {
    int dialogResult = JOptionPane.showConfirmDialog(
            null, "Are you sure you want to logout and close Coursepresso?",
            "Warning", JOptionPane.YES_NO_OPTION
    );

    if (dialogResult == JOptionPane.YES_OPTION) {
      loginController.logout();
      // get a handle to the stage
      Stage stage = (Stage) Main.getScene().getWindow();
      // close the stage
      stage.close();

    } else {
      //Do Nothing
    }
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
    try {
      showAdminMenu();
    } catch (AccessDeniedException e) {
      showUserMenu();
    }
  }

  @PreAuthorize("hasRole('ADMIN')")
  public void showAdminMenu() {
    contentArea.setCenter(adminMenuController.getView());
  }

  public void showUserMenu() {
    contentArea.setCenter(userMenuController.getView());
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
