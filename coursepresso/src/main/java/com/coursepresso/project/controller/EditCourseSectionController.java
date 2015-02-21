/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coursepresso.project.controller;

import com.coursepresso.project.entity.Course;
import com.coursepresso.project.entity.CourseSection;
import com.coursepresso.project.entity.Department;
import com.coursepresso.project.entity.MeetingDay;
import com.coursepresso.project.entity.MeetingTime;
import com.coursepresso.project.entity.Professor;
import com.coursepresso.project.entity.Room;
import com.coursepresso.project.entity.Term;
import com.coursepresso.project.repository.CourseSectionRepository;
import com.coursepresso.project.repository.DepartmentRepository;
import com.coursepresso.project.repository.MeetingDayRepository;
import com.coursepresso.project.repository.MeetingTimeRepository;
import com.coursepresso.project.repository.RoomRepository;
import com.coursepresso.project.repository.TermRepository;
import com.google.common.collect.Lists;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javax.inject.Inject;


/**
 * FXML Controller class
 *
 * @author steev_000
 */
public class EditCourseSectionController implements Initializable {
  
  @FXML
  private Node root;
  @FXML
  private ComboBox departmentCombo;
  @FXML
  private ComboBox courseNumberCombo;
  @FXML
  private TextField titleField;
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
  private ComboBox startTimeCombo;
  @FXML
  private ComboBox endTimeCombo;
  @FXML
  private ComboBox roomCombo;
  @FXML
  private ComboBox dayCombo;
  @FXML
  private ComboBox instructorCombo;
  @FXML
  private Button addDayButton;
  @FXML
  private Button submitChangesButton;
  @FXML
  private Button backButton;

  @Inject
  private CourseSectionRepository courseSectionRepository;
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
    mainController.showSearchResults();
  }
  
  @FXML
  private void submitChangesButtonClick(ActionEvent event) {
    
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
  
  @FXML
  private void courseNumberComboSelect(ActionEvent event) {
    Course course = (Course) courseNumberCombo.getValue();

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
  
  @FXML
  private void departmentComboSelect(ActionEvent event) {
    
  }
  
  public void buildView(CourseSection cs) {
    // Build department combo box
    departmentCombo.getSelectionModel().select(cs.getDepartment());
    
    // Build course number combo box
    Department department = (Department) departmentCombo.getValue();
    
    ObservableList<Course> courses = FXCollections.observableArrayList(
        // Get course list for selected department
        department.getCourseList()
    );
    courseNumberCombo.setItems(courses);
    courseNumberCombo.setVisibleRowCount(4);
    courseNumberCombo.getSelectionModel().select(cs.getCourseNumber());
    
    // Fill course title textbox
    Course course = (Course) courseNumberCombo.getValue();
    titleField.setText(course.getTitle());
    
    // Fill section text box
    sectionField.setText(Integer.toString(cs.getSectionNumber()));

    // Build professor combo box
    ObservableList<Professor> professors = FXCollections.observableArrayList(
        // Get professor list for selected department
        department.getProfessorList()
    );
    instructorCombo.setItems(professors);
    instructorCombo.setVisibleRowCount(4); 
    instructorCombo.getSelectionModel().select(cs.getProfessorId());
    
    // Build type combo box
    ObservableList<String> types = FXCollections.observableArrayList(
        "HYB", "LEC", "ONL"
    );
    typeCombo.setItems(types);
    typeCombo.setVisibleRowCount(4);
    typeCombo.getSelectionModel().select(cs.getType());

    // Build term combo box
    ObservableList<Term> terms = FXCollections.observableArrayList(
        Lists.newArrayList(termRepository.findAll())
    );
    termCombo.setItems(terms);
    termCombo.setVisibleRowCount(4);
    termCombo.getSelectionModel().select(cs.getTerm());
    
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
    //roomCombo.getSelectionModel().select(cs.getMeetingDayList().get(index)

    // Build day combo box
    ObservableList<String> days = FXCollections.observableArrayList(
        "M", "T", "W", "TH", "F", "S", "SU"
    );
    dayCombo.setItems(days);
    dayCombo.setVisibleRowCount(4);
    //dayCombo.getSelectionModel().select(cs.getMeetingDayList().get(1).getDay());
  }
  
}