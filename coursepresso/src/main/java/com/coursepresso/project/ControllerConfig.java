package com.coursepresso.project;

import com.coursepresso.project.controller.CourseSearchController;
import com.coursepresso.project.controller.LoginController;
import com.coursepresso.project.controller.MainController;
import com.coursepresso.project.controller.MenuController;
import com.coursepresso.project.controller.NewCourseSectionController;
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
  public MenuController menuController() {
    return loadController("/fxml/Menu.fxml");
  }
  
  @Bean
  public CourseSearchController courseSearchController() {
    return loadController("/fxml/CourseSearch.fxml");
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
