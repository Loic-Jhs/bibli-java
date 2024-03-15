package org.example.biblijava;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class BibliJavaApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException{
        try {
            // Crée un BorderPane comme racine de la scène
            BorderPane root = new BorderPane();

            // Charger le contenu FXML et l'ajouter au centre du BorderPane
            FXMLLoader fxmlLoader = new FXMLLoader(BibliJavaApplication.class.getResource("/org/example/biblijava/bibliTable.fxml"));
            BorderPane tableView = fxmlLoader.load(); // Charge le contenu FXML

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
                fileChooser.showOpenDialog(primaryStage);
            });

            MenuItem exitItem = new MenuItem("Quitter");
            exitItem.setOnAction(e -> System.exit(0));
            fileMenu.getItems().addAll(importItem, exitItem);

            // Menu Édition
            Menu editMenu = new Menu("Édition");
            MenuItem saveItem = new MenuItem("Enregistrer");
            saveItem.setOnAction(e -> System.out.println("Enregistrer les modifications"));
            MenuItem saveAsItem = new MenuItem("Enregistrer sous");
            saveAsItem.setOnAction(e -> System.out.println("Enregistrer les modifications dans un nouveau fichier"));
            editMenu.getItems().addAll(saveItem, saveAsItem);

            // Menu Infos
            Menu aboutMenu = new Menu("About");
            MenuItem infosItem = new MenuItem("Infos");
            infosItem.setOnAction(e -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/biblijava/aboutView.fxml"));
                    Scene scene = new Scene(loader.load());
                    Stage aboutStage = new Stage();
                    aboutStage.setMinWidth(400); // Largeur minimale
                    aboutStage.setMinHeight(300); // Hauteur minimale
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
            Scene scene = new Scene(root, 1280, 740);
            String css = this.getClass().getResource("/styles.css").toExternalForm();
            scene.getStylesheets().add(css);
            primaryStage.setTitle("Bibliothèque");
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