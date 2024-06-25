package org.example.biblijava;

import java.io.File;
import java.io.IOException;

import org.example.biblijava.controller.BibliController;
import org.example.biblijava.controller.auth.LoginController;
import org.example.biblijava.util.DatabaseUtil;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class BibliJavaApplication extends Application {

    private File currentFile;
    private boolean isAuthenticated = false;

    @Override
    public void start(Stage primaryStage) throws IOException {
        DatabaseUtil.initializeDatabase();  // Initialiser la base de données
        showLoginStage(primaryStage);

        if (isAuthenticated) {
            showMainStage(primaryStage);
        } else {
            System.out.println("L'utilisateur n'est pas authentifié. L'application va se fermer.");
            primaryStage.close();
        }
    }

    private void showLoginStage(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/biblijava/loginView.fxml"));
        Scene loginScene = new Scene(loader.load(), 300, 200);

        LoginController loginController = loader.getController();
        loginController.setMainApp(this);

        String css = getClass().getResource("/loginStyle.css").toExternalForm();
        loginScene.getStylesheets().add(css);

        Stage loginStage = new Stage();
        loginStage.setTitle("Login");
        loginStage.setScene(loginScene);
        loginStage.setResizable(false);

        loginStage.setOnCloseRequest(event -> {
            isAuthenticated = false;
        });

        loginStage.showAndWait();

        if (!isAuthenticated) {
            primaryStage.close();
        }
    }

    public void setAuthenticated(boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }

    private void showMainStage(Stage primaryStage) throws IOException {
        BorderPane root = new BorderPane();
        FXMLLoader fxmlLoader = new FXMLLoader(BibliJavaApplication.class.getResource("/org/example/biblijava/bibliTable.fxml"));
        BorderPane tableView = fxmlLoader.load();
        BibliController bibliController = fxmlLoader.getController();

        root.setCenter(tableView);
        root.setTop(createMenuBar(primaryStage, bibliController));

        Scene scene = new Scene(root, 1280, 720);
        String css = this.getClass().getResource("/styles.css").toExternalForm();
        scene.getStylesheets().add(css);

        primaryStage.setTitle("Bibliothèque");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private MenuBar createMenuBar(Stage primaryStage, BibliController bibliController) {
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("Fichier");
        MenuItem importItem = new MenuItem("Ouvrir");
        importItem.setOnAction(e -> handleImport(primaryStage, bibliController));
        MenuItem connexionItem = new MenuItem("Connexion");
        MenuItem exportItem = new MenuItem("Export");
        exportItem.setOnAction(e -> handleExport(primaryStage, bibliController));
        MenuItem exitItem = new MenuItem("Quitter");
        exitItem.setOnAction(e -> System.exit(0));
        fileMenu.getItems().addAll(importItem, connexionItem, exportItem, exitItem);

        Menu editMenu = new Menu("Édition");
        MenuItem saveItem = new MenuItem("Enregistrer");
        saveItem.setOnAction(e -> handleSave(bibliController));
        MenuItem saveAsItem = new MenuItem("Enregistrer sous");
        saveAsItem.setOnAction(e -> handleSaveAs(primaryStage, bibliController));
        editMenu.getItems().addAll(saveItem, saveAsItem);

        Menu aboutMenu = new Menu("About");
        MenuItem infosItem = new MenuItem("Infos");
        infosItem.setOnAction(e -> handleAbout());
        aboutMenu.getItems().addAll(infosItem);

        menuBar.getMenus().addAll(fileMenu, editMenu, aboutMenu);
        return menuBar;
    }

    private void handleImport(Stage primaryStage, BibliController bibliController) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter xmlFilter = new FileChooser.ExtensionFilter("Fichiers XML (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(xmlFilter);
        File file = fileChooser.showOpenDialog(primaryStage);

        if (file != null) {
            bibliController.loadBooksFromXML(file);
            currentFile = file;
        }
    }

    private void handleExport(Stage primaryStage, BibliController bibliController) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Fichiers Word (*.docx)", "*.docx");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(primaryStage);

        if (file != null) {
            bibliController.exportBooksToWord(file);
        }
    }

    private void handleSave(BibliController bibliController) {
        if (currentFile != null) {
            bibliController.saveBooksToXML(currentFile);
        } else {
            System.out.println("Aucun fichier n'est actuellement ouvert. Utilisez 'Enregistrer sous' pour sauvegarder vos modifications.");
        }
    }

    private void handleSaveAs(Stage primaryStage, BibliController bibliController) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Fichiers XML (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(primaryStage);

        if (file != null) {
            bibliController.saveBooksToXML(file);
            currentFile = file;
        }
    }

    private void handleAbout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/biblijava/aboutView.fxml"));
            Scene scene = new Scene(loader.load());
            Stage aboutStage = new Stage();
            aboutStage.setMinWidth(400);
            aboutStage.setMinHeight(300);
            aboutStage.setResizable(false);
            aboutStage.setTitle("Infos");
            aboutStage.setScene(scene);
            aboutStage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
