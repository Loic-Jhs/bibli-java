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
 * Controller class to manage password reset functionality in the BibliJava app.
 */
public class ResetPasswordController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField newPasswordField;

    /**
     * Manages the password reset action as soon as the recording button is clicked.
     * Validates the input fields and updates the user's password in the database if valid.
     */
    @FXML
    private void handleResetPassword() {
        String username = usernameField.getText();
        String password = newPasswordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Reset Password Failed", "All fields are required.");
            return;
        }

        if (resetUserPassword(username, password)) {
            showAlert(Alert.AlertType.INFORMATION, "Password Reset Successful", "Password for " + username + " has been reset.");
            closeResetPasswordWindow();
        } else {
            showAlert(Alert.AlertType.ERROR, "Reset Password Failed", "No user found with the username " + username + ".");
        }
    }

    /**
     * Updates the password of the user in the database.
     *
     * @param username the username
     * @param password the new password
     * @return true if the password was updated successfully, false otherwise
     */
    private boolean resetUserPassword(String username, String password) {
        String query = "UPDATE users SET password = ? WHERE username = ?";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, password);
            statement.setString(2, username);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
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
     * Closes the password reset window.
     */
    private void closeResetPasswordWindow() {
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.close();
    }
}
