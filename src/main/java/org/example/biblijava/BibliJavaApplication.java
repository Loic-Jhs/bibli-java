package org.example.biblijava;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.biblijava.controller.BibliController;
import org.example.biblijava.util.DatabaseUtil;

import java.io.File;
import java.io.IOException;

public class BibliJavaApplication extends Application {

    private File currentFile;

    @Override
    public void start(Stage primaryStage) throws IOException {
        DatabaseUtil.initializeDatabase();  // Initialiser la base de données
        showLoginStage(primaryStage);
    }

    private void showLoginStage(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/biblijava/loginView.fxml"));
        Scene loginScene = new Scene(loader.load(), 300, 200);

        Stage loginStage = new Stage();
        loginStage.setTitle("Login");
        loginStage.setScene(loginScene);
        loginStage.setResizable(false);
        loginStage.showAndWait(); // Attendre que l'utilisateur se connecte

        // Charger l'application principale après la connexion réussie
        showMainStage(primaryStage);
    }

    private void showMainStage(Stage primaryStage) throws IOException {
        try {
            // Crée un BorderPane comme racine de la scène
            BorderPane root = new BorderPane();

            // Charger le contenu FXML et l'ajouter au centre du BorderPane
            FXMLLoader fxmlLoader = new FXMLLoader(BibliJavaApplication.class.getResource("/org/example/biblijava/bibliTable.fxml"));
            BorderPane tableView = fxmlLoader.load(); // Charge le contenu FXML
            BibliController bibliController = fxmlLoader.getController();

            root.setCenter(tableView); // Ajoute le TableView chargé au centre du BorderPane

            // Crée la barre de menu
            MenuBar menuBar = new MenuBar();

            // Menu Fichier
            Menu fileMenu = new Menu("Fichier");
            MenuItem importItem = new MenuItem("Ouvrir");
            importItem.setOnAction(e -> {
                FileChooser fileChooser = new FileChooser();
                FileChooser.ExtensionFilter xmlFilter = new FileChooser.ExtensionFilter("Fichiers XML (*.xml)", "*.xml");

                fileChooser.getExtensionFilters().add(xmlFilter);
                File file = fileChooser.showOpenDialog(primaryStage);

                if (file != null) {
                    bibliController.loadBooksFromXML(file);
                    currentFile = file; // Mise à jour de currentFile ici
                }
            });

            MenuItem connexionItem = new MenuItem("Connexion");
            MenuItem exitItem = new MenuItem("Quitter");
            exitItem.setOnAction(e -> System.exit(0));

            MenuItem exportItem = new MenuItem("Export");
            exportItem.setOnAction(e -> {
                FileChooser fileChooser = new FileChooser();
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Fichiers Word (*.docx)", "*.docx");
                fileChooser.getExtensionFilters().add(extFilter);
                File file = fileChooser.showSaveDialog(primaryStage);

                if (file != null) {
                    bibliController.exportBooksToWord(file);
                }
            });

            fileMenu.getItems().addAll(importItem, connexionItem, exportItem, exitItem);

            // Menu Édition
            Menu editMenu = new Menu("Édition");
            MenuItem saveItem = new MenuItem("Enregistrer");
            saveItem.setOnAction(e -> {
                if (currentFile != null) {
                    bibliController.saveBooksToXML(currentFile);
                } else {
                    // Ici, vous pouvez choisir d'ouvrir la boîte de dialogue "Enregistrer sous" si aucun fichier n'est actuellement ouvert
                    // Ou bien afficher un message d'erreur ou d'information à l'utilisateur
                    System.out.println("Aucun fichier n'est actuellement ouvert. Utilisez 'Enregistrer sous' pour sauvegarder vos modifications.");
                }
            });

            MenuItem saveAsItem = new MenuItem("Enregistrer sous");
            saveAsItem.setOnAction(e -> {
                FileChooser fileChooser = new FileChooser();
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Fichiers XML (*.xml)", "*.xml");
                fileChooser.getExtensionFilters().add(extFilter);
                File file = fileChooser.showSaveDialog(primaryStage);

                if (file != null) {
                    bibliController.saveBooksToXML(file);
                    currentFile = file;
                }
            });

            editMenu.getItems().addAll(saveItem, saveAsItem);

            // Menu Infos
            Menu aboutMenu = new Menu("About");
            MenuItem infosItem = new MenuItem("Infos");
            infosItem.setOnAction(e -> {
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
            });

            aboutMenu.getItems().addAll(infosItem);

            // Ajoute les menus à la barre de menu
            menuBar.getMenus().addAll(fileMenu, editMenu, aboutMenu);

            // Ajoute la barre de menu au BorderPane
            root.setTop(menuBar);

            // Finalise et affiche la scène
            Scene scene = new Scene(root, 1280, 720);
            String css = this.getClass().getResource("/styles.css").toExternalForm();
            scene.getStylesheets().add(css);
            primaryStage.setTitle("Bibliothèque");
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
