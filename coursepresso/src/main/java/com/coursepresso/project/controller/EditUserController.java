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

  private User user;

  private static final Logger log = LoggerFactory.getLogger(
          EditUserController.class
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

      ArrayList<Authority> authorities = new ArrayList<>();
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle(null);
      alert.setHeaderText(null);

      if (!(passwordField.getText().equals("")
              && confirmPasswordField.getText().equals(""))
              && (!passwordField.getText().equals(confirmPasswordField.getText()))) {
        alert.setContentText("The passwords do not match!");
        alert.showAndWait();
      } else {
        // Add user authorities to list
        Authority authority = new Authority();
        authority.setAuthority(authorityCombo.getValue());
        authorities.add(
                authority
        );

        // Only update the password if password and confirm password were entered
        if (!passwordField.getText().equals("")
                && !confirmPasswordField.getText().equals("")) {
          // Hash the password
          BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
          String hashedPassword = passwordEncoder.encode(
                  passwordField.getText()
          );

          user.setPassword(hashedPassword);
        }

        // Save user changes
        user.setUsername(usernameField.getText());
        user.setFirstname(firstnameField.getText());
        user.setLastname(lastnameField.getText());
        user.setEmail(emailField.getText());
        user.setDepartment(departmentCombo.getValue());
        for (Authority a : authorities) {
          a.setUser(user);
        }

        // Call the repository to save the user
        userRepository.save(user);
        for (Authority a : user.getAuthorityList()) {
          authorityRepository.delete(a.getId());
        }
        authorityRepository.save(authorities);

        // Nofify user
        alert.setContentText("The user account has been saved successfully!");
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

    // Populate other fields
    passwordField.clear();
    confirmPasswordField.clear();
    firstnameField.setText(user.getFirstname());
    lastnameField.setText(user.getLastname());
    emailField.setText(user.getEmail());

    // Build department combo box
    ObservableList<Department> departments = FXCollections.observableArrayList(
            Lists.newArrayList(departmentRepository.findAll())
    );
    departmentCombo.setItems(departments);
    departmentCombo.getSelectionModel().select(
            user.getDepartment()
    );

    this.user = user;
  }

}
