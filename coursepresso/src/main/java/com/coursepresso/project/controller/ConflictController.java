package com.coursepresso.project.controller;

import com.coursepresso.project.entity.Term;
import com.coursepresso.project.service.ConflictService;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javax.inject.Inject;

/**
 * FXML Controller class
 *
 * @author Caleb Miller
 */
public class ConflictController implements Initializable {

  @FXML
  private Node root;
  @FXML
  private Label numberLabel;
  @FXML
  private TableView conflictTable;
  @FXML
  private TableColumn courseColumn;
  @FXML
  private TableColumn sectionColumn;
  @FXML
  private TableColumn termColumn;
  @FXML
  private TableColumn reasonColumn;
  @FXML
  private Button backButton;
  @FXML
  private Button resolveManuallyButton;
  @FXML
  private Button resolveAutomaticallyButton;

  @Inject
  private MainController mainController;
  @Inject
  private ConflictService conflictService;
  
  public Node getView() {
    return root;
  }
  
  private static List<String> conflicts;

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
  private void backButtonClick() {
    mainController.showScheduleSelection();
  }

  public void buildView(Term selectedTerm) {
    //conflicts = conflictService.getConflicts(selectedTerm);
    
    System.out.println(conflicts);
  }
}
