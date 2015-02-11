/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coursepresso.project.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javax.inject.Inject;

/**
 * FXML Controller class
 *
<<<<<<< HEAD
 * @author Caleb
=======
 * @author steev_000
>>>>>>> origin/master
 */
public class NewScheduleController implements Initializable {

  @FXML
  private Node root;
  @FXML
  private ComboBox<String> semesterCombo;
  @FXML
  private Button createScheduleButton;
  @FXML
  private Button backButton;
  @FXML
  private ComboBox<String> yearCombo;
  
  @Inject
  private MainController mainController;
  
  @FXML
  private void backButtonClick(ActionEvent event) {
    mainController.showMenu();
  }

  @FXML
  private void createScheduleButtonClick(ActionEvent event) {

  }

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
  
  public void buildView() {
    // Build type combo box
    ObservableList<String> levels = FXCollections.observableArrayList(
        "FA", "WI", "SP", "SU"
    );
    semesterCombo.setItems(levels);
    semesterCombo.setVisibleRowCount(4);

    // Build type combo box
    ObservableList<String> credits = FXCollections.observableArrayList(
        "2015", "2016", "2017", "2018", "2019", "2020"
    );
    yearCombo.setItems(credits);
    yearCombo.setVisibleRowCount(4);
  }
}
