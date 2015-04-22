package com.coursepresso.project.controller;

import com.coursepresso.project.entity.Term;
import com.coursepresso.project.entity.Conflict;
import com.coursepresso.project.entity.CourseSection;
import com.coursepresso.project.repository.CourseSectionRepository;
import com.coursepresso.project.service.ConflictService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FXML Controller class
 *
 * @author Steve Foco, Caleb Miller
 */
public class ConflictController implements Initializable {

  @FXML
  private Node root;
  @FXML
  private Label numberLabel;
  @FXML
  private TableView<Conflict> conflictTable;
  @FXML
  private TableColumn<Conflict, String> courseColumn;
  @FXML
  private TableColumn<Conflict, String> sectionColumn;
  @FXML
  private TableColumn<Conflict, String> lineNoColumn;
  @FXML
  private TableColumn<Conflict, String> reasonColumn;
  @FXML
  private Button backButton;
  @FXML
  private Button resolveManuallyButton;
  @FXML
  private Button resolveAutomaticallyButton;

  @Inject
  private MainController mainController;
  @Inject
  private ConflictService conflictService;
  @Inject
  private CourseSectionRepository courseSectionRepository;

  private ObservableList<Conflict> conflicts;

  private static final Logger log = LoggerFactory.getLogger(
      ConflictController.class
  );

  public Node getView() {
    return root;
  }

  /**
   * Initializes the controller class.
   *
   * @param url
   * @param rb
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    lineNoColumn.setCellValueFactory(
        conflict -> {
          SimpleStringProperty property = new SimpleStringProperty();
          property.setValue(
              conflict.getValue().getCourseSection().getId().toString()
          );
          return property;
        }
    );
    courseColumn.setCellValueFactory(
        conflict -> {
          SimpleStringProperty property = new SimpleStringProperty();
          property.setValue(
              conflict.getValue().getCourseSection().getCourse().getTitle()
          );
          return property;
        }
    );
    sectionColumn.setCellValueFactory(
        conflict -> {
          SimpleStringProperty property = new SimpleStringProperty();
          property.setValue(
              conflict.getValue().getCourseSection().toString()
          );
          return property;
        }
    );
    reasonColumn.setCellValueFactory(new PropertyValueFactory<>("reason"));

    // Initialize the conflicts observable list and table view
    conflicts = FXCollections.observableArrayList();
    conflictTable.setItems(conflicts);
  }

  @FXML
  private void backButtonClick(ActionEvent event) {
    mainController.showScheduleSelection();
  }

  @FXML
  private void resolveManuallyButtonClick(ActionEvent event) {
    // Fectch course section again to populate meeting day list
    CourseSection section = courseSectionRepository.findOne(
        conflictTable.getSelectionModel()
        .getSelectedItem().getCourseSection().getId()
    );
    // Call edit course section controller to edit section stored in conflict
    mainController.showEditCourseSection(section, "CONFLICT");
  }

  public void buildView(Term selectedTerm) {
    conflicts.setAll(conflictService.getConflicts(selectedTerm));

    numberLabel.setText(conflicts.size() + " Conflicts Found");
    
    conflictTable.getSelectionModel().selectFirst();
  }
}
