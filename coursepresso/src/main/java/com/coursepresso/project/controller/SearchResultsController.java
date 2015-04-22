package com.coursepresso.project.controller;

import com.coursepresso.project.entity.CourseSection;
import com.coursepresso.project.entity.MeetingDay;
import com.coursepresso.project.helper.Day;
import com.coursepresso.project.helper.MeetingDayHelper;
import com.coursepresso.project.repository.CourseSectionRepository;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSet;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
  private TableColumn<CourseSection, String> capacityColumn;
  @FXML
  private TableColumn<CourseSection, String> roomNumColumn;
  @FXML
  private TableColumn<CourseSection, String> startTimeColumn;
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
  @FXML
  private TableColumn<CourseSection, String> updatedAtColumn;
  @FXML
  private Button modifySectionButton;
  @FXML
  private Button deleteSectionButton;
  @FXML
  private Button addSectionButton;
  @FXML
  private Button backButton;

  @Inject
  private MainController mainController;
  @Inject
  private CourseSectionRepository courseSectionRepository;

  private ObservableList<CourseSection> courseSections;

  private static final Logger log = LoggerFactory.getLogger(
      SearchResultsController.class
  );

  public Node getView() {
    return root;
  }

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    lineNumColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    courseNumColumn.setCellValueFactory(new PropertyValueFactory<>("course"));
    professorColumn.setCellValueFactory(new PropertyValueFactory<>("professor"));
    studentsColumn.setCellValueFactory(new PropertyValueFactory<>("studentCount"));
    capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));

    // setCellValueFactory to display the course title
    courseNameColumn.setCellValueFactory(
        courseSection -> {
          SimpleStringProperty property = new SimpleStringProperty();
          property.setValue(
              courseSection.getValue().getCourse().getTitle()
          );
          return property;
        }
    );

    // setCellValueFactory to display the course days
    daysColumn.setCellValueFactory(
        courseSection -> {
          SimpleStringProperty property = new SimpleStringProperty();
          List<Day> days = new ArrayList<>();
          List<String> dayNames = new ArrayList<>();

          // Build list of weighted day name enums
          for (MeetingDay day : courseSection.getValue().getMeetingDayList()) {
            days.add(Day.valueOf(day.getDay()));
          }
          // Remove duplicate day names
          days = ImmutableSet.copyOf(days).asList();
          // Sort enum list by day names
          days = MeetingDayHelper.sortDayNames(days);
          // Build string list of day names
          for (Day day : days) {
            dayNames.add(day.getName());
          }
          // Generate delimited string of day names at the cell value
          property.setValue(
              Joiner.on(", ").join(dayNames)
          );
          return property;
        }
    );

    // setCellValueFactory to display the formatted time
    startTimeColumn.setCellValueFactory(
        courseSection -> {
          SimpleStringProperty property = new SimpleStringProperty();
          DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
          MeetingDay day;

          if (!courseSection.getValue().getMeetingDayList().isEmpty()) {
            // Get first meeting day of the course section
            day = courseSection.getValue().getMeetingDayList().get(0);
            // Format the start time of the meeting day
            property.setValue(dateFormat.format(day.getStartTime()));
          }
          return property;
        }
    );

    // setCellValueFactory to display the formatted time
    endTimeColumn.setCellValueFactory(
        courseSection -> {
          SimpleStringProperty property = new SimpleStringProperty();
          DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
          MeetingDay day;

          if (!courseSection.getValue().getMeetingDayList().isEmpty()) {
            // Get first meeting day of the course section
            day = courseSection.getValue().getMeetingDayList().get(0);
            // Format the end time of the meeting day
            property.setValue(dateFormat.format(day.getEndTime()));
          }
          return property;
        }
    );

    // setCellValueFactory to display the room number
    roomNumColumn.setCellValueFactory(
        courseSection -> {
          SimpleStringProperty property = new SimpleStringProperty();
          MeetingDay day;

          if (!courseSection.getValue().getMeetingDayList().isEmpty()) {
            // Get first meeting day of the course section
            day = courseSection.getValue().getMeetingDayList().get(0);
            property.setValue(day.getRoom().toString());
          }
          return property;
        }
    );

    // setCellValueFactory to display updated at
    updatedAtColumn.setCellValueFactory(
        courseSection -> {
          SimpleStringProperty property = new SimpleStringProperty();
          DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy hh:mm a");

          property.setValue(dateFormat.format(
                  courseSection.getValue().getUpdatedAt()
              ));
          return property;
        }
    );

    // Initialize the meeting day observable list and table view
    courseSections = FXCollections.observableArrayList();
    courseSectionTable.setItems(courseSections);
  }

  @FXML
  void addSectionButtonClick(ActionEvent event) {
    mainController.showNewCourseSection("SEARCH");
  }

  @FXML
  void backButtonClick(ActionEvent event) {
    mainController.showCourseSearch();
  }

  @FXML
  void modifySectionButtonClick(ActionEvent event) {
    mainController.showEditCourseSection(
        courseSectionTable.getSelectionModel().getSelectedItem(),
        "SEARCH"
    );
  }

  @FXML
  void deleteSectionButtonClick(ActionEvent event) {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle(null);
    alert.setHeaderText("Warning");
    alert.setContentText("Are you sure you want to delete this section?");

    Optional<ButtonType> result = alert.showAndWait();

    if (result.get() == ButtonType.OK) {
      courseSectionRepository.delete(
          courseSectionTable.getSelectionModel().getSelectedItem().getId()
      );
      courseSections.remove(
          courseSectionTable.getSelectionModel().getSelectedItem()
      );

      alert = new Alert(AlertType.INFORMATION);
      alert.setTitle(null);
      alert.setHeaderText(null);
      alert.setContentText("Course section deleted successfully!");

      alert.showAndWait();
    }
  }

  public void setResults(List<CourseSection> results) {
    courseSections.setAll(results);
    courseSectionTable.getSelectionModel().selectFirst();
  }
}
