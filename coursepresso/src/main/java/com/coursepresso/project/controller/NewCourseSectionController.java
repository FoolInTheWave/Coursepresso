package com.coursepresso.project.controller;

import com.coursepresso.project.entity.MeetingDay;
import com.coursepresso.project.entity.MeetingTime;
import com.coursepresso.project.repository.CourseSectionRepository;
import com.coursepresso.project.repository.MeetingTimeRepository;
import com.google.common.collect.Lists;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
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
  private ChoiceBox departmentChoice;
  @FXML
  private ChoiceBox courseNumberChoice;
  @FXML
  private TextField titleField;
  @FXML
  private ChoiceBox instructorChoice;
  @FXML
  private TextField sectionField;
  @FXML
  private TextField capacityField;
  @FXML
  private ChoiceBox typeChoice;
  @FXML
  private ChoiceBox termChoice;
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
  private ChoiceBox startTimeChoice;
  @FXML
  private ChoiceBox endTimeChoice;
  @FXML
  private ChoiceBox roomChoice;
  @FXML
  private ChoiceBox dayChoice;
  @FXML
  private Button addDayButton;
  @FXML
  private Button submitCourseButton;
  @FXML
  private Button backToListingButton;

  @Inject
  private CourseSectionRepository courseSectionRepository;
  @Inject
  private MeetingTimeRepository meetingTimeRepository;
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
    // Build time choice boxes
    ObservableList<Date> meetingTimes = FXCollections.observableArrayList(
        meetingTimeRepository.selectAllMeetingTimes()
    );
    startTimeChoice = new ChoiceBox(meetingTimes);
    endTimeChoice = new ChoiceBox(meetingTimes);
    
    // Build room choice box
  }
}
