package org.example.biblijava.controller.auth;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.biblijava.util.DatabaseUtil;

public class ResetPasswordController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField newPasswordField;

    @FXML
    private void handleResetPassword() {
        String username = usernameField.getText();
        String newPassword = newPasswordField.getText();

        if (username.isEmpty() || newPassword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Reset Password Failed", "All fields are required.");
            return;
        }

        if (resetUserPassword(email, newPassword)) {
            showAlert(Alert.AlertType.INFORMATION, "Password Reset Successful", "Password for " + username + " has been reset.");
            closeResetPasswordWindow();
        } else {
            showAlert(Alert.AlertType.ERROR, "Reset Password Failed", "No user found with the username " + username + ".");
        }
    }

    private boolean resetUserPassword(String username, String newPassword) {
        String query = "UPDATE users SET password = ? WHERE username = ?";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, newPassword);
            statement.setString(2, username);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeResetPasswordWindow() {
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.close();
    }
}