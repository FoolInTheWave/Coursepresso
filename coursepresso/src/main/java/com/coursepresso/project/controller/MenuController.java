/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coursepresso.project.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javax.inject.Inject;

/**
 * FXML Controller class
 *
 * @author steev_000
 */
public class MenuController implements Initializable {

  @FXML
  private Node root;
  @FXML
  private Button searchCoursesButton;
  @FXML
  private Button newCourseButton;
  @FXML
  private Button displayConflictsButton;
  @FXML
  private Button newScheduleButton;
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
  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
      // TODO
  }    
    
  public Node getView() {
    return root;
  }
  
  @FXML
  private void newCourseButtonClick(ActionEvent event) {
    mainController.showNewCourseSection();
  }
    
}
