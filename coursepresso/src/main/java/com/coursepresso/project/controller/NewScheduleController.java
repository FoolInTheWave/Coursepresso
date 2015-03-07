package com.coursepresso.project.controller;

import com.coursepresso.project.entity.Term;
import com.coursepresso.project.repository.TermRepository;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javax.inject.Inject;

/**
 * FXML Controller class
 *
 * @author steev_000
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
  private TermRepository termRepository;
  @Inject
  private MainController mainController;
  
  @FXML
  private void backButtonClick(ActionEvent event) {
    mainController.showMenu();
  }

  @FXML
  private void createScheduleButtonClick(ActionEvent event) {
    Term term = new Term();
    String year = yearCombo.getValue();
    
    year = year.substring(year.length() - 2);
    String termName = year + "/" + semesterCombo.getValue();
    
    term.setTerm(termName);
    
    termRepository.save(term);
    
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Term Saved");
    alert.setHeaderText(null);
    alert.setContentText("The term has been saved successfully!");
    alert.showAndWait();
    
    mainController.showMenu();
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
    ObservableList<String> semesters = FXCollections.observableArrayList(
        "FA", "WI", "SP", "SU"
    );
    semesterCombo.setItems(semesters);
    semesterCombo.setVisibleRowCount(4);

    // Build type combo box
    ObservableList<String> years = FXCollections.observableArrayList(
        "2015", "2016", "2017", "2018", "2019", "2020"
    );
    yearCombo.setItems(years);
    yearCombo.setVisibleRowCount(4);
  }
}
