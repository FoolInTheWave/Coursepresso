package com.coursepresso.project.controller;

import com.coursepresso.project.entity.Term;
import com.coursepresso.project.repository.TermRepository;
import com.google.common.collect.Lists;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
 * @author Steve Foco
 */
public class ViewSchedulesController implements Initializable {

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
  private Button addButton;
  @FXML
  private Button deleteButton;
  @FXML
  private Button toggleButton;

  @Inject
  private MainController mainController;
  @Inject
  private ConflictController conflictController;
  @Inject
  private TermRepository termRepository;

  private ObservableList<Term> terms;

  private static final Logger log = LoggerFactory.getLogger(
      ViewSchedulesController.class
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
  private void addButtonClick() {
    mainController.showNewSchedule();
  }

  @FXML
  private void toggleButtonClick() {
    Term term = scheduleTable.getSelectionModel().getSelectedItem();
    String newStatus = "";

    if (term.getStatus().equals("Open")) {
      newStatus = "Closed";
    } else {
      newStatus = "Open";
    }

    term.setStatus(newStatus);

    termRepository.save(term);

    buildView();
  }

  @FXML
  public void deleteButtonClick() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle(null);
    alert.setHeaderText("Warning");
    alert.setContentText(
        "Are you sure? All course sections in the term will be deleted as well."
    );

    Optional<ButtonType> result = alert.showAndWait();

    if (result.get() == ButtonType.OK) {
      numberLabel.setText(
          Integer.toString(Integer.parseInt(numberLabel.getText()) - 1)
      );

      termRepository.delete(
          scheduleTable.getSelectionModel().getSelectedItem().getTerm()
      );
      terms.remove(
          scheduleTable.getSelectionModel().getSelectedItem()
      );

      alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle(null);
      alert.setHeaderText(null);
      alert.setContentText("Term deleted successfully!");

      alert.showAndWait();
    }
  }

  public void buildView() {
    terms.clear();
    terms = FXCollections.observableArrayList(
        Lists.newArrayList(termRepository.findAll())
    );

    numberLabel.setText(Integer.toString(terms.size()));
    scheduleTable.setItems(terms);

    scheduleTable.getSelectionModel().selectFirst();
  }

}
