package com.coursepresso.project;

import com.coursepresso.project.presenter.MainPresenter;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CoursepressoApp extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(CoursepressoConfig.class);
    
    MainPresenter mainPresenter = context.getBean(MainPresenter.class);
    mainPresenter.showNewCourseSection();
    
    Scene scene = new Scene(mainPresenter.getView(), 1280, 800);
    scene.getStylesheets().add("styles/Styles.css");
    
    stage.setScene(scene);
    stage.setTitle("Coursepresso - Course Schedule Management");
    stage.show();
  }
}
