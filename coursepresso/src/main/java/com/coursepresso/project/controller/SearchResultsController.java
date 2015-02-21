package com.coursepresso.project.controller;

import com.coursepresso.project.entity.CourseSection;
import com.coursepresso.project.entity.MeetingDay;
import com.coursepresso.project.repository.CourseSectionRepository;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.inject.Inject;

/**
 * FXML Controller class
 *
 * @author steev_000, Caleb Miller
 */
public class SearchResultsController implements Initializable {

  @FXML
  private Node root;
  @FXML
  private TableView<CourseSection> courseSectionTable;
  @FXML
  private TableColumn<CourseSection, String> courseNumColumn;
  @FXML
  private TableColumn<CourseSection, String> lineNumColumn;
  @FXML
  private Button modifySectionButton;
  @FXML
  private TableColumn<CourseSection, String> capacityColumn;
  @FXML
  private TableColumn<CourseSection, String> roomNumColumn;
  @FXML
  private TableColumn<CourseSection, String> startTimeColumn;
  @FXML
  private Button backButton;
  @FXML
  private TableColumn<CourseSection, String> daysColumn;
  @FXML
  private TableColumn<CourseSection, String> courseNameColumn;
  @FXML
  private TableColumn<CourseSection, String> professorColumn;
  @FXML
  private TableColumn<CourseSection, String> endTimeColumn;
  @FXML
  private TableColumn<CourseSection, String> studentsColumn;

  @Inject
  private MainController mainController;
  @Inject
  private CourseSectionRepository courseSectionRepository;

  private ObservableList<CourseSection> courseSections;

  public Node getView() {
    return root;
  }

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    lineNumColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    courseNumColumn.setCellValueFactory(new PropertyValueFactory<>("courseNumber"));
    professorColumn.setCellValueFactory(new PropertyValueFactory<>("professorId"));
    studentsColumn.setCellValueFactory(new PropertyValueFactory<>("studentCount"));
    capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));

    // Initialize the meeting day observable list and table view
    courseSections = FXCollections.observableArrayList();
    courseSectionTable.setItems(courseSections);
  }

  @FXML
  void backButtonClick(ActionEvent event) {
    mainController.showCourseSearch();
  }

  @FXML
  void modifySectionButtonClick(ActionEvent event) {
    mainController.showEditCourseSection(courseSectionTable.getSelectionModel().getSelectedItem());
  }

  public void buildView() {

  }

  public void setResults(List<CourseSection> results) {
    System.out.println(results);
    courseSections.setAll(results);
  }
}
