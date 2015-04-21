package com.coursepresso.project.controller;

import com.coursepresso.project.Main;
import com.coursepresso.project.entity.Term;
import com.coursepresso.project.helper.StringHelper;
import com.coursepresso.project.repository.TermRepository;
import com.coursepresso.project.service.ExportService;
import com.google.common.collect.Lists;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FXML Controller class
 *
 * @author Caleb Miller
 */
public class ExportDataController implements Initializable {

  private static final Logger log = LoggerFactory.getLogger(
      ExportDataController.class
  );

  @FXML
  private Node root;
  @FXML
  private TextArea previewArea;
  @FXML
  private ComboBox<String> tableCombo;
  @FXML
  private ComboBox<Term> termCombo;
  @FXML
  private Button exportButton;
  @FXML
  private Button backButton;

  @Inject
  private MainController mainController;
  @Inject
  private ExportService exportService;
  @Inject
  private TermRepository termRepository;

  private String data;

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
  void tableComboSelect(ActionEvent event) {
    String table = (tableCombo.getValue() != null) ? tableCombo.getValue() : "";
    String term = (termCombo.getValue() != null) ? termCombo.getValue().getTerm() : "";
    data = "";

    switch (table) {
      case "Appliances":
        data = exportService.exportAppliances();
        break;
      case "Authorities":
        data = exportService.exportAuthorities();
        break;
      case "Course Prerequisites":
        data = exportService.exportCoursePrerequisites();
        break;
      case "Course Sections":
        data = exportService.exportCourseSections(term);
        break;
      case "Courses":
        data = exportService.exportCourses();
        break;
      case "Departments":
        data = exportService.exportDepartments();
        break;
      case "Group Authorities":
        data = exportService.exportGroupAuthorities();
        break;
      case "Group Members":
        data = exportService.exportGroupMembers();
        break;
      case "Groups":
        data = exportService.exportGroups();
        break;
      case "Meeting Days":
        data = exportService.exportMeetingDays(term);
        break;
      case "Professors":
        data = exportService.exportProfessors();
        break;
      case "Rooms":
        data = exportService.exportRooms();
        break;
      case "Terms":
        data = exportService.exportTerms();
        break;
      case "Users":
        data = exportService.exportUsers();
        break;
    }

    previewArea.setText(data);
  }

  @FXML
  void termComboSelect(ActionEvent event) {
    String table = (tableCombo.getValue() != null) ? tableCombo.getValue() : "";
    String term = (termCombo.getValue() != null) ? termCombo.getValue().getTerm() : "";
    data = "";

    switch (table) {
      case "Course Sections":
        data = exportService.exportCourseSections(term);
        break;
      case "Meeting Days":
        data = exportService.exportMeetingDays(term);
        break;
    }

    previewArea.setText(data);
  }

  @FXML
  void exportButtonClick(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    Stage stage = (Stage) Main.getScene().getWindow();

    // Set extension filters
    ArrayList<ExtensionFilter> extensions = new ArrayList<>();
    extensions.add(new ExtensionFilter("CSV (*.csv)", "*.csv"));
    extensions.add(new ExtensionFilter("Text (*.txt)", "*.txt"));
    fileChooser.getExtensionFilters().addAll(extensions);

    // Show save file dialog
    File file = fileChooser.showSaveDialog(stage);

    if (file != null) {
      SaveFile(data, file);
    }
  }

  @FXML
  void backButtonClick(ActionEvent event) {
    mainController.showMenu();
  }

  public void buildView() {
    // Build terms combo box
    ObservableList<Term> terms = FXCollections.observableArrayList(
        Lists.newArrayList(termRepository.findAll())
    );
    terms.add(0, null);
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

  private void SaveFile(String content, File file) {
    try {
      FileWriter fileWriter = null;

      fileWriter = new FileWriter(file);
      fileWriter.write(content);
      fileWriter.close();
    } catch (IOException e) {
      log.error("Save file failed: ", e);
    }

  }

}
