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
    usernameField.setText(null);
    passwordField.setText(null);
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
        //log.info("Logging in as user '{}'", username);
        System.out.println("Logging in as user: " + username);
        securityService.login(username, password);
        return null;
      }
    };

    loginTask.stateProperty().addListener(new ChangeListener<Worker.State>() {
      @Override
      public void changed(ObservableValue<? extends Worker.State> source,
          Worker.State oldState, Worker.State newState) {
        if (newState.equals(Worker.State.SUCCEEDED)) {
          //log.info("Successfully logged in as user '{}'", username);
          System.out.println("Successfully logged in as user: " + username);
          
          
          mainController.showMenu();
          
          
        } else if (newState.equals(Worker.State.FAILED)) {
          Throwable exception = loginTask.getException();
          if (exception instanceof BadCredentialsException) {
            //log.debug("Invalid login attempt");
            System.out.println("Invalid login attempt");
            statusLabel.setText("Invalid username or password");
          } else {
            //log.error("Login failed", exception);
            System.out.println("Login failed " + exception);
            statusLabel.setText("Login error: " + exception);
          }
        }
      }
    });

    new Thread(loginTask).start();
  }

}
