package com.coursepresso.project.controller;

import com.coursepresso.project.entity.Authority;
import com.coursepresso.project.entity.Department;
import com.coursepresso.project.entity.User;
import com.coursepresso.project.repository.AuthorityRepository;
import com.coursepresso.project.repository.DepartmentRepository;
import com.coursepresso.project.repository.UserRepository;
import com.google.common.collect.Lists;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
  private ComboBox<Department> departmentCombo;
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
  @Inject
  private AuthorityRepository authorityRepository;
  @Inject
  private DepartmentRepository departmentRepository;

  private static final Logger log = LoggerFactory.getLogger(
          CreateUserController.class
  );

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
    StringBuilder sbErrors = validate();
    if (!(sbErrors.toString().equals(""))) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Errors");
      alert.setHeaderText(null);
      alert.setContentText(sbErrors.toString());
      alert.showAndWait();
    } else {

      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle(null);
      alert.setHeaderText(null);

      ArrayList<Authority> authorities = new ArrayList<>();

      if (!(passwordField.getText().equals(confirmPasswordField.getText()))) {
        alert.setContentText("The passwords do not match!");
        alert.showAndWait();
      } else {
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

        // Create new user
        User user = new User();
        user.setUsername(usernameField.getText());
        user.setPassword(hashedPassword);
        user.setFirstname(firstnameField.getText());
        user.setLastname(lastnameField.getText());
        user.setEmail(emailField.getText());
        user.setDepartment(departmentCombo.getValue());
        user.setEnabled(true);
        for (Authority a : authorities) {
          a.setUser(user);
        }

        // Call the repository to save the user
        userRepository.save(user);
        authorityRepository.save(authorities);

        // Nofify user
        alert.setContentText("The user account has been created successfully!");
        alert.showAndWait();

        mainController.showViewUsers();
      }
    }
  }

  @FXML
  private void backButtonClick() {
    mainController.showViewUsers();
  }

  public StringBuilder validate() {
    ArrayList<String> errors = new ArrayList();
    StringBuilder sbErrors = new StringBuilder();
    sbErrors.append("");

    if (usernameField.getText().equals("")) {
      errors.add("Username");
    }
    if (passwordField.getText().equals("")) {
      errors.add("Password");
    }
    if (authorityCombo.getSelectionModel().getSelectedItem() == null) {
      errors.add("Authority");
    }
    if (firstnameField.getText().equals("")) {
      errors.add("First Name");
    }
    if (lastnameField.getText().equals("")) {
      errors.add("Last Name");
    }
    if (emailField.getText().equals("")) {
      errors.add("Email Address");
    }
    if (departmentCombo.getSelectionModel().getSelectedItem() == null) {
      errors.add("Department");
    }

    if (!errors.isEmpty()) {
      sbErrors.append("Please enter the following before submitting: " + '\n');

      for (String error : errors) {
        sbErrors.append('\t').append(error).append('\n');
      }
    }

    return sbErrors;
  }

  public void buildView() {
    // Build authority combo box
    ObservableList<String> authorities = FXCollections.observableArrayList(
            "ADMIN", "CHAIR", "DEAN", "PROFESSOR", "SCHEDULING STAFF", "USER"
    );
    authorityCombo.setItems(authorities);

    // Build department combo box
    ObservableList<Department> departments = FXCollections.observableArrayList(
            Lists.newArrayList(departmentRepository.findAll())
    );
    departmentCombo.setItems(departments);

    // Clear fields
    usernameField.clear();
    passwordField.clear();
    confirmPasswordField.clear();
    authorityCombo.getSelectionModel().clearSelection();
    firstnameField.clear();
    lastnameField.clear();
    emailField.clear();
    departmentCombo.getSelectionModel().clearSelection();
  }

}
