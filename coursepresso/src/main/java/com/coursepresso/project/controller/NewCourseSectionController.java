package com.coursepresso.project.controller;

import com.coursepresso.project.entity.Course;
import com.coursepresso.project.entity.Department;
import com.coursepresso.project.entity.MeetingDay;
import com.coursepresso.project.entity.MeetingTime;
import com.coursepresso.project.entity.Professor;
import com.coursepresso.project.entity.Room;
import com.coursepresso.project.entity.Term;
import com.coursepresso.project.repository.CourseRepository;
import com.coursepresso.project.repository.CourseSectionRepository;
import com.coursepresso.project.repository.DepartmentRepository;
import com.coursepresso.project.repository.MeetingTimeRepository;
import com.coursepresso.project.repository.RoomRepository;
import com.coursepresso.project.repository.TermRepository;

import com.google.common.collect.Lists;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.inject.Inject;

/**
 * FXML Controller class
 *
 * @author Caleb Miller
 */
public class NewCourseSectionController implements Initializable {
  
  @FXML
  private Node root;
  @FXML
  private ComboBox departmentCombo;
  @FXML
  private ComboBox courseNumberCombo;
  @FXML
  private TextField titleField;
  @FXML
  private ComboBox instructorCombo;
  @FXML
  private TextField sectionField;
  @FXML
  private TextField capacityField;
  @FXML
  private ComboBox typeCombo;
  @FXML
  private ComboBox termCombo;
  @FXML
  private DatePicker startDatePicker;
  @FXML
  private DatePicker endDatePicker;
  @FXML
  private TableView<MeetingDay> meetingDayTable;
  @FXML
  private TableColumn<MeetingDay, Date> startTimeColumn;
  @FXML
  private TableColumn<MeetingDay, Date> endTimeColumn;
  @FXML
  private TableColumn<MeetingDay, Room> roomColumn;
  @FXML
  private TableColumn<MeetingDay, String> dayColumn;
  @FXML
  private ComboBox startTimeCombo;
  @FXML
  private ComboBox endTimeCombo;
  @FXML
  private ComboBox roomCombo;
  @FXML
  private ComboBox dayCombo;
  @FXML
  private Button addDayButton;
  @FXML
  private Button submitCourseButton;
  @FXML
  private Button backToListingButton;

  @Inject
  private CourseRepository courseRepository;
  @Inject
  private CourseSectionRepository courseSectionRepository;
  @Inject
  private DepartmentRepository departmentRepository;
  @Inject
  private MeetingTimeRepository meetingTimeRepository;
  @Inject
  private RoomRepository roomRepository;
  @Inject
  private TermRepository termRepository;
  @Inject
  private MainController mainPresenter;
  
  private ObservableList<MeetingDay> meetingDays;

  /**
   * Initializes the controller class.
   * 
   * @param url
   * @param rb
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // Initialize the meeting day table with four columns
    startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
    // setCellFactory to display the formatted time
    endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
    // setCellFactory to display the formatted time
    roomColumn.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
    dayColumn.setCellValueFactory(new PropertyValueFactory<>("day"));
    
    // Initialize the meeting day observable list and table view
    meetingDays = FXCollections.observableArrayList();
    meetingDayTable.setItems(meetingDays);
  }
  
  public Node getView() {
    return root;
  }
  
  @FXML
  private void saveCourseSection(ActionEvent event) {
    // TODO
  }
  
  @FXML
  private void departmentComboSelect(ActionEvent event) {
    Department department = (Department) departmentCombo.getSelectionModel()
        .getSelectedItem();
    
    // Get course list for selected department
    ArrayList<Course> courseList = new ArrayList<>(department.getCourseList());
    
    // Build course number combo box
    ObservableList<Course> courses = FXCollections.observableArrayList(
        courseList
    );
    courseNumberCombo.setItems(courses);
    courseNumberCombo.setVisibleRowCount(4);
    
    // Get professor list for selected department
    ArrayList<Professor> professorList = new ArrayList<>(
        department.getProfessorList()
    );
    
    // Build professor combo box
    ObservableList<Professor> professors = FXCollections.observableArrayList(
        professorList
    );
    instructorCombo.setItems(professors);
    instructorCombo.setVisibleRowCount(4);
  }
  
  @FXML
  private void courseNumberComboSelect(ActionEvent event) {
    Course course = (Course) courseNumberCombo.getSelectionModel()
        .getSelectedItem();
    
    titleField.setText(course.getTitle());
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
    day.setRoomNumber((Room) roomCombo.getValue());
    day.setDay(dayCombo.getValue().toString());
    
    meetingDays.add(day);
  }
  
  public void buildView() {
    // Build department combo box
    ObservableList<Department> departments = FXCollections.observableArrayList(
        // Sort department list by name
        Lists.newArrayList(departmentRepository.findAll()).stream()
            .sorted((d1, d2) -> d1.getName()
                .compareTo(d2.getName()))
            .collect(Collectors.toList())
    );
    departmentCombo.setItems(departments);
    departmentCombo.setVisibleRowCount(4);
    
    // Build type combo box
    ObservableList<String> types = FXCollections.observableArrayList(
        "HYB", "LEC", "ONL"
    );
    typeCombo.setItems(types);
    typeCombo.setVisibleRowCount(4);
    
    // Build term combo box
    ObservableList<Term> terms = FXCollections.observableArrayList(
        // Sort term list by term
        Lists.newArrayList(termRepository.findAll()).stream()
            .sorted((t1, t2) -> t1.getTerm()
                .compareTo(t2.getTerm()))
            .collect(Collectors.toList())
    );
    termCombo.setItems(terms);
    termCombo.setVisibleRowCount(4);
    
    // Build time combo boxes
    ObservableList<MeetingTime> meetingTimes = FXCollections.observableArrayList(
        // Sort meeting time list by meeting time
        Lists.newArrayList(meetingTimeRepository.findAll()).stream()
            .sorted((m1, m2) -> m1.getMeetingTime()
                .compareTo(m2.getMeetingTime()))
            .collect(Collectors.toList())
    );
    startTimeCombo.setItems(meetingTimes);
    startTimeCombo.setVisibleRowCount(4);
    endTimeCombo.setItems(meetingTimes);
    endTimeCombo.setVisibleRowCount(4);
    
    // Build room combo box
    ObservableList<Room> roomNumbers = FXCollections.observableArrayList(
        // Sort room list by room
        Lists.newArrayList(roomRepository.findAll()).stream()
            .sorted((r1, r2) -> r1.getRoomNumber()
                .compareTo(r2.getRoomNumber()))
            .collect(Collectors.toList())
    );
    roomCombo.setItems(roomNumbers);
    roomCombo.setVisibleRowCount(4);
    
    // Build day combo box
    ObservableList<String> days = FXCollections.observableArrayList(
        "M", "T", "W", "TH", "F", "S", "SU"
    );
    dayCombo.setItems(days);
    dayCombo.setVisibleRowCount(4);
  }
  
}
