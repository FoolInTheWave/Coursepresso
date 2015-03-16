package com.coursepresso.project.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javax.inject.Inject;

/**
 * FXML Controller class
 *
 * @author Steve
 */
public class UserMenuController implements Initializable {

  @FXML
  private Node root;
  
  
  @Inject
  private MainController mainController;

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

}
