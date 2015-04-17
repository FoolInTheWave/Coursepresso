package com.coursepresso.project.controller;

import com.coursepresso.project.entity.Authority;
import com.coursepresso.project.entity.User;
import com.coursepresso.project.repository.UserRepository;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * FXML Controller class
 *
 * @author Caleb Miller
 */
public class EditUserController implements Initializable {

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
  private TextField firstnameField;
  @FXML
  private TextField lastnameField;
  @FXML
  private TextField emailField;
  @FXML
  private Button createUserButton;
  @FXML
  private PasswordField confirmPasswordField;

  @Inject
  private MainController mainController;
  @Inject
  private UserRepository userRepository;
  
  private User user;

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
    ArrayList<Authority> authorities = new ArrayList<>();
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("User Creation");
    alert.setHeaderText(null);

    if (!usernameField.getText().equals("")) {
      if (!passwordField.getText().equals("")) {
        if (passwordField.getText().equals(confirmPasswordField.getText())) {
          if (authorityCombo.getValue() != null) {
            // Add user authorities to list
            Authority authority = new Authority();
            authority.setAuthority(authorityCombo.getValue());
            authorities.add(
                authority
            );

            // Hash the password
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(
                passwordField.getText()
            );

            // Save user changes
            user.setUsername(usernameField.getText());
            user.setPassword(hashedPassword);
            user.setAuthorityList(authorities);
            user.setFirstname(firstnameField.getText());
            user.setLastname(lastnameField.getText());
            user.setEmail(emailField.getText());

            // Call the repository to save the user
            userRepository.save(user);

            // Nofify user
            alert.setContentText("The new user account has been created successfully!");
            alert.showAndWait();

            mainController.showMenu();
          } else {
            // Notify user of no authority error
            alert.setContentText("The new user must have an authority!");
            alert.showAndWait();
          }
        } else {
          // Notify user of password mismatch
          alert.setContentText("The passwords do not match!");
          alert.showAndWait();
        }
      } else {
        // Notify user of no password error
        alert.setContentText("The new user must have a password!");
        alert.showAndWait();
      }
    } else {
      // Notify user of no username error
      alert.setContentText("The new user must have a username!");
      alert.showAndWait();
    }
  }

  @FXML
  private void backButtonClick() {
    mainController.showMenu();
  }

  public void buildView(User user) {
    // Build username text field
    usernameField.setText(user.getUsername());
    
    // Build authority combo box
    ObservableList<String> authorities = FXCollections.observableArrayList(
        "ADMIN", "CHAIR", "DEAN", "PROFESSOR", "SCHEDULING STAFF", "USER"
    );
    authorityCombo.setItems(authorities);
    authorityCombo.getSelectionModel().select(
        user.getAuthorityList().get(0).getAuthority()
    );
    
    this.user = user;
  }

}
