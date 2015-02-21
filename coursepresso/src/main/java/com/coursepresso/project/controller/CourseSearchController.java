package com.coursepresso.project.controller;

import com.coursepresso.project.entity.Course;
import com.coursepresso.project.entity.CourseSection;
import com.coursepresso.project.entity.Department;
import com.coursepresso.project.entity.MeetingDay;
import com.coursepresso.project.entity.Professor;
import com.coursepresso.project.entity.Term;
import com.coursepresso.project.repository.CourseSectionRepository;
import com.coursepresso.project.repository.DepartmentRepository;
import com.coursepresso.project.repository.TermRepository;
import com.google.common.collect.Lists;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
  private MainController mainController;
  @Inject
  private SearchResultsController searchResultsController;

  @PersistenceContext
  private EntityManager entityManager;

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
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery cq = cb.createQuery();
    Root<CourseSection> section = cq.from(CourseSection.class);
    Join<CourseSection, MeetingDay> day = section.join("day");
    List<Predicate> predicates = new ArrayList<>();
    
    // For joining queries
    // http://stackoverflow.com/questions/17154676/criteria-jpa-2-with-3-tables

    if (departmentCombo.getValue() != null) {
      predicates.add(cb.equal(
          section.get("department"),
          (Department) departmentCombo.getValue())
      );
    }
    if (termCombo.getValue() != null) {
      predicates.add(cb.equal(
          section.get("term"),
          (Term) termCombo.getValue())
      );
    }
    if (courseNumberCombo.getValue() != null) {
      predicates.add(cb.equal(
          section.get("courseNumber"),
          (Course) courseNumberCombo.getValue())
      );
    }
    if (instructorCombo.getValue() != null) {
      predicates.add(cb.equal(
          section.get("professorId"),
          (Professor) instructorCombo.getValue())
      );
    }
    if (lineNumberText.getText() != null) {
      predicates.add(cb.equal(
          section.get("id"),
          Integer.valueOf(lineNumberText.getText()))
      );
    }
    if (mondayCheckbox.isSelected()) {
      predicates.add(cb.equal(
          section.get("id"),
          Integer.valueOf(lineNumberText.getText()))
      );
    }
    if (tuesdayCheckbox.isSelected()) {

    }
    if (wednesdayCheckbox.isSelected()) {

    }
    if (thursdayCheckbox.isSelected()) {

    }
    if (fridayCheckbox.isSelected()) {

    }

    cq.select(section).where(predicates.toArray(new Predicate[]{}));
    List<CourseSection> result = entityManager.createQuery(cq).getResultList();

    searchResultsController.setResults(result);
    mainController.showSearchResults();
  }

  @FXML
  void courseNumberComboSelect(ActionEvent event) {

  }

  @FXML
  void departmentComboSelect(ActionEvent event) {
    Department department = (Department) departmentCombo.getValue();

    // Build course number combo box
    ObservableList<Course> courses = FXCollections.observableArrayList(
        // Get course list for selected department
        department.getCourseList()
    );
    courseNumberCombo.setItems(courses);
    courseNumberCombo.setVisibleRowCount(4);

    // Build professor combo box
    ObservableList<Professor> professors = FXCollections.observableArrayList(
        // Get professor list for selected department
        department.getProfessorList()
    );
    instructorCombo.setItems(professors);
    instructorCombo.setVisibleRowCount(4);
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
    
    lineNumberText.setText(null);
  }
}
