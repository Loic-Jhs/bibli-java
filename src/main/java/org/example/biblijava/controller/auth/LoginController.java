package org.example.biblijava.controller.auth;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.example.biblijava.BibliJavaApplication;
import org.example.biblijava.util.DatabaseUtil;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controller class that manages the connection function in the BibliJava app.
 */
public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private BibliJavaApplication mainApp;

    /**
     * Defines the main application instance.
     *
     * @param mainApp the main application
     */
    public void setMainApp(BibliJavaApplication mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Manage the connection as soon as you click on the connection button.
     * Authenticates the user based on the username and password.
     */
    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (authenticateUser(username, password)) {
            showAlert(Alert.AlertType.INFORMATION, "Login Successful!", "Welcome " + username);
            mainApp.setAuthenticated(true);  // Mettre à jour l'état d'authentification
            closeLoginWindow();
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Incorrect username or password.");
        }
    }

    /**
     * Shows the registration window.
     */
    @FXML
    private void showRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/biblijava/registerView.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Register");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the reset password window.
     */
    @FXML
    private void showResetPassword() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/biblijava/resetPasswordView.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Reset Password");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Authenticates the user by checking the username and password
     * based on the database.
     *
     * @param username the username
     * @param password the password
     * @return true if authentication is successful, false otherwise
     */
    private boolean authenticateUser(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
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
     * Closes the login window.
     */
    private void closeLoginWindow() {
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.close();
    }
}
