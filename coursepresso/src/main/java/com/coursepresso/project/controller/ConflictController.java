package com.coursepresso.project.controller;

import com.coursepresso.project.entity.CourseSection;
import com.coursepresso.project.entity.Term;
import com.coursepresso.project.entity.Conflict;
import com.coursepresso.project.repository.CourseSectionRepository;
import com.coursepresso.project.service.ConflictService;
import java.net.URL;
import java.util.List;
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

/**
 * FXML Controller class
 *
 * @author Caleb Miller, Steve Foco
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
  
  public Node getView() {
    return root;
  }
  
  private static List<String> conflictsStr;
  private ObservableList<Conflict> conflicts;
  
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
                  conflict.getValue().getSection().getId().toString()
          );
          return property;
        }        
    );
    courseColumn.setCellValueFactory(
        conflict -> {
          SimpleStringProperty property = new SimpleStringProperty();
          property.setValue(
                  conflict.getValue().getSection().getCourse().getTitle()
          );
          return property;
        } 
    );
    sectionColumn.setCellValueFactory(
        conflict -> {
          SimpleStringProperty property = new SimpleStringProperty();
          property.setValue(
                  conflict.getValue().getSection().toString()
          );
          return property;
        } 
    );
    reasonColumn.setCellValueFactory(
        new PropertyValueFactory<Conflict, String>("reason")
    );
    
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
    mainController.showEditCourseSection(
        conflictTable.getSelectionModel().getSelectedItem().getSection(),
        "CONFLICT"
    );
  }
  
  public void buildView(Term selectedTerm) {
    CourseSection cs1, cs2;
    conflicts.clear();
    
    conflictsStr = conflictService.getConflicts(selectedTerm);
    
    System.out.println(conflictsStr);
    
    for(String conflict : conflictsStr) {
      String[] sections = conflict.split("#");
      
      System.out.println(sections[0] + " " + sections[1]);
      
      cs1 = courseSectionRepository.findByIdWithCourse(
          Integer.parseInt(sections[0])
      );
      cs2 = courseSectionRepository.findByIdWithCourse(
          Integer.parseInt(sections[1])
      );
      
      conflicts.add(new Conflict(cs1, cs2.getId().toString()));
    }
    
    numberLabel.setText(conflicts.size() + " Conflicts Found");
    
    conflictTable.setItems(conflicts);
  }
}
