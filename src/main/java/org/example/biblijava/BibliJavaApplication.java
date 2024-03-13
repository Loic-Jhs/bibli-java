package org.example.biblijava;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class BibliJavaApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Crée un BorderPane comme racine de la scène
        BorderPane root = new BorderPane();

        // Crée la barre de menu
        MenuBar menuBar = new MenuBar();

        // Menu Fichier
        Menu fileMenu = new Menu("Fichier");
        MenuItem importItem = new MenuItem("Importer");
        importItem.setOnAction(e -> System.out.println("Importer un fichier XML"));
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

        // Ajoute les menus à la barre de menu
        menuBar.getMenus().addAll(fileMenu, editMenu);

        // Ajoute la barre de menu au BorderPane
        root.setTop(menuBar);

        // Finalise et affiche la scène
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Bibliothèque");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}