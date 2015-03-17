package com.coursepresso.project.controller;

import com.coursepresso.project.service.SecurityService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;

/**
 * FXML Controller class
 *
 * @author Caleb Miller
 */
public class LoginController implements Initializable {

  @FXML
  private Node root;
  @FXML
  private TextField usernameField;
  @FXML
  private PasswordField passwordField;
  @FXML
  private Button loginButton;
  @FXML
  private Label statusLabel;

  @Inject
  private MainController mainController;
  @Inject
  private SecurityService securityService;

  private static final Logger log = LoggerFactory.getLogger(
      LoginController.class
  );

  /**
   * Initializes the controller class.
   *
   * @param url
   * @param rb
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }

  public void logout() {
    final String username = usernameField.getText();

    final Task<Void> logoutTask = new Task<Void>() {
      @Override
      protected Void call() throws Exception {
        // Never log password information!
        log.info("Logging in as user '{}'", username);
        securityService.logout();
        return null;
      }
    };

    logoutTask.stateProperty().addListener(new ChangeListener<Worker.State>() {
      @Override
      public void changed(ObservableValue<? extends Worker.State> source,
          Worker.State oldState, Worker.State newState) {
        if (newState.equals(Worker.State.SUCCEEDED)) {
          log.info("Successfully logged out user '{}'", username);

          usernameField.setText(null);
          passwordField.setText(null);
          statusLabel.setText("User logged out successfully!");

          mainController.showLogin();
        } else if (newState.equals(Worker.State.FAILED)) {
          Throwable exception = logoutTask.getException();
          if (exception instanceof BadCredentialsException) {
            log.debug("Invalid logout attempt");
          } else {
            log.error("Logout failed", exception);
            statusLabel.setText("Logout failed! Check logs for expection.");
          }
        }
      }
    });

    new Thread(logoutTask).start();
  }

  public Node getView() {
    return root;
  }

  @FXML
  private void loginButtonClick(ActionEvent event) {
    final String username = usernameField.getText();
    final String password = passwordField.getText();

    final Task<Void> loginTask = new Task<Void>() {
      @Override
      protected Void call() throws Exception {
        // Never log password information!
        log.info("Logging in as user '{}'", username);
        securityService.login(username, password);
        return null;
      }
    };

    loginTask.stateProperty().addListener(new ChangeListener<Worker.State>() {
      @Override
      public void changed(ObservableValue<? extends Worker.State> source,
          Worker.State oldState, Worker.State newState) {
        if (newState.equals(Worker.State.SUCCEEDED)) {
          log.info("Successfully logged in as user '{}'", username);
          mainController.showMenu();

        } else if (newState.equals(Worker.State.FAILED)) {
          Throwable exception = loginTask.getException();
          if (exception instanceof BadCredentialsException) {
            log.debug("Invalid login attempt");
            statusLabel.setText("Invalid username or password");
          } else {
            log.error("Login failed", exception);
            statusLabel.setText("Login failed! Check logs for exception.");
          }
        }
      }
    });

    new Thread(loginTask).start();
  }

}
