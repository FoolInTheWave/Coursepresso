package com.coursepresso.project.controller;

import com.coursepresso.project.Main;
import com.coursepresso.project.entity.Term;
import com.coursepresso.project.helper.ImportFileHelper;
import com.coursepresso.project.repository.TermRepository;
import com.coursepresso.project.service.CopyScheduleService;
import com.coursepresso.project.service.ImportService;
import com.google.common.collect.Lists;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
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
  private ImportService importService;

  private File file;
  ObservableList<String> semesters;
  ObservableList<Integer> years;

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
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    StringBuilder sbErrors = validate();
    if (!(sbErrors.toString().equals(""))) {
      alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Errors");
      alert.setHeaderText(null);
      alert.setContentText(sbErrors.toString());
      alert.showAndWait();
    } else {

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
        try {
          importService.importCourseSections(
              ImportFileHelper.readCourseSectionFile(file, term)
          );
          alert.setTitle("Term Saved");
          alert.setHeaderText(null);
          alert.setContentText("The term has been saved successfully!");
        } catch (Exception ex) {
          alert.setTitle("Error");
          alert.setHeaderText("Import File Error");
          alert.setContentText(
              "Could not import file! Please check logs for error!"
          );
          alert.showAndWait();
          log.error("Import file error: ", ex);
        }
      } else if (copyRdo.isSelected()) {
        copyPrevious(term);
        alert.setTitle("Term Saved");
        alert.setHeaderText(null);
        alert.setContentText("The term has been saved successfully!");
      }

      alert.showAndWait();

      mainController.showViewSchedules();
    }
  }

  @FXML
  private void termComboClick(ActionEvent event) {
    String selectedSeason = semesterCombo.getSelectionModel().getSelectedItem();
    ObservableList<Term> terms = FXCollections.observableArrayList(
        Lists.newArrayList(termRepository.findAll())
    );

    for (Term term : terms) {
      if (term.getSeason().equals(selectedSeason)) {
        years.remove((Object) term.getYear());
      }
    }

    yearCombo.setItems(years);
  }

  @FXML
  private void yearComboClick(ActionEvent event) {
    int selectedYear = yearCombo.getSelectionModel().getSelectedItem();

    ObservableList<Term> terms = FXCollections.observableArrayList(
        Lists.newArrayList(termRepository.findAll())
    );

    for (Term term : terms) {
      if (term.getYear() == selectedYear) {
        semesters.remove(term.getSeason());
      }
    }

    semesterCombo.setItems(semesters);
  }

  public StringBuilder validate() {
    ArrayList<String> errors = new ArrayList();
    StringBuilder sbErrors = new StringBuilder();
    sbErrors.append("");

    if (semesterCombo.getSelectionModel().getSelectedItem() == null) {
      errors.add("Semester");
    }
    if (yearCombo.getSelectionModel().getSelectedItem() == null) {
      errors.add("Year");
    }
    if (statusCombo.getSelectionModel().getSelectedItem().equals("")) {
      errors.add("Status");
    }
    if (importRdo.isSelected()) {
      if (fileNameLabel.getText().equals("")) {
        errors.add("File to import");
      }
    } else if (copyRdo.isSelected()) {
      if (termCombo.getSelectionModel().getSelectedItem() == null) {
        errors.add("Term to copy");
      }
    }

    if (!errors.isEmpty()) {
      sbErrors.append("Please enter the following before submitting: " + '\n');

      for (String error : errors) {
        sbErrors.append('\t').append(error).append('\n');
      }
    }

    return sbErrors;
  }

  public void copyPrevious(Term newTerm) {
    Term prevTerm = termRepository.findByTermWithCourseSections(
        termCombo.getSelectionModel().getSelectedItem().toString()
    );

    copyScheduleService.copySchedule(prevTerm, newTerm);
  }

  public void buildView() {
    // Build type combo box
    semesters = FXCollections.observableArrayList(
        "Fall", "Winter", "Spring", "Summer"
    );
    semesterCombo.setItems(semesters);
    semesterCombo.setVisibleRowCount(4);
    semesterCombo.getSelectionModel().select(null);

    int year = Calendar.getInstance().get(Calendar.YEAR);

    years = FXCollections.observableArrayList(
        year - 1, year, year + 1, year + 2, year + 3, year + 4, year + 5
    );
    yearCombo.setItems(years);
    yearCombo.setVisibleRowCount(4);
    yearCombo.getSelectionModel().select(null);

    ObservableList<String> statuses = FXCollections.observableArrayList(
        "Open", "Closed"
    );
    statusCombo.setItems(statuses);
    statusCombo.setVisibleRowCount(4);
    statusCombo.getSelectionModel().select("");

    // Build term combo box
    ObservableList<Term> terms = FXCollections.observableArrayList(
        Lists.newArrayList(termRepository.findAll())
    );
    termCombo.setItems(terms);
  }
}
