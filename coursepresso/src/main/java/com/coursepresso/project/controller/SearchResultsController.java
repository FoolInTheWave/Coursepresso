package com.coursepresso.project.controller;

import com.coursepresso.project.repository.CourseProfessorRepository;
import com.coursepresso.project.repository.CourseSectionRepository;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javax.inject.Inject;

/**
 * FXML Controller class
 *
 * @author steev_000
 */
public class SearchResultsController implements Initializable {

  @FXML
  private Node root;
  @FXML
  private TableColumn<?, ?> courseNumCell;
  @FXML
  private TableColumn<?, ?> lineNumCell;
  @FXML
  private Button modifySectionButton;
  @FXML
  private TableColumn<?, ?> capacityCell;
  @FXML
  private TableColumn<?, ?> roomNumCell;
  @FXML
  private TableColumn<?, ?> startTimeCell;
  @FXML
  private Button backButton;
  @FXML
  private TableColumn<?, ?> daysCell;
  @FXML
  private TableColumn<?, ?> courseNameCell;
  @FXML
  private TableColumn<?, ?> professorCell;
  @FXML
  private TableColumn<?, ?> endTimeCell;
  @FXML
  private TableColumn<?, ?> studentsCell;

  @Inject
  private MainController mainController;
  @Inject
  private CourseSectionRepository courseSectionRepository;
  @Inject
  private CourseProfessorRepository courseProfessorRepository;
    
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
    mainController.showCourseSearch();
  }

  @FXML
  void modifySectionButtonClick(ActionEvent event) {

  }
  
  public void buildView() {
    
  }
}
