package com.coursepresso.project.controller;

import com.coursepresso.project.entity.*;
import com.coursepresso.project.repository.*;
import com.coursepresso.project.service.SearchService;
import com.google.common.collect.Lists;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FXML Controller class
 *
 * @author steev_000, Caleb Miller
 */
public class CourseSearchController implements Initializable {

  @FXML
  private Node root;
  @FXML
  private AnchorPane titledPaneAnchor;
  @FXML
  private CheckBox wednesdayCheckbox;
  @FXML
  private Button searchButton;
  @FXML
  private ComboBox<String> courseLevelCombo;
  @FXML
  private ComboBox<Term> termCombo;
  @FXML
  private TextField lineNumberText;
  @FXML
  private ComboBox<Department> departmentCombo;
  @FXML
  private CheckBox fridayCheckbox;
  @FXML
  private CheckBox thursdayCheckbox;
  @FXML
  private ComboBox<Course> courseNumberCombo;
  @FXML
  private CheckBox tuesdayCheckbox;
  @FXML
  private ComboBox<Professor> instructorCombo;
  @FXML
  private CheckBox mondayCheckbox;
  @FXML
  private Button backButton;
  @FXML
  private ComboBox<Integer> creditsCombo;

  @Inject
  private DepartmentRepository departmentRepository;
  @Inject
  private TermRepository termRepository;
  @Inject
  private SearchService searchService;
  @Inject
  private MainController mainController;
  @Inject
  private SearchResultsController searchResultsController;

  private List<CourseSection> result;
  
  private static final Logger log = LoggerFactory.getLogger(
      CourseSearchController.class
  );

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
  private void backButtonClick(ActionEvent event) {
    mainController.showMenu();
  }

  @FXML
  public void searchButtonClick(ActionEvent event) {
    Map<String, Object> params = new HashMap<>();
    result = new ArrayList<>();

    final Task<Void> searchTask = new Task<Void>() {
      @Override
      protected Void call() throws Exception {
        log.info("Performing course section search");
        result = searchService.searchSections(params);
        return null;
      }
    };

    if (termCombo.getValue() != null) {
      params.put("term", termCombo.getValue());
    }
    if (departmentCombo.getValue() != null) {
      params.put("department", departmentCombo.getValue());
    }
    if (courseNumberCombo.getValue() != null) {
      params.put("course", courseNumberCombo.getValue());
    }
    if (instructorCombo.getValue() != null) {
      params.put("professor", instructorCombo.getValue());
    }
    if (courseLevelCombo.getValue() != null) {
      params.put("courseLevel", (String) courseLevelCombo.getValue());
    }
    if (courseNumberCombo.getValue() != null) {
      Course c = courseNumberCombo.getValue();
      params.put("courseNumber", c.getCourseNumber());
    }
    if (lineNumberText.getText() != null) {
      params.put("lineNumber", Integer.valueOf(lineNumberText.getText()));
    }
    if (creditsCombo.getValue() != null) {
      params.put("credits", creditsCombo.getValue());
    }
    if (mondayCheckbox.isSelected()) {
      params.put("monday", "M");
    }
    if (tuesdayCheckbox.isSelected()) {
      params.put("tuesday", "T");
    }
    if (wednesdayCheckbox.isSelected()) {
      params.put("wednesday", "W");
    }
    if (thursdayCheckbox.isSelected()) {
      params.put("thursday", "TH");
    }
    if (fridayCheckbox.isSelected()) {
      params.put("friday", "F");
    }

    searchTask.stateProperty().addListener(
        (ObservableValue<? extends Worker.State> source,
            Worker.State oldState,
            Worker.State newState) -> {
          if (newState.equals(Worker.State.SUCCEEDED)) {
            log.info("Successfully retrieved search results");
            searchResultsController.setResults(result);
            mainController.showSearchResults();
          } else if (newState.equals(Worker.State.FAILED)) {
            Throwable exception = searchTask.getException();
            log.error("Retrieval of search results failed: ", exception);
          }
        }
    );

    new Thread(searchTask).start();
  }

  @FXML
  void departmentComboSelect(ActionEvent event) {
    if (departmentCombo.getValue() != null) {
      try {
        Department department = (Department) departmentCombo.getValue();

        // Build course number combo box
        department = departmentRepository.findByNameWithCourses(
            department.getName()
        );
        ObservableList<Course> courses = FXCollections.observableArrayList(
            // Get course list for selected department
            department.getCourseList()
        );
        courseNumberCombo.setItems(courses);
        courseNumberCombo.setVisibleRowCount(4);

        // Build professor combo box
        department = departmentRepository.findByNameWithProfessors(
            department.getName()
        );
        ObservableList<Professor> professors = FXCollections.observableArrayList(
            // Get professor list for selected department
            department.getProfessorList()
        );
        instructorCombo.setItems(professors);
        instructorCombo.setVisibleRowCount(4);
      } catch (NullPointerException e) {

      }
    }
  }

  public void buildView() {
    // Build department combo box
    ObservableList<Department> departments = FXCollections.observableArrayList(
        Lists.newArrayList(departmentRepository.findAll())
    );
    departmentCombo.setItems(departments);

    // Build term combo box
    ObservableList<Term> terms = FXCollections.observableArrayList(
        Lists.newArrayList(termRepository.findAll())
    );
    termCombo.setItems(terms);

    // Build type combo box
    ObservableList<String> levels = FXCollections.observableArrayList(
        "100", "200", "300", "400"
    );
    courseLevelCombo.setItems(levels);

    // Build type combo box
    ObservableList<Integer> credits = FXCollections.observableArrayList(
        1, 2, 3, 4, 5
    );
    creditsCombo.setItems(credits);

    departmentCombo.getSelectionModel().clearSelection();
    termCombo.getSelectionModel().clearSelection();
    courseLevelCombo.getSelectionModel().clearSelection();
    courseNumberCombo.getSelectionModel().clearSelection();
    instructorCombo.getSelectionModel().clearSelection();
    creditsCombo.getSelectionModel().clearSelection();
    lineNumberText.setText(null);
  }
}
