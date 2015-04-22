package com.coursepresso.project.controller;

import com.coursepresso.project.Main;
import com.coursepresso.project.entity.*;
import com.coursepresso.project.helper.ImportFileHelper;
import com.coursepresso.project.helper.StringHelper;
import com.coursepresso.project.repository.*;
import com.coursepresso.project.service.ExportService;
import com.coursepresso.project.service.ImportService;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;
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
  private DepartmentRepository departmentRepository;
  @Inject
  private RoomRepository roomRepository;
  @Inject
  private TermRepository termRepository;

  private File file;

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
    Term term = termCombo.getValue();

    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Data Import");
    alert.setHeaderText(null);

    switch (table) {
      case "Appliances":
        importService.importAppliances(
            ImportFileHelper.readApplianceFile(file)
        );
        alert.setContentText("Appliances have been imported successfully!");
        break;
      case "Authorities":
        importService.importAuthorities(
            ImportFileHelper.readAuthorityFile(file)
        );
        alert.setContentText(
            "Authorities have been imported successfully!"
        );
        break;
      case "Course Prerequisites":
        importService.importCoursePrerequisites(
            ImportFileHelper.readCoursePrerequisiteFile(file)
        );
        alert.setContentText(
            "Course prerequisites have been imported successfully!"
        );
        break;
      case "Course Sections":
        if (!term.getTerm().equals("")) {
          importService.importCourseSections(
              ImportFileHelper.readCourseSectionFile(file, term)
          );
          alert.setContentText(
              "Course Sections have been imported successfully!"
          );
        }
        break;
      case "Courses":
        importService.importCourses(ImportFileHelper.readCourseFile(file));
        alert.setContentText("Courses have been imported successfully!");
        break;
      case "Departments":
        departmentRepository.save(ImportFileHelper.readDepartmentFile(file));
        alert.setContentText("Departments have been imported successfully!");
        break;
      case "Meeting Days":
        if (!term.getTerm().equals("")) {
          importService.importMeetingDays(
              ImportFileHelper.readMeetingDayFile(file, term)
          );
          alert.setContentText("Meeting days have been imported successfully!");
        }
        break;
      case "Professors":
        importService.importProfessors(
            ImportFileHelper.readProfessorFile(file)
        );
        alert.setContentText("Professors have been imported successfully!");
        break;
      case "Rooms":
        roomRepository.save(ImportFileHelper.readRoomFile(file));
        alert.setContentText("Rooms have been imported successfully!");
        break;
      case "Terms":
        termRepository.save(ImportFileHelper.readTermFile(file));
        alert.setContentText("Terms have been imported successfully!");
        break;
      case "Users":
        importService.importUsers(ImportFileHelper.readUserFile(file));
        alert.setContentText("Users have been imported successfully!");
        break;
      default:
        alert.setContentText("No data was imported!");
        break;
    }

    alert.showAndWait();
  }

  public void buildView() {
    // Build rooms combo box
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

}
