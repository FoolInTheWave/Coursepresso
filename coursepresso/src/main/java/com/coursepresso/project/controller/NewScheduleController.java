package com.coursepresso.project.controller;

import com.coursepresso.project.Main;
import com.coursepresso.project.entity.CourseSection;
import com.coursepresso.project.entity.MeetingDay;
import com.coursepresso.project.entity.Term;
import com.coursepresso.project.repository.CourseRepository;
import com.coursepresso.project.repository.CourseSectionRepository;
import com.coursepresso.project.repository.DepartmentRepository;
import com.coursepresso.project.repository.MeetingDayRepository;
import com.coursepresso.project.repository.ProfessorRepository;
import com.coursepresso.project.repository.RoomRepository;
import com.coursepresso.project.repository.TermRepository;
import com.coursepresso.project.service.CopyScheduleService;
import com.coursepresso.project.service.ImportScheduleService;
import com.google.common.collect.Lists;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.Pane;
import javax.inject.Inject;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author steev_000
 */
public class NewScheduleController implements Initializable {

  @FXML
  private Node root;
  @FXML
  private ComboBox<String> semesterCombo;
  @FXML
  private Button createScheduleButton;
  @FXML
  private Button backButton;
  @FXML
  private ComboBox<String> yearCombo;
  @FXML
  private Button chooseFileButton;
  @FXML
  private Label fileNameLabel;
  @FXML
  private RadioButton blankRdo;
  @FXML
  private RadioButton copyRdo;
  @FXML
  private RadioButton importRdo;
  @FXML
  private Label importLabel;
  @FXML
  private Pane importPane;
  @FXML
  private Pane copyPane;
  @FXML
  private ComboBox termCombo;
  @FXML
  private Group paneGroup;
  @FXML
  private ProgressBar progressBar;

  @Inject
  private TermRepository termRepository;
  @Inject
  private DepartmentRepository departmentRepository;
  @Inject
  private CourseRepository courseRepository;
  @Inject
  private CourseSectionRepository courseSectionRepository;
  @Inject
  private ProfessorRepository professorRepository;
  @Inject
  private MeetingDayRepository meetingDayRepository;
  @Inject
  private RoomRepository roomRepository;
  @Inject
  private MainController mainController;
  @Inject
  private CopyScheduleService copyScheduleService;
  @Inject
  private ImportScheduleService importScheduleService;

  private static File file;
  private ArrayList<MeetingDay> meetingDays;

  @FXML
  private void backButtonClick(ActionEvent event) {
    mainController.showMenu();
  }

  @FXML
  private void blankRdoSelected(ActionEvent event) {
    copyPane.setVisible(false);
    importPane.setVisible(false);
  }

  @FXML
  private void copyRdoSelected(ActionEvent event) {
    copyPane.setVisible(true);
    importPane.setVisible(false);
  }

  @FXML
  private void importRdoSelected(ActionEvent event) {
    importPane.setVisible(true);
    copyPane.setVisible(false);
  }

  @FXML
  private void chooseFileButtonClick(ActionEvent event) {
    final FileChooser fileChooser = new FileChooser();
    Stage stage = (Stage) Main.getScene().getWindow();

    file = fileChooser.showOpenDialog(stage);
    if (file != null) {
      fileNameLabel.setText(file.toString());
    }
  }

  @FXML
  private void createScheduleButtonClick(ActionEvent event) {
    Term term = new Term();
    String year = yearCombo.getValue();

    year = year.substring(year.length() - 2);
    String termName = year + "/" + semesterCombo.getValue();

    term.setTerm(termName);
    term.setStatus("Open");

    termRepository.save(term);

    if (importRdo.isSelected()) {
      importSections(term);
    } else if (copyRdo.isSelected()) {
      copyPrevious(term);
    }

    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Term Saved");
    alert.setHeaderText(null);
    alert.setContentText("The term has been saved successfully!");
    alert.showAndWait();

    mainController.showMenu();
  }

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

  public void copyPrevious(Term newTerm) {
    Term prevTerm = termRepository.findByTermWithCourseSections(
            termCombo.getSelectionModel().getSelectedItem().toString()
    );

    copyScheduleService.copySchedule(prevTerm, newTerm);
  }

  public void importSections(Term term) {
    String line = "";
  
    try {
      BufferedReader br = new BufferedReader(new FileReader(file));
      
      while ((line = br.readLine()) != null) {
        importScheduleService.importSchedule(term, line);
      }
    } catch (FileNotFoundException ex) {
      Logger.getLogger(NewScheduleController.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
      Logger.getLogger(NewScheduleController.class.getName()).log(Level.SEVERE, null, ex);
    }
  
  }

  public void buildView() {
    // Build type combo box
    ObservableList<String> semesters = FXCollections.observableArrayList(
            "FA", "WI", "SP", "SU"
    );
    semesterCombo.setItems(semesters);
    semesterCombo.setVisibleRowCount(4);

    // Build type combo box
    ObservableList<String> years = FXCollections.observableArrayList(
            "2015", "2016", "2017", "2018", "2019", "2020"
    );
    yearCombo.setItems(years);
    yearCombo.setVisibleRowCount(4);

    // Build term combo box
    ObservableList<Term> terms = FXCollections.observableArrayList(
            Lists.newArrayList(termRepository.findAll())
    );
    termCombo.setItems(terms);
  }
}
