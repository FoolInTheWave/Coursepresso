package com.coursepresso.project;

import com.coursepresso.project.controller.*;
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
  public AuthenticationController authenticationController() {
    return loadController("/fxml/Authentication.fxml");
  }

  @Bean
  public ConflictController conflictController() {
    return loadController("/fxml/Conflict.fxml");
  }

  @Bean
  public AdminMenuController adminMenuController() {
    return loadController("/fxml/AdminMenu.fxml");
  }

  @Bean
  public UserMenuController userMenuController() {
    return loadController("/fxml/UserMenu.fxml");
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
  public ViewSchedulesController viewSchedulesController() {
    return loadController("/fxml/ViewSchedules.fxml");
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

  @Bean
  public CreateUserController createUserController() {
    return loadController("/fxml/CreateUser.fxml");
  }

  @Bean
  public ViewUsersController viewUsersController() {
    return loadController("/fxml/ViewUsers.fxml");
  }

  @Bean
  public EditUserController editUserController() {
    return loadController("/fxml/EditUser.fxml");
  }

  @Bean
  public ExportDataController exportDataController() {
    return loadController("/fxml/ExportData.fxml");
  }

  @Bean
  public ImportDataController importDataController() {
    return loadController("/fxml/ImportData.fxml");
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
