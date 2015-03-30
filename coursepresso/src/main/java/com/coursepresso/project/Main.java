package com.coursepresso.project;

import com.coursepresso.project.controller.MainController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main extends Application {

  public static Scene scene;
  
  public static void main(String[] args) {
    launch(args);
  }
  
  public static Scene getScene() {
    return scene;
  }

  @Override
  public void start(Stage stage) throws Exception {
    AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(MainConfig.class);
    
    MainController mainPresenter = context.getBean(MainController.class);
    mainPresenter.showMenu();
    
    scene = new Scene(mainPresenter.getView(), 1024, 720);
    scene.getStylesheets().add("styles/Styles.css");
    
    stage.setScene(scene);
    stage.setTitle("Coursepresso - Course Schedule Management");
    stage.show();
  }
}
