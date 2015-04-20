package com.coursepresso.project.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javax.inject.Inject;

/**
 * FXML Controller class
 *
 * @author steev_000
 */
public class AdminMenuController implements Initializable {

  @FXML
  private Node root;
  @FXML
  private Button manageSectionsButton;
  @FXML
  private Button exportDataButton;
  @FXML
  private Button displayConflictsButton;
  @FXML
  private Button newScheduleButton;
  @FXML
  private Button userManagementButton;
  @FXML
  private Label displayConflictsLabel;
  @FXML
  private Label searchCoursesLabel;
  @FXML
  private Label newCourseLabel;
  @FXML
  private Label newScheduleLabel;
  @FXML
  private Label headerLabel;
  
  @Inject
  private MainController mainController;
  @Inject
  private ScheduleSelectionController scheduleSelectionController;
  
  public Node getView() {
    return root;
  }

  /**
   * Initializes the controller class.
   *
   * @param url
   * @param rb
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }

  @FXML
  private void exportDataButtonClick(ActionEvent event) {
    mainController.showExportData();
  }

  @FXML
  private void displayConflictsButtonClick(ActionEvent event) {
    scheduleSelectionController.buildView();
    mainController.showScheduleSelection();
  }

  @FXML
  private void manageSectionsButtonClick(ActionEvent event) {
    mainController.showCourseSearch();
  }

  @FXML
  private void displayNewScheduleButtonClick(ActionEvent event) {
    mainController.showNewSchedule();
  }

  @FXML
  private void userManagementButtonClick(ActionEvent event) {
    mainController.showViewUsers();
  }

}
