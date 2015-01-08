/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coursepresso.project.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javax.inject.Inject;

/**
 * FXML Controller class
 *
 * @author steev_000
 */
public class CourseSearchController implements Initializable {
  @FXML
  private Node root;
  @FXML
  private Label DepartmentLabel;
  @FXML
  private ComboBox<?> DepartmentCombo;
  
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
    
}
