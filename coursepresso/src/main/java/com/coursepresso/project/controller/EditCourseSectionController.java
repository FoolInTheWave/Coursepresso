package com.coursepresso.project.controller;

import com.coursepresso.project.entity.*;
import com.coursepresso.project.helper.DateHelper;
import com.coursepresso.project.repository.*;
import com.google.common.collect.Lists;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FXML Controller class
 *
 * @author steev_000
 */
public class EditCourseSectionController implements Initializable {

  @FXML
  private Node root;
  @FXML
  private ComboBox<Department> departmentCombo;
  @FXML
  private ComboBox<Course> courseNumberCombo;
  @FXML
  private TextField titleField;
  @FXML
  private TextField sectionField;
  @FXML
  private TextField capacityField;
  @FXML
  private ComboBox<String> typeCombo;
  @FXML
  private ComboBox<Term> termCombo;
  @FXML
  private DatePicker startDatePicker;
  @FXML
  private DatePicker endDatePicker;
  @FXML
  private TableView<MeetingDay> meetingDayTable;
  @FXML
  private TableColumn<MeetingDay, String> startTimeColumn;
  @FXML
  private TableColumn<MeetingDay, String> endTimeColumn;
  @FXML
  private TableColumn<MeetingDay, String> roomColumn;
  @FXML
  private TableColumn<MeetingDay, String> dayColumn;
  @FXML
  private TextField startTimeField;
  @FXML
  private TextField endTimeField;
  @FXML
  private ComboBox<Room> roomCombo;
  @FXML
  private ComboBox<String> dayCombo;
  @FXML
  private ComboBox<Professor> instructorCombo;
  @FXML
  private Button addDayButton;
  @FXML
  private Button submitChangesButton;
  @FXML
  private Button backButton;
  @FXML
  private Button deleteDayButton;

  @Inject
  private CourseSectionRepository courseSectionRepository;
  @Inject
  private CourseRepository courseRepository;
  @Inject
  private DepartmentRepository departmentRepository;
  @Inject
  private MeetingDayRepository meetingDayRepository;
  @Inject
  private RoomRepository roomRepository;
  @Inject
  private TermRepository termRepository;
  @Inject
  private MainController mainController;

  private ObservableList<MeetingDay> meetingDays;
  private ArrayList<MeetingDay> daysToDelete;
  private CourseSection courseSection;
  private String sourcePage;

  private static final Logger log = LoggerFactory.getLogger(
      EditCourseSectionController.class
  );

  public Node getView() {
    return root;
  }

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // setCellValueFactory to display the formatted time
    startTimeColumn.setCellValueFactory(
        meetingDay -> {
          SimpleStringProperty property = new SimpleStringProperty();
          DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
          property.setValue(dateFormat.format(
                  meetingDay.getValue().getStartTime())
          );
          return property;
        }
    );
    // setCellFactory to display the formatted time
    endTimeColumn.setCellValueFactory(
        meetingDay -> {
          SimpleStringProperty property = new SimpleStringProperty();
          DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
          property.setValue(dateFormat.format(
                  meetingDay.getValue().getEndTime())
          );
          return property;
        }
    );
    roomColumn.setCellValueFactory(new PropertyValueFactory<>("room"));
    dayColumn.setCellValueFactory(new PropertyValueFactory<>("day"));

    // Initialize the meeting day observable list and table view
    meetingDays = FXCollections.observableArrayList();
    meetingDayTable.setItems(meetingDays);
    daysToDelete = new ArrayList<>();
  }

  @FXML
  private void backButtonClick(ActionEvent event) {
    if (sourcePage.equals("SEARCH")) {
      mainController.showSearchResults();
    } else if (sourcePage.equals("CONFLICT")) {
      mainController.showConflict();
    }
  }

  @FXML
  private void submitChangesButtonClick(ActionEvent event) {
    if (meetingDayTable.getSelectionModel().getTableView().getItems().isEmpty()) {
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Errors");
      alert.setHeaderText(null);
      alert.setContentText("Please enter at least one meeting day.");
      alert.showAndWait();
    } else {
      courseSection.setCourse(courseNumberCombo.getValue());
      courseSection.setSectionNumber(Integer.parseInt(sectionField.getText()));
      courseSection.setAvailable(true);
      courseSection.setCapacity(Integer.parseInt(capacityField.getText()));
      courseSection.setSeatsAvailable(courseSection.getCapacity());
      courseSection.setStatus("Open");
      courseSection.setTerm(termCombo.getValue());
      courseSection.setStudentCount(0);
      courseSection.setType(typeCombo.getValue());

      // Save LocalDate as Date
      courseSection.setStartDate(DateHelper.asDate(startDatePicker.getValue()));
      courseSection.setEndDate(DateHelper.asDate(endDatePicker.getValue()));
      courseSection.setDepartment(departmentCombo.getValue());
      courseSection.setProfessor(instructorCombo.getValue());

      for (MeetingDay dayToDel : daysToDelete) {
        meetingDayRepository.delete(dayToDel.getId());
      }

      courseSection.setMeetingDayList(new ArrayList<MeetingDay>());
      courseSection = courseSectionRepository.save(courseSection);

      // Save MeetingDays for CourseSection
      for (MeetingDay day : meetingDays) {
        day.setCourseSection(courseSection);
        day.setTerm(courseSection.getTerm());
      }
      meetingDayRepository.save(new ArrayList<MeetingDay>(meetingDays));

      Alert alert = new Alert(AlertType.INFORMATION);
      alert.setTitle("Course Section Saved");
      alert.setHeaderText(null);
      alert.setContentText("The course section has been saved successfully!");
      alert.showAndWait();

      if (sourcePage.equals("SEARCH")) {
        mainController.courseSearchController.searchButtonClick(event);
      } else if (sourcePage.equals("CONFLICT")) {
        mainController.conflictController.buildView(courseSection.getTerm());
        mainController.showConflict();
      }
    }
  }

  @FXML
  private void deleteDayButtonClick(ActionEvent event) {
    MeetingDay md = meetingDayTable.getSelectionModel().getSelectedItem();

    daysToDelete.add(md);
    meetingDays.remove(md);
  }

  @FXML
  private void addDayButtonClick(ActionEvent event) {
    DateFormat df = new SimpleDateFormat("hh:mm a");
    MeetingDay day = new MeetingDay();

    try {
      day.setStartTime(df.parse(startTimeField.getText()));
      day.setEndTime(df.parse(endTimeField.getText()));

      day.setRoom(roomCombo.getValue());
      day.setDay(dayCombo.getValue());

      meetingDays.add(day);

      // Set the capacity field to the room with the lowest capacity
      capacityField.setText(
          Integer.toString(
              // Find the room with the lowest capacity from the current day list
              meetingDays.stream()
              .min((m1, m2) -> Integer.compare(m1.getRoom().getCapacity(),
                      m2.getRoom().getCapacity()
                  )).get().getRoom().getCapacity()
          )
      );

      // Clear the combo boxes for meeting day
      startTimeField.clear();
      endTimeField.clear();
      roomCombo.getSelectionModel().clearSelection();
      dayCombo.getSelectionModel().clearSelection();
    } catch (ParseException ex) {
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Error");
      alert.setHeaderText("Parse Exception");
      alert.setContentText(
          "The meeting day start or end time format is invalid!"
      );
      alert.showAndWait();
      log.error("Parse exception: ", ex);
    }

    // Clear the combo boxes for meeting day
    startTimeField.clear();
    endTimeField.clear();
    roomCombo.getSelectionModel().clearSelection();
    dayCombo.getSelectionModel().clearSelection();
  }

  @FXML
  private void courseNumberComboSelect(ActionEvent event) {
    Course course = courseRepository.findByCourseNumberWithCourseSections(
        courseSection.getCourse().getCourseNumber()
    );

    if (course != null) {
      titleField.setText(course.getTitle());

      if (!course.getCourseSectionList().isEmpty()) {
        // Set the section number field with the next available number
        sectionField.setText(
            Integer.toString(
                course.getCourseSectionList().stream()
                .max((cs1, cs2) -> Integer.compare(
                        cs1.getSectionNumber(),
                        cs2.getSectionNumber()
                    )).get().getSectionNumber() + 1
            )
        );
      } else {
        sectionField.setText("1");
      }
    } else {
      titleField.setText(null);
      sectionField.setText(null);
    }
  }

  public void buildView(CourseSection cs, String sourcePage) {
    this.courseSection = cs;
    this.sourcePage = sourcePage;

    // Build department combo box
    ObservableList<Department> departments = FXCollections.observableArrayList(
        Lists.newArrayList(departmentRepository.findAll())
    );
    departmentCombo.setItems(departments);
    departmentCombo.getSelectionModel().select(cs.getDepartment());

    // Build course number combo box
    Department department = departmentRepository.findByNameWithCourses(
        departmentCombo.getValue().getName()
    );

    ObservableList<Course> courses = FXCollections.observableArrayList(
        // Get course list for selected department
        department.getCourseList()
    );
    courseNumberCombo.setItems(courses);
    courseNumberCombo.getSelectionModel().select(cs.getCourse());

    // Fill course title textbox
    Course course = courseNumberCombo.getValue();
    titleField.setText(course.getTitle());

    // Fill section text box
    sectionField.setText(Integer.toString(cs.getSectionNumber()));

    // Build professor combo box
    department = departmentRepository.findByNameWithProfessors(
        department.getName()
    );
    ObservableList<Professor> professors = FXCollections.observableArrayList(
        // Get professor list for selected department
        department.getProfessorList()
    );
    instructorCombo.setItems(professors);
    instructorCombo.getSelectionModel().select(cs.getProfessor());

    // Build type combo box
    ObservableList<String> types = FXCollections.observableArrayList(
        "HYB", "LEC", "ONL"
    );
    typeCombo.setItems(types);
    typeCombo.getSelectionModel().select(cs.getType());

    // Build term combo box
    ObservableList<Term> terms = FXCollections.observableArrayList(
        Lists.newArrayList(termRepository.findAll())
    );
    termCombo.setItems(terms);
    termCombo.getSelectionModel().select(cs.getTerm());

    // Build room combo box
    ObservableList<Room> roomNumbers = FXCollections.observableArrayList(
        Lists.newArrayList(roomRepository.findAll())
    );
    roomCombo.setItems(roomNumbers);

    // Build day combo box
    ObservableList<String> days = FXCollections.observableArrayList(
        "M", "T", "W", "TH", "F", "S", "SU"
    );
    dayCombo.setItems(days);

    // Build meeting day table
    meetingDays.clear();
    if (cs.getMeetingDayList() != null) {
      cs.getMeetingDayList().stream().forEach((day) -> {
        meetingDays.add(day);
      });
    }

    // Build start and end date pickers
    Instant startInstant = Instant.ofEpochMilli(cs.getStartDate().getTime());
    LocalDate startLocalDate = LocalDateTime.ofInstant(
        startInstant, ZoneId.systemDefault()
    ).toLocalDate();
    startDatePicker.setValue(startLocalDate);

    Instant endInstant = Instant.ofEpochMilli(cs.getEndDate().getTime());
    LocalDate endLocalDate = LocalDateTime.ofInstant(
        endInstant, ZoneId.systemDefault()
    ).toLocalDate();
    endDatePicker.setValue(endLocalDate);

    // Set the capacity field to the room with the lowest capacity
    if (!meetingDays.isEmpty()) {
      capacityField.setText(
          Integer.toString(
              // Find the room with the lowest capacity from the current day list
              meetingDays.stream()
              .min((m1, m2) -> Integer.compare(m1.getRoom().getCapacity(),
                      m2.getRoom().getCapacity()
                  )).get().getRoom().getCapacity()
          )
      );
    }
  }

}
