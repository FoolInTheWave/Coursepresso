package com.coursepresso.project.controller;

import com.coursepresso.project.entity.Course;
import com.coursepresso.project.entity.CourseSection;
import com.coursepresso.project.entity.Department;
import com.coursepresso.project.entity.Professor;
import com.coursepresso.project.entity.Term;
import com.coursepresso.project.repository.DepartmentRepository;
import com.coursepresso.project.repository.TermRepository;
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
  private ComboBox courseLevelCombo;
  @FXML
  private ComboBox termCombo;
  @FXML
  private TextField lineNumberText;
  @FXML
  private ComboBox departmentCombo;
  @FXML
  private CheckBox fridayCheckbox;
  @FXML
  private CheckBox thursdayCheckbox;
  @FXML
  private ComboBox courseNumberCombo;
  @FXML
  private CheckBox tuesdayCheckbox;
  @FXML
  private ComboBox instructorCombo;
  @FXML
  private CheckBox mondayCheckbox;
  @FXML
  private Button backButton;
  @FXML
  private ComboBox creditsCombo;

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
  private void searchButtonClick(ActionEvent event) {
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

    if (departmentCombo.getValue() != null) {
      params.put("department", (Department) departmentCombo.getValue());
    }
    if (termCombo.getValue() != null) {
      params.put("term", (Term) termCombo.getValue());
    }
    if (courseNumberCombo.getValue() != null) {
      params.put("course", (Course) courseNumberCombo.getValue());
    }
    if (instructorCombo.getValue() != null) {
      params.put("professor", (Professor) instructorCombo.getValue());
    }
    if (courseLevelCombo.getValue() != null) {
      params.put("courseLevel", (String) courseLevelCombo.getValue());
    }
    if (courseNumberCombo.getValue() != null) {
      Course c = (Course) courseNumberCombo.getValue();
      params.put("courseNumber", c.getCourseNumber());
    }
    if (lineNumberText.getText() != null) {
      params.put("lineNumber", Integer.valueOf(lineNumberText.getText()));
    }
    if (creditsCombo.getValue() != null) {
      String credits = (String) creditsCombo.getValue();
      params.put("credits", Integer.valueOf(credits));
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
  void courseNumberComboSelect(ActionEvent event) {

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

  @FXML
  void termComboSelect(ActionEvent event) {

  }

  @FXML
  void courseLevelComboSelect(ActionEvent event) {

  }

  @FXML
  void instructorComboSelect(ActionEvent event) {

  }

  @FXML
  void creditsComboSelect(ActionEvent event) {

  }

  public void buildView() {
    // Build department combo box
    ObservableList<Department> departments = FXCollections.observableArrayList(
        Lists.newArrayList(departmentRepository.findAll())
    );
    departmentCombo.setItems(departments);
    departmentCombo.setVisibleRowCount(4);

    // Build term combo box
    ObservableList<Term> terms = FXCollections.observableArrayList(
        Lists.newArrayList(termRepository.findAll())
    );
    termCombo.setItems(terms);
    termCombo.setVisibleRowCount(4);

    // Build type combo box
    ObservableList<String> levels = FXCollections.observableArrayList(
        "100", "200", "300", "400"
    );
    courseLevelCombo.setItems(levels);
    courseLevelCombo.setVisibleRowCount(4);

    // Build type combo box
    ObservableList<String> credits = FXCollections.observableArrayList(
        "1", "2", "3", "4"
    );
    creditsCombo.setItems(credits);
    creditsCombo.setVisibleRowCount(4);

    departmentCombo.getSelectionModel().clearSelection();
    termCombo.getSelectionModel().clearSelection();
    courseLevelCombo.getSelectionModel().clearSelection();
    courseNumberCombo.getSelectionModel().clearSelection();
    instructorCombo.getSelectionModel().clearSelection();
    creditsCombo.getSelectionModel().clearSelection();
    lineNumberText.setText(null);
  }
}
