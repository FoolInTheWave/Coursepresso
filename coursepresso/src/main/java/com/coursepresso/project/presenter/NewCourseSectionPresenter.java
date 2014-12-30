package com.coursepresso.project.presenter;

import com.coursepresso.project.entity.MeetingDay;
import com.coursepresso.project.entity.MeetingTime;
import com.coursepresso.project.repository.CourseSectionRepository;
import com.coursepresso.project.repository.MeetingTimeRepository;
import com.google.common.collect.Lists;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
public class NewCourseSectionPresenter implements Initializable {
  
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

  @Inject
  private CourseSectionRepository courseSectionRepository;
  @Inject
  private MeetingTimeRepository meetingTimeRepository;
  @Inject
  private MainPresenter mainPresenter;

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
    // Build table view
    meetingDayTable = new TableView<>();
    meetingDayTable.getItems().add(new MeetingDay());
    
    // Build time combo boxes
    ArrayList<MeetingTime> meetingTimes = Lists.newArrayList(
            meetingTimeRepository.findAll()
    );
    startTimeColumn.setCellFactory(ComboBoxTableCell.forTableColumn(meetingTimes));
    endTimeColumn.setCellFactory(ComboBoxTableCell.forTableColumn(meetingTimes));
  }
}
