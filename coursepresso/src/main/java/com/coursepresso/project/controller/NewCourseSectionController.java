package com.coursepresso.project.controller;

import com.coursepresso.project.repository.CourseSectionRepository;
import com.coursepresso.project.repository.DepartmentRepository;
import com.coursepresso.project.repository.MeetingTimeRepository;
import com.coursepresso.project.repository.RoomRepository;
import com.coursepresso.project.repository.TermRepository;
import java.net.URL;
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
  private TableView meetingDayTable;
  @FXML
  private TableColumn startTimeColumn;
  @FXML
  private TableColumn endTimeColumn;
  @FXML
  private TableColumn roomColumn;
  @FXML
  private TableColumn dayColumn;
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

  /**
   * Initializes the controller class.
   * 
   * @param url
   * @param rb
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    //TODO
  }
  
  public Node getView() {
    return root;
  }
  
  public void save(ActionEvent event) {
    // TODO
  }
  
  public void buildView() {
    // Build department combo box
    ObservableList<String> departments = FXCollections.observableArrayList(
        departmentRepository.selectAllNames()
    );
    departmentCombo.setItems(departments);
    departmentCombo.setVisibleRowCount(4);
    
    // Build type combo box
    ObservableList<String> types = FXCollections.observableArrayList(
        "LEC", "ONL", "HYB"
    );
    typeCombo.setItems(types);
    typeCombo.setVisibleRowCount(4);
    
    // Build term combo box
    ObservableList<String> terms = FXCollections.observableArrayList(
        termRepository.selectAllTerms()
    );
    termCombo.setItems(terms);
    termCombo.setVisibleRowCount(4);
    
    // Build time combo boxes
    ObservableList<Date> meetingTimes = FXCollections.observableArrayList(
        meetingTimeRepository.selectAllMeetingTimes()
    );
    startTimeCombo.setItems(meetingTimes);
    startTimeCombo.setVisibleRowCount(4);
    endTimeCombo.setItems(meetingTimes);
    endTimeCombo.setVisibleRowCount(4);
    
    // Build room combo box
    ObservableList<String> roomNumbers = FXCollections.observableArrayList(
        roomRepository.selectAllRoomNumbers()
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
