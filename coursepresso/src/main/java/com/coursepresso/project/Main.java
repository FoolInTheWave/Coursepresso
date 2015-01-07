package com.coursepresso.project;

import com.coursepresso.project.controller.MainController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(MainConfig.class);
    
    MainController mainPresenter = context.getBean(MainController.class);
    mainPresenter.showLogin();
    
    Scene scene = new Scene(mainPresenter.getView(), 1440, 900);
    scene.getStylesheets().add("styles/Styles.css");
    
    stage.setScene(scene);
    stage.setTitle("Coursepresso - Course Schedule Management");
    stage.show();
  }
}
