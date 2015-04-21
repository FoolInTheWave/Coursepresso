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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
  private Button manageSchedulesButton;
  @FXML
  private Button userManagementButton;
  @FXML
  private Label headerLabel;

  @Inject
  private MainController mainController;
  @Inject
  private ScheduleSelectionController scheduleSelectionController;

  private static final Logger log = LoggerFactory.getLogger(
      AdminMenuController.class
  );

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
  void displayCourseSearchButtonClick(ActionEvent event) {
    mainController.showCourseSearch();
  }

  @FXML
  private void newCourseButtonClick(ActionEvent event) {
    mainController.showNewCourseSection("MENU");
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
  private void manageSchedulesButtonClick(ActionEvent event) {
    mainController.showViewSchedules();
  }

  @FXML
  private void userManagementButtonClick(ActionEvent event) {
    mainController.showViewUsers();
  }

  @FXML
  private void exportDataButtonClick(ActionEvent event) {
    mainController.showExportData();
  }

}
