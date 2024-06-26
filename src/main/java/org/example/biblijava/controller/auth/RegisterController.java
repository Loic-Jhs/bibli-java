package org.example.biblijava.controller.auth;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.biblijava.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Controller class to manage user registration in the BibliJava app.
 */
public class RegisterController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;

    /**
     * Manages the registration action as soon as the recording button is clicked.
     * Validates the input fields and adds the user to the database if valid.
     */
    @FXML
    private void handleRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "All fields are required.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "Passwords do not match.");
            return;
        }

        if (addUser(username, password)) {
            showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "User " + username + " has been registered.");
            closeRegisterWindow();
        } else {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "User " + username + " already exists.");
        }
    }

    /**
     * Adds a new user on the database.
     *
     * @param username the username
     * @param password the password
     * @return true if the user was added successfully, false otherwise
     */
    private boolean addUser(String username, String password) {
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            statement.setString(2, password);

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Shows an alert dialog box with the type, title, and message of the alert.
     *
     * @param alertType the type
     * @param title the title
     * @param message the message content
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Closes the registration window.
     */
    private void closeRegisterWindow() {
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.close();
    }
}
