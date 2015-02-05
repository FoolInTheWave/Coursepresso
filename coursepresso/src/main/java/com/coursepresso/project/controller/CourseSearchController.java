/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coursepresso.project.controller;

import com.coursepresso.project.entity.Course;
import com.coursepresso.project.entity.Department;
import com.coursepresso.project.entity.Professor;
import com.coursepresso.project.entity.Term;
import com.coursepresso.project.repository.CourseProfessorRepository;
import com.coursepresso.project.repository.CourseSectionRepository;
import com.coursepresso.project.repository.DepartmentRepository;
import com.coursepresso.project.repository.TermRepository;
import com.google.common.collect.Lists;
import java.net.URL;
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
  private CourseSectionRepository courseSectionRepository;
  @Inject
  private CourseProfessorRepository courseProfessorRepository;
  @Inject
  private DepartmentRepository departmentRepository;
  @Inject
  private MainController mainController;
  @Inject
  private TermRepository termRepository;

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
  }

}
