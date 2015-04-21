package com.coursepresso.project.controller;

import com.coursepresso.project.Main;
import com.coursepresso.project.entity.*;
import com.coursepresso.project.helper.StringHelper;
import com.coursepresso.project.repository.*;
import com.coursepresso.project.service.ExportService;
import com.coursepresso.project.service.ImportService;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.inject.Inject;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FXML Controller class
 *
 * @author Caleb Miller
 */
public class ImportDataController implements Initializable {

  @FXML
  private Node root;
  @FXML
  private ComboBox<Term> termCombo;
  @FXML
  private ComboBox<String> tableCombo;
  @FXML
  private TextArea previewArea;
  @FXML
  private Button backButton;
  @FXML
  private Button chooseFileButton;
  @FXML
  private Button importButton;

  @Inject
  private MainController mainController;
  @Inject
  private ExportService exportService;
  @Inject
  private ImportService importService;
  @Inject
  private TermRepository termRepository;

  File file;

  private static final Logger log = LoggerFactory.getLogger(
      ImportDataController.class
  );

  public Node getView() {
    return root;
  }

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }

  @FXML
  void backButtonClick(ActionEvent event) {
    mainController.showMenu();
  }

  @FXML
  void getImportData(ActionEvent event) {

  }

  @FXML
  void chooseFileButtonClick(ActionEvent event) {
    final FileChooser fileChooser = new FileChooser();
    Stage stage = (Stage) Main.getScene().getWindow();

    // Set extension filters
    ArrayList<FileChooser.ExtensionFilter> extensions = new ArrayList<>();
    extensions.add(new FileChooser.ExtensionFilter("CSV (*.csv)", "*.csv"));
    extensions.add(new FileChooser.ExtensionFilter("Text (*.txt)", "*.txt"));
    fileChooser.getExtensionFilters().addAll(extensions);

    File chosenFile = fileChooser.showOpenDialog(stage);
    if (chosenFile != null) {
      file = chosenFile;

      try {
        previewArea.setText(
            Joiner.on('\n').join(
                Files.readAllLines(
                    Paths.get(file.getAbsolutePath())
                )
            )
        );
      } catch (IOException ex) {
        log.error("IO failure: ", ex);
      }
    }
  }

  @FXML
  void importButtonClick(ActionEvent event) {
    String table = (tableCombo.getValue() != null) ? tableCombo.getValue() : "";
    String term = (termCombo.getValue() != null)
        ? termCombo.getValue().getTerm() : "";
    String data = "";

    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Data Import");
    alert.setHeaderText(null);

    switch (table) {
      case "Appliances":
        alert.setContentText("Appliances have been imported successfully!");
        break;
      case "Authorities":
        alert.setContentText("Authorities have been imported successfully!");
        break;
      case "Course Prerequisites":
        alert.setContentText("Course Prerequisites have been imported successfully!");
        break;
      case "Course Sections":
        importService.importCourseSections(readCourseSectionFile());
        alert.setContentText("Course Sections have been imported successfully!");
        break;
      case "Courses":
        alert.setContentText("Courses have been imported successfully!");
        break;
      case "Departments":
        alert.setContentText("Departments have been imported successfully!");
        break;
      case "Group Authorities":
        alert.setContentText("Group Authorities have been imported successfully!");
        break;
      case "Group Members":
        alert.setContentText("Group Members have been imported successfully!");
        break;
      case "Groups":
        alert.setContentText("Groups have been imported successfully!");
        break;
      case "Meeting Days":
        alert.setContentText("Meeting Days have been imported successfully!");
        break;
      case "Professors":
        alert.setContentText("Professors have been imported successfully!");
        break;
      case "Rooms":
        alert.setContentText("Rooms have been imported successfully!");
        break;
      case "Terms":
        alert.setContentText("Terms have been imported successfully!");
        break;
      case "Users":
        alert.setContentText("Users have been imported successfully!");
        break;
    }

    alert.showAndWait();
  }

  public void buildView() {
    // Build terms combo box
    ObservableList<Term> terms = FXCollections.observableArrayList(
        Lists.newArrayList(termRepository.findAll())
    );
    terms.add(0, new Term(""));
    termCombo.setItems(terms);

    // Build table names combo box
    ArrayList<String> tableNames = new ArrayList<>();
    for (String name : exportService.getTableNames()) {
      name = name.replace("_", " ");
      name = StringHelper.toTitleCase(name);
      tableNames.add(name);
    }
    ObservableList<String> tables = FXCollections.observableArrayList(
        tableNames
    );
    tableCombo.setItems(tables);

    termCombo.getSelectionModel().clearSelection();
    tableCombo.getSelectionModel().clearSelection();
    previewArea.clear();
  }

  private List<CourseSection> readCourseSectionFile() {
    // Create the CSVFormat object
    CSVFormat format = CSVFormat.DEFAULT.withHeader().withDelimiter(';');
    List<CourseSection> sections = new ArrayList<>();
    SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy-MM-dd");

    // Initialize the CSVParser object
    if (file != null) {
      try (CSVParser parser = new CSVParser(new FileReader(file), format)) {

        for (CSVRecord record : parser) {
          CourseSection section = new CourseSection();
          section.setCourse(new Course("course_number"));
          section.setSectionNumber(Integer.parseInt(record.get(
              "section_number"
          )));
          section.setAvailable(Boolean.parseBoolean(record.get("available")));
          section.setCapacity(Integer.parseInt(record.get("capacity")));
          section.setSeatsAvailable(Integer.parseInt(record.get(
              "seats_available"
          )));
          section.setStatus(record.get("status"));
          section.setTerm(new Term(record.get("term")));
          section.setStudentCount(Integer.parseInt(record.get(
              "student_count"
          )));
          section.setType(record.get("type"));
          section.setStartDate(timestampFormat.parse(record.get("start_date")));
          section.setEndDate(timestampFormat.parse(record.get("end_date")));
          section.setDepartment(new Department(record.get("department")));
          section.setProfessor(new Professor(
              Integer.parseInt(record.get("professor_id"))
          ));
          sections.add(section);
        }

        // Close the parser
        parser.close();
      } catch (IOException ex) {
        log.error("IO failure: ", ex);
      } catch (ParseException ex) {
        log.error("Parse failure: ", ex);
      }
    }
    return sections;
  }

}
