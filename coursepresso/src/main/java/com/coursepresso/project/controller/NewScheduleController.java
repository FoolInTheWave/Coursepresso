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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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

  private static File file;

  @FXML
  private void backButtonClick(ActionEvent event) {
    mainController.showMenu();
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

    termRepository.save(term);

    if (fileNameLabel.getText().isEmpty()) {
      importSections(term);
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

  public void importSections(Term term) {

    BufferedReader br = null;
    String line = "";
    String splitBy = ",";

    try {
      br = new BufferedReader(new FileReader(file));

      while ((line = br.readLine()) != null) {
        String[] column = line.split(splitBy);

        CourseSection courseSection = new CourseSection();

        String[] courseNum = column[0].split("*");
        courseSection.setCourseNumber(
            courseRepository.findByCourseNumber(courseNum[0] + courseNum[1])
        );

        courseSection.setSectionNumber(Integer.parseInt(column[4]));
        courseSection.setAvailable(true);
        courseSection.setCapacity(Integer.parseInt(column[3]));
        courseSection.setSeatsAvailable(courseSection.getCapacity());
        courseSection.setStatus("Open");
        courseSection.setTerm(term);
        courseSection.setStudentCount(Integer.parseInt(column[2]));
        courseSection.setType(column[6]);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        courseSection.setStartDate(formatter.parse(column[10]));
        courseSection.setEndDate(formatter.parse(column[11]));

        courseSection.setDepartment(
            departmentRepository.findByAbbreviation(courseNum[0])
        );

        courseSection.setProfessorId(
            professorRepository.findById(column[1])
        );

        courseSection = courseSectionRepository.save(courseSection);

        MeetingDay day = new MeetingDay();

        DateFormat df = new SimpleDateFormat("hh:mm a");
        String[] times = column[12].split("-");
        day.setStartTime(df.parse(times[0]));
        day.setEndTime(df.parse(times[1]));

        String[] rooms = column[9].split(",");
        day.setRoomNumber(
            roomRepository.findByRoomNumber(rooms[0])
        );

        // Save MeetingDays for CourseSection
        day.setCourseSectionId(courseSection);
        day.setTerm(courseSection.getTerm());

        meetingDayRepository.save(day);

      }
    } catch (FileNotFoundException ex) {
      Logger.getLogger(NewScheduleController.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException | ParseException ex) {
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
  }
}
