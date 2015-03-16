package com.coursepresso.project;

import com.coursepresso.project.controller.CourseSearchController;
import com.coursepresso.project.controller.ConflictController;
import com.coursepresso.project.controller.EditCourseSectionController;
import com.coursepresso.project.controller.LoginController;
import com.coursepresso.project.controller.MainController;
import com.coursepresso.project.controller.AdminMenuController;
import com.coursepresso.project.controller.NewCourseSectionController;
import com.coursepresso.project.controller.NewScheduleController;
import com.coursepresso.project.controller.ScheduleSelectionController;
import com.coursepresso.project.controller.SearchResultsController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Caleb Miller
 */
@Configuration
public class ControllerConfig {
  
  @Bean
  public MainController mainController() {
    return loadController("/fxml/Main.fxml");
  }

  @Bean
  public NewCourseSectionController newCourseSectionController() {
    return loadController("/fxml/NewCourseSection.fxml");
  }
  
  @Bean
  public LoginController loginController() {
    return loadController("/fxml/Login.fxml");
  }
  
  @Bean
  public ConflictController conflictController() {
    return loadController("/fxml/Conflict.fxml");
  }
  
  @Bean
  public AdminMenuController menuController() {
    return loadController("/fxml/Menu.fxml");
  }
  
  @Bean
  public CourseSearchController courseSearchController() {
    return loadController("/fxml/CourseSearch.fxml");
  }
    
  @Bean
  public ScheduleSelectionController scheduleSelectionController() {
    return loadController("/fxml/ScheduleSelection.fxml");
  }
  
  @Bean
  public SearchResultsController searchResultsController() {
    return loadController("/fxml/SearchResults.fxml");
  }
  
  @Bean
  public NewScheduleController newScheduleController() {
    return loadController("/fxml/NewSchedule.fxml");
  }
  
  @Bean
  public EditCourseSectionController editCourseSectionController() {
    return loadController("/fxml/EditCourseSection.fxml");
  }
  
  @SuppressWarnings("unchecked")
  private <T> T loadController(String fxmlFile) {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.load(getClass().getResourceAsStream(fxmlFile));
      return (T) loader.getController();
    } catch (IOException e) {
      throw new RuntimeException(
          String.format("Unable to load FXML file '%s'", fxmlFile), e
      );
    }
  }
}
