package com.coursepresso.project.controller;

import com.coursepresso.project.entity.Course;
import com.coursepresso.project.entity.CourseSection;
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
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
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
  private TableColumn<MeetingDay, String> startTimeColumn;
  @FXML
  private TableColumn<MeetingDay, String> endTimeColumn;
  @FXML
  private TableColumn<MeetingDay, String> roomColumn;
  @FXML
  private TableColumn<MeetingDay, String> dayColumn;
  @FXML
  private TableColumn<MeetingDay, String> instructorColumn;
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
    // setCellValueFactory to display the formatted time
    startTimeColumn.setCellValueFactory(
        time -> {
          SimpleStringProperty property = new SimpleStringProperty();
          DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
          property.setValue(dateFormat.format(time.getValue().getStartTime()));
          return property;
        }
    );
    // setCellFactory to display the formatted time
    endTimeColumn.setCellValueFactory(
        time -> {
          SimpleStringProperty property = new SimpleStringProperty();
          DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
          property.setValue(dateFormat.format(time.getValue().getEndTime()));
          return property;
        }
    );
    roomColumn.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
    dayColumn.setCellValueFactory(new PropertyValueFactory<>("day"));
    // A meeting day needs to be associated with one professor and a professor will
    // have a list of possibly many meeting days
    instructorColumn.setCellValueFactory(new PropertyValueFactory<>("professor"));
    
    // Initialize the meeting day observable list and table view
    meetingDays = FXCollections.observableArrayList();
    meetingDayTable.setItems(meetingDays);
  }
  
  public Node getView() {
    return root;
  }
  
  @FXML
  private void saveCourseSection(ActionEvent event) {
    CourseSection courseSection = new CourseSection();
    
    courseSection.setCourseNumber((Course) courseNumberCombo.getValue());
    // CourseProfessorList
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
    
    if (course != null) {
      // Get course with course section list
      course = 
          courseRepository.findByCourseNumberAndFetchCourseSectionListEagerly(
              course.getCourseNumber()
          );
      
      titleField.setText(course.getTitle());
      
      if (course.getCourseSectionList() != null) {
        // Set the section number field with the next available number
        sectionField.setText(
            Integer.toString(
                course.getCourseSectionList().stream()
                    .min((cs1, cs2) -> Integer.compare(
                        cs1.getSectionNumber(), 
                        cs2.getSectionNumber()
                    )).get().getSectionNumber() + 1
            )
        );
      }
    }
    else {
      titleField.setText("");
      sectionField.setText("");
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
    day.setRoomNumber((Room) roomCombo.getValue());
    day.setDay(dayCombo.getValue().toString());
    
    meetingDays.add(day);
    
    // Set the capacity field to the room with the lowest capacity
    capacityField.setText(
        Integer.toString(
            // Find the room with the lowest capacity from the current day list
            meetingDays.stream()
                .min((m1, m2) -> Integer.compare(
                    m1.getRoomNumber().getCapacity(), 
                    m2.getRoomNumber().getCapacity()
                )).get().getRoomNumber().getCapacity()
        )
    );
    
    // Clear the combo boxes for meeting day
    startTimeCombo.setValue(null);
    endTimeCombo.setValue(null);
    roomCombo.setValue(null);
    dayCombo.setValue(null);
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
