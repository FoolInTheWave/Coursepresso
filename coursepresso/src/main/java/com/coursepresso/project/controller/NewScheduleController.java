package com.coursepresso.project.controller;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javax.inject.Inject;

/**
 * FXML Controller class
 *
 * @author Caleb
 */
public class NewScheduleController implements Initializable {

  @FXML
  private Node root;

  @FXML
  private Button backButton;

  @FXML
  private Button submitButton;

  @FXML
  private ComboBox<String> semesterCombo;

  @FXML
  private ComboBox<String> yearCombo;

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

  public void buildView() {

    // Build type combo box
    ObservableList<String> semesters = FXCollections.observableArrayList(
        "FA", "WI", "SP", "SU"
    );
    semesterCombo.setItems(semesters);
    semesterCombo.setVisibleRowCount(4);

    // Build type combo box
    ObservableList<String> years = FXCollections.observableArrayList(
        "2015", "2016", "2017", "2018"
    );
    yearCombo.setItems(years);
    yearCombo.setVisibleRowCount(4);
  }

  @FXML
  private void submitButtonClick(ActionEvent event) {
    //TODO
  }

  @FXML
  private void backButtonClick(ActionEvent event) {
    mainController.showMenu();
  }

}
