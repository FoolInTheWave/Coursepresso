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
 * @author Steve Foco
 */
public class UserMenuController implements Initializable {

  @FXML
  private Node root;
  @FXML
  private Button searchCoursesButton;
  @FXML
  private Label headerLabel;
  @FXML
  private Button displayConflictsButton;
  @FXML
  private Label displayConflictsLabel;
  @FXML
  private Label searchCoursesLabel;
  @FXML
  private Label newCourseLabel;
  @FXML
  private Button newSectionButton;

  @Inject
  private MainController mainController;

  public Node getView() {
    return root;
  }

  /**
   * Initializes the controller class.
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
  void newSectionButtonClick(ActionEvent event) {
    mainController.showNewCourseSection();
  }

  @FXML
  void displayConflictsButtonClick(ActionEvent event) {
    mainController.showConflict();
  }

}
