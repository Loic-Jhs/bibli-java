package org.example.biblijava.controller.auth;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.biblijava.model.User;

import java.util.ArrayList;
import java.util.List;

public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private List<User> users = new ArrayList<>();

    @FXML
    private void initialize() {
        // Ajouter des utilisateurs fictifs pour le test
        users.add(new User("admin", "admin123"));
        users.add(new User("user", "user123"));
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                showAlert(Alert.AlertType.INFORMATION, "Login Successful!", "Welcome " + username);
                closeLoginWindow();
                return;
            }
        }
        showAlert(Alert.AlertType.ERROR, "Login Failed", "Incorrect username or password.");
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeLoginWindow() {
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.close();
    }
}
