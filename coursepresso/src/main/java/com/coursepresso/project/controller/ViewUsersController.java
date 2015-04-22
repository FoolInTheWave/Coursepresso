package com.coursepresso.project.controller;

import com.coursepresso.project.entity.Authority;
import com.coursepresso.project.entity.User;
import com.coursepresso.project.repository.UserRepository;
import com.google.common.base.Joiner;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FXML Controller class
 *
 * @author Steve Foco, Caleb Miller
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
  private Button createUserButton;
  @FXML
  private Button backButton;
  @FXML
  private TableView<User> userTable;

  @Inject
  private MainController mainController;
  @Inject
  private UserRepository userRepository;

  private ObservableList<User> users;

  private static final Logger log = LoggerFactory.getLogger(
          ViewUsersController.class
  );

  public Node getView() {
    return root;
  }

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

    // setCellValueFactory to display a delimited string of user authorities
    authorityColumn.setCellValueFactory(
            user -> {
              SimpleStringProperty property = new SimpleStringProperty();
              List<String> authorities = new ArrayList<>();

              for (Authority authority : user.getValue().getAuthorityList()) {
                authorities.add(authority.getAuthority());
              }

              // Generate delimited string of authorities at the cell value
              property.setValue(
                      Joiner.on(", ").join(authorities)
              );
              return property;
            }
    );

    // Initialize the observable list and table view
    users = FXCollections.observableArrayList();
    userTable.setItems(users);
  }

  @FXML
  void editUserButtonClick(ActionEvent event) {
    if (userTable.getSelectionModel().getSelectedItem() != null) {
      mainController.showEditUser(
              userTable.getSelectionModel().getSelectedItem()
      );
    }
  }

  @FXML
  void deleteUserButtonClick(ActionEvent event) {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle(null);
    alert.setHeaderText("Warning");
    alert.setContentText("Are you sure you want to delete this user?");

    Optional<ButtonType> result = alert.showAndWait();

    if (result.get() == ButtonType.OK) {
      userRepository.delete(
              userTable.getSelectionModel().getSelectedItem().getUsername()
      );
      users.remove(userTable.getSelectionModel().getSelectedItem());

      alert = new Alert(AlertType.INFORMATION);
      alert.setTitle(null);
      alert.setHeaderText(null);
      alert.setContentText("User deleted successfully!");

      alert.showAndWait();
    }
  }

  @FXML
  void createUserButtonClick(ActionEvent event) {
    mainController.showCreateUser();
  }

  @FXML
  void backButtonClick(ActionEvent event) {
    mainController.showMenu();
  }

  public void buildView() {
    users.clear();
    users.addAll(userRepository.findAllWithAuthorities());

    userTable.getSelectionModel().selectFirst();
  }
}
