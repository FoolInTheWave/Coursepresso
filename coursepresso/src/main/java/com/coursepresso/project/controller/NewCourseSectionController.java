package com.coursepresso.project.controller;

import com.coursepresso.project.entity.*;
import com.coursepresso.project.repository.*;
import com.coursepresso.project.helper.DateHelper;
import com.google.common.collect.Lists;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
 * @author Caleb Miller
 */
public class NewCourseSectionController implements Initializable {

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
  private ComboBox<MeetingTime> startTimeCombo;
  @FXML
  private ComboBox<MeetingTime> endTimeCombo;
  @FXML
  private ComboBox<Room> roomCombo;
  @FXML
  private ComboBox<String> dayCombo;
  @FXML
  private ComboBox<Professor> instructorCombo;
  @FXML
  private Button addDayButton;
  @FXML
  private Button submitCourseButton;
  @FXML
  private Button backButton;

  @Inject
  private CourseSectionRepository courseSectionRepository;
  @Inject
  private CourseRepository courseRepository;
  @Inject
  private DepartmentRepository departmentRepository;
  @Inject
  private MeetingDayRepository meetingDayRepository;
  @Inject
  private MeetingTimeRepository meetingTimeRepository;
  @Inject
  private RoomRepository roomRepository;
  @Inject
  private TermRepository termRepository;
  @Inject
  private MainController mainController;

  private ObservableList<MeetingDay> meetingDays;
  private String sourcePage;

  private static final Logger log = LoggerFactory.getLogger(
          NewCourseSectionController.class
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
  }

  @FXML
  private void submitCourseButtonClick(ActionEvent event) {
    StringBuilder sbErrors = validate();
    if (!(sbErrors.toString().equals(""))) {
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Errors");
      alert.setHeaderText(null);
      alert.setContentText(sbErrors.toString());
      alert.showAndWait();
    } else {

      CourseSection courseSection = new CourseSection();

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
      if (sourcePage.equals("MENU")) {
        mainController.showMenu();
      } else if (sourcePage.equals("SEARCH")) {
        mainController.courseSearchController.searchButtonClick(event);
      }
    }
  }

  @FXML
  private void addDayButtonClick(ActionEvent event) {
    DateFormat df = new SimpleDateFormat("hh:mm a");
    MeetingDay day = new MeetingDay();

    try {
      day.setStartTime(df.parse(startTimeCombo.getValue().toString()));
      day.setEndTime(df.parse(endTimeCombo.getValue().toString()));
    } catch (ParseException ex) {
      System.err.println(ex);
    }
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
    startTimeCombo.setValue(null);
    endTimeCombo.setValue(null);
    roomCombo.setValue(null);
    dayCombo.setValue(null);
  }

  @FXML
  private void backButtonClick(ActionEvent event) {
    if (sourcePage.equals("MENU")) {
      mainController.showMenu();
    } else if (sourcePage.equals("SEARCH")) {
      mainController.showSearchResults();
    }
  }

  @FXML
  private void departmentComboSelect(ActionEvent event) {
    // Build course number combo box
    Department department = departmentRepository.findByNameWithCourses(
            departmentCombo.getValue().getName()
    );
    ObservableList<Course> courses = FXCollections.observableArrayList(
            // Get course list for selected department
            department.getCourseList()
    );
    courseNumberCombo.setItems(courses);

    // Build professor combo box
    department = departmentRepository.findByNameWithProfessors(
            department.getName()
    );
    ObservableList<Professor> professors = FXCollections.observableArrayList(
            // Get professor list for selected department
            department.getProfessorList()
    );
    instructorCombo.setItems(professors);
  }

  @FXML
  private void courseNumberComboSelect(ActionEvent event) {
    Course course = courseRepository.findByCourseNumberWithCourseSections(
            courseNumberCombo.getValue().getCourseNumber()
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

  public StringBuilder validate() {
    ArrayList<String> errors = new ArrayList();
    StringBuilder sbErrors = new StringBuilder();
    sbErrors.append("");

    if (departmentCombo.getSelectionModel().getSelectedItem() == null) {
      errors.add("Department");
    }
    if (courseNumberCombo.getSelectionModel().getSelectedItem() == null) {
      errors.add("Course Number");
    }
    if (termCombo.getSelectionModel().getSelectedItem() == null) {
      errors.add("Term");
    }
    if (typeCombo.getSelectionModel().getSelectedItem() == null) {
      errors.add("Type");
    }
    if (instructorCombo.getSelectionModel().getSelectedItem() == null) {
      errors.add("Instructor");
    }
    if (startDatePicker.getValue() == null) {
      errors.add("Start Date");
    }
    if (endDatePicker.getValue() == null) {
      errors.add("End Date");
    }
    if (meetingDayTable.getSelectionModel().getTableView().getItems().isEmpty()) {
      errors.add("Meeting Days");
    }

    if (!errors.isEmpty()) {
      sbErrors.append("Please enter the following before submitting: " + '\n');

      for (String error : errors) {
        sbErrors.append('\t').append(error).append('\n');
      }
    }

    return sbErrors;
  }
  
  public void buildView(String sourcePage) {
    this.sourcePage = sourcePage;

    // Build department combo box
    ObservableList<Department> departments = FXCollections.observableArrayList(
            Lists.newArrayList(departmentRepository.findAll())
    );
    departmentCombo.setItems(departments);

    // Build type combo box
    ObservableList<String> types = FXCollections.observableArrayList(
            "HYB", "LEC", "ONL"
    );
    typeCombo.setItems(types);

    // Build term combo box
    ObservableList<Term> terms = FXCollections.observableArrayList(
            Lists.newArrayList(termRepository.findAll())
    );
    termCombo.setItems(terms);

    // Build time combo boxes
    ObservableList<MeetingTime> meetingTimes = FXCollections.observableArrayList(
            Lists.newArrayList(meetingTimeRepository.findAll())
    );
    startTimeCombo.setItems(meetingTimes);
    endTimeCombo.setItems(meetingTimes);

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
  }

}
