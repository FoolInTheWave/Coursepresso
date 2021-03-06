package com.coursepresso.project.controller;

import com.coursepresso.project.entity.Term;
import com.coursepresso.project.repository.TermRepository;
import com.google.common.collect.Lists;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FXML Controller class
 *
 * @author Caleb Miller
 */
public class ScheduleSelectionController implements Initializable {

  @FXML
  private Node root;
  @FXML
  private Label numberLabel;
  @FXML
  private TableView<Term> scheduleTable;
  @FXML
  private TableColumn<Term, String> termColumn;
  @FXML
  private TableColumn<Term, String> statusColumn;
  @FXML
  private Button backButton;
  @FXML
  private Button submitButton;

  @Inject
  private MainController mainController;
  @Inject
  private ConflictController conflictController;
  @Inject
  private TermRepository termRepository;

  private ObservableList<Term> terms;

  private static final Logger log = LoggerFactory.getLogger(
      ScheduleSelectionController.class
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
    termColumn.setCellValueFactory(new PropertyValueFactory<>("term"));
    statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

    // Initialize the term observable list and table view
    terms = FXCollections.observableArrayList();
    scheduleTable.setItems(terms);
  }

  @FXML
  private void backButtonClick() {
    mainController.showMenu();
  }

  @FXML
  public void submitButtonClick() {
    Term term = scheduleTable.getSelectionModel().getSelectedItem();
    conflictController.buildView(term);

    mainController.showConflict();
  }

  public void buildView() {
    terms.clear();
    terms = FXCollections.observableArrayList(
        Lists.newArrayList(termRepository.findAll())
    );

    numberLabel.setText(terms.size() + " Schedules Found");
    scheduleTable.setItems(terms);
    
    scheduleTable.getSelectionModel().selectFirst();
  }

}
