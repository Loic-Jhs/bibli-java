package org.example.biblijava.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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

    @FXML
    private TextField titreTextField;
    @FXML
    private TextField auteurTextField;
    @FXML
    private TextArea presentationTextArea;
    @FXML
    private TextField parutionTextField;
    @FXML
    private TextField colonneTextField;
    @FXML
    private TextField rangeeTextField;
    @FXML
    private Button ajouterButton;



    private ObservableList<Book> booksData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {

        titreColumn.setCellValueFactory(cellData -> cellData.getValue().titreProperty());
        auteurColumn.setCellValueFactory(cellData -> cellData.getValue().auteurProperty());
        presentationColumn.setCellValueFactory(cellData -> cellData.getValue().presentationProperty());
        parutionColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<Number>(cellData.getValue().getParution()));
        colonneColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<Number>(cellData.getValue().getColonne()));
        rangeeColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<Number>(cellData.getValue().getRangee()));

        tableBooks.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                fillFormWithBookDetails(newSelection);
            }
        });

        booksData.add(new Book("Le Petit Prince", "Antoine de Saint-Exupéry", "Une belle histoire", 1943, 1, 1));
        booksData.add(new Book("1984", "George Orwell", "Dystopie classique", 1949, 2, 3));


        tableBooks.setItems(booksData);
    }

    @FXML
    private void handleAjouterAction() {
        String titre = titreTextField.getText();
        String auteur = auteurTextField.getText();
        String presentation = presentationTextArea.getText();
        int parution = Integer.parseInt(parutionTextField.getText());
        int colonne = Integer.parseInt(colonneTextField.getText());
        int rangee = Integer.parseInt(rangeeTextField.getText());

        Book nouveauLivre = new Book(titre, auteur, presentation, parution, colonne, rangee);
        booksData.add(nouveauLivre);

        // Optionnel: Effacer les champs du formulaire après l'ajout
        titreTextField.clear();
        auteurTextField.clear();
        presentationTextArea.clear();
        parutionTextField.clear();
        colonneTextField.clear();
        rangeeTextField.clear();
    }

    @FXML
    private void handleUnlockAction() {
        titreTextField.setDisable(false);
        auteurTextField.setDisable(false);
        presentationTextArea.setDisable(false);
        parutionTextField.setDisable(false);
        colonneTextField.setDisable(false);
        rangeeTextField.setDisable(false);
        ajouterButton.setDisable(false);
    }

    private void fillFormWithBookDetails(Book book) {
        titreTextField.setText(book.getTitre());
        auteurTextField.setText(book.getAuteur());
        presentationTextArea.setText(book.getPresentation());
        parutionTextField.setText(String.valueOf(book.getParution()));
        colonneTextField.setText(String.valueOf(book.getColonne()));
        rangeeTextField.setText(String.valueOf(book.getRangee()));

        handleUnlockAction();
    }

}
