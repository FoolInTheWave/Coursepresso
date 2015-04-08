package com.coursepresso.project.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javax.inject.Inject;
import org.springframework.security.core.userdetails.User;

/**
 * FXML Controller class
 *
 * @author Steve
 */
public class ViewUsersController implements Initializable {

  @FXML
  private Node root;
  @FXML
  private TableColumn<User, String> usernameColumn;
  @FXML
  private TableColumn<User, String> authorityColumn;
  @FXML
  private Button deleteUserButton;
  @FXML
  private Button editUserButton;
  @FXML
  private Button backButton;
  @FXML
  private TableView<User> userTable;

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

  @FXML
  void editUserButtonClick(ActionEvent event) {

  }

  @FXML
  void deleteUserButtonClick(ActionEvent event) {

  }

  @FXML
  void backButtonClick(ActionEvent event) {

  }
}
