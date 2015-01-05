package com.coursepresso.project.controller;

import com.coursepresso.project.entity.Course;
import com.coursepresso.project.entity.Department;
import com.coursepresso.project.entity.MeetingDay;
import com.coursepresso.project.entity.MeetingTime;
import com.coursepresso.project.entity.Professor;
import com.coursepresso.project.entity.Room;
import com.coursepresso.project.entity.Term;
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
import java.util.Date;
import java.util.ResourceBundle;
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
  private void courseNumberComboSelect(ActionEvent event) {
    Course course = (Course) courseNumberCombo.getValue();
    
    if (course != null)
      titleField.setText(course.getTitle());
    else
      titleField.setText("");
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
        Lists.newArrayList(departmentRepository.findAll())
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
        Lists.newArrayList(termRepository.findAll())
    );
    termCombo.setItems(terms);
    termCombo.setVisibleRowCount(4);
    
    // Build time combo boxes
    ObservableList<MeetingTime> meetingTimes = FXCollections.observableArrayList(
        Lists.newArrayList(meetingTimeRepository.findAll())
    );
    startTimeCombo.setItems(meetingTimes);
    startTimeCombo.setVisibleRowCount(4);
    endTimeCombo.setItems(meetingTimes);
    endTimeCombo.setVisibleRowCount(4);
    
    // Build room combo box
    ObservableList<Room> roomNumbers = FXCollections.observableArrayList(
        Lists.newArrayList(roomRepository.findAll())
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
