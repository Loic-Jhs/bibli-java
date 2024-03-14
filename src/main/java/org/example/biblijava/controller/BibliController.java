package org.example.biblijava.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.biblijava.model.Book;

public class BibliController {

    @FXML
    private TableView<Book> tableBooks;
    @FXML
    private TableColumn<Book, String> titreColumn;
    @FXML
    private TableColumn<Book, String> auteurColumn;
    @FXML
    private TableColumn<Book, String> presentationColumn;
    @FXML
    private TableColumn<Book, Number> parutionColumn;
    @FXML
    private TableColumn<Book, Number> colonneColumn;
    @FXML
    private TableColumn<Book, Number> rangeeColumn;

    // Référence au contrôleur DetailFormController
    @FXML
    private DetailFormController detailFormController;

    private final ObservableList<Book> booksData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {

        titreColumn.setCellValueFactory(cellData -> cellData.getValue().titreProperty());
        auteurColumn.setCellValueFactory(cellData -> cellData.getValue().auteurProperty());
        presentationColumn.setCellValueFactory(cellData -> cellData.getValue().presentationProperty());
        parutionColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getParution()));
        colonneColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getColonne()));
        rangeeColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getRangee()));

        booksData.add(new Book("Le Petit Prince", "Antoine de Saint-Exupéry", "Une belle histoire", 1943, 1, 1));
        booksData.add(new Book("1984", "George Orwell", "Dystopie classique", 1949, 2, 3));

        tableBooks.setItems(booksData);

        // Appel de la méthode handleBookSelection lors du changement de sélection dans la TableView
        tableBooks.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            handleBookSelection();
        });
    }

    // Méthode pour gérer le changement de sélection dans la TableView
    @FXML
    private void handleBookSelection() {
        Book selectedBook = tableBooks.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            detailFormController.displayBookDetails(selectedBook);
        } else {
            detailFormController.clearBookDetails(); // Efface les détails si aucun livre n'est sélectionné
        }
    }
}
