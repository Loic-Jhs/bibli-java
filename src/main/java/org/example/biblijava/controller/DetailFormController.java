package org.example.biblijava.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Label;
import org.example.biblijava.model.Book;

public class DetailFormController {

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

    public void displayBookDetails(Book book) {
        // Vous pouvez définir le contenu des colonnes ici
        // Par exemple :
        titreColumn.setText("Titre: " + book.getTitre());
        auteurColumn.setText("Auteur: " + book.getAuteur());
        presentationColumn.setText("Présentation: " + book.getPresentation());
        parutionColumn.setText("Parution: " + book.getParution());
        colonneColumn.setText("Colonne: " + book.getColonne());
        rangeeColumn.setText("Rangée: " + book.getRangee());
    }

    public void clearBookDetails() {
        // Effacez le contenu des colonnes ici
        // Par exemple :
        titreColumn.setText("");
        auteurColumn.setText("");
        presentationColumn.setText("");
        parutionColumn.setText("");
        colonneColumn.setText("");
        rangeeColumn.setText("");
    }
}
