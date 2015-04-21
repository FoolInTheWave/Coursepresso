package com.coursepresso.project.controller;

import com.coursepresso.project.Main;
import com.coursepresso.project.entity.Term;
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
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import org.slf4j.LoggerFactory;

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
  private ComboBox<Integer> yearCombo;
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
  @FXML
  private ComboBox statusCombo;

  @Inject
  private TermRepository termRepository;
  @Inject
  private MainController mainController;
  @Inject
  private CopyScheduleService copyScheduleService;
  @Inject
  private ImportScheduleService importScheduleService;

  private static File file;

  private static final org.slf4j.Logger log = LoggerFactory.getLogger(
      NewScheduleController.class
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
  private void backButtonClick(ActionEvent event) {
    mainController.showViewSchedules();
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

    // Set extension filters
    ArrayList<FileChooser.ExtensionFilter> extensions = new ArrayList<>();
    extensions.add(new FileChooser.ExtensionFilter("CSV (*.csv)", "*.csv"));
    extensions.add(new FileChooser.ExtensionFilter("Text (*.txt)", "*.txt"));
    fileChooser.getExtensionFilters().addAll(extensions);

    file = fileChooser.showOpenDialog(stage);
    if (file != null) {
      fileNameLabel.setText(file.toString());
    }
  }

  @FXML
  private void createScheduleButtonClick(ActionEvent event) {
    StringBuilder termName = new StringBuilder();

    // Build term object
    Term term = new Term();
    term.setYear(yearCombo.getValue());
    term.setSeason(semesterCombo.getValue());

    // Get last two digits of year integer
    termName.append(Integer.toString(yearCombo.getValue() % 100));
    termName.append("/");
    termName.append(semesterCombo.getValue().substring(0, 2).toUpperCase());

    term.setTerm(termName.toString());
    term.setStatus(statusCombo.getValue().toString());

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

    mainController.showViewSchedules();
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
      log.error("File not found: ", ex);
    } catch (IOException ex) {
      log.error("IO failure: ", ex);
    }

  }

  public void buildView() {
    // Build type combo box
    ObservableList<String> semesters = FXCollections.observableArrayList(
        "Fall", "Winter", "Spring", "Summer"
    );
    semesterCombo.setItems(semesters);
    semesterCombo.setVisibleRowCount(4);

    ObservableList<Integer> years = FXCollections.observableArrayList(
        2015, 2016, 2017, 2018, 2019, 2020
    );
    yearCombo.setItems(years);
    yearCombo.setVisibleRowCount(4);

    ObservableList<String> statuses = FXCollections.observableArrayList(
        "Open", "Closed"
    );
    statusCombo.setItems(statuses);
    statusCombo.setVisibleRowCount(4);

    // Build term combo box
    ObservableList<Term> terms = FXCollections.observableArrayList(
        Lists.newArrayList(termRepository.findAll())
    );
    termCombo.setItems(terms);
  }
}
