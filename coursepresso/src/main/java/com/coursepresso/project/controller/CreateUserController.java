package com.coursepresso.project.controller;

import com.coursepresso.project.service.SecurityService;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.inject.Inject;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * FXML Controller class
 *
 * @author Caleb Miller
 */
public class CreateUserController implements Initializable {

  @FXML
  private Node root;
  @FXML
  private TextField usernameField;
  @FXML
  private Button backButton;
  @FXML
  private PasswordField passwordField;
  @FXML
  private ComboBox<String> authorityCombo;
  @FXML
  private Button createUserButton;
  @FXML
  private PasswordField confirmPasswordField;

  @Inject
  private MainController mainController;
  @Inject
  private SecurityService securityService;

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
  private void createUserButtonClick() {
    ArrayList<GrantedAuthority> authorities = new ArrayList<>();

    if (!usernameField.getText().equals("")) {
      if (!passwordField.getText().equals("")) {
        if (passwordField.getText().equals(confirmPasswordField.getText())) {
          if (authorityCombo.getValue() != null) {
            // Add user authorities to list
            authorities.add(
                new SimpleGrantedAuthority(authorityCombo.getValue())
            );

            // Hash the password
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(
                passwordField.getText()
            );

            // Create new user
            User user = new User(
                usernameField.getText(),
                hashedPassword,
                authorities
            );

            // Nofify user
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("New User Created");
            alert.setHeaderText(null);
            alert.setContentText("The new user account has been created successfully!");
            alert.showAndWait();

            // Call the service to save the user
            securityService.createUser(user);

            mainController.showMenu();
          } else {
            // Notify user of no authority error
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("The new user must have an authority!");
            alert.showAndWait();
          }
        } else {
          // Notify user of password mismatch
          Alert alert = new Alert(Alert.AlertType.INFORMATION);
          alert.setTitle("Error");
          alert.setHeaderText(null);
          alert.setContentText("The passwords do not match!");
          alert.showAndWait();
        }
      } else {
        // Notify user of no password error
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("The new user must have a password!");
        alert.showAndWait();
      }
    } else {
      // Notify user of no username error
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Error");
      alert.setHeaderText(null);
      alert.setContentText("The new user must have a username!");
      alert.showAndWait();
    }
  }

  @FXML
  private void backButtonClick() {
    mainController.showMenu();
  }

  public void buildView() {
    // Build authority combo box
    ObservableList<String> authorities = FXCollections.observableArrayList(
        "ADMIN", "CHAIR", "DEAN", "PROFESSOR", "SCHEDULING STAFF", "USER"
    );
    authorityCombo.setItems(authorities);
  }

}
