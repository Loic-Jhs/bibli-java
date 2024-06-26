package org.example.biblijava.service;

import java.time.LocalDate;

import org.example.biblijava.model.Book;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * Service class for handling book management operations.
 */
public class BookService {

    /**
     * Manages the action of adding a new book to the library.
     *
     * @param booksData             the list of books
     * @param titreTextField        the text field for entering the book title
     * @param auteurTextField       the text field for entering the author's name
     * @param presentationTextArea  the text area for entering the book description
     * @param parutionTextField     the text field for entering the publication year
     * @param colonneTextField      the text field for entering the column number
     * @param rangeeTextField       the text field for entering the row number
     * @param gazetteTextField      the text field for entering the gazette URL
     * @param disponibleCheckBox    the check box indicating book availability
     */
    public void handleAjouterAction(ObservableList<Book> booksData, TextField titreTextField, TextField auteurTextField,
                                    TextArea presentationTextArea, TextField parutionTextField, TextField colonneTextField,
                                    TextField rangeeTextField, TextField gazetteTextField, CheckBox disponibleCheckBox) {

        String titre = titreTextField.getText(); //Définition du titre
        String auteur = auteurTextField.getText();
        String presentation = presentationTextArea.getText();
        String parutionStr = parutionTextField.getText();
        String colonneStr = colonneTextField.getText();
        String rangeeStr = rangeeTextField.getText();
        String gazetteURL = gazetteTextField.getText();
        boolean disponible = disponibleCheckBox.isSelected();

        if (titre.isEmpty() || auteur.isEmpty() || presentation.isEmpty() || parutionStr.isEmpty()
                || colonneStr.isEmpty() || rangeeStr.isEmpty()) {
            showAlert("Erreur", "Tous les champs doivent être remplis.");
            return;
        }

        int parution, colonne, rangee;
        try {
            parution = Integer.parseInt(parutionStr);
            colonne = Integer.parseInt(colonneStr);
            rangee = Integer.parseInt(rangeeStr);
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Les champs Parution, Colonne et Rangée doivent être numériques.");
            return;
        }

        if (parution > LocalDate.now().getYear()) {
            showAlert("Erreur", "L'année de parution ne peut pas être supérieure à l'année actuelle.");
            return;
        }

        if (colonne < 0 || colonne > 7 || rangee < 1 || rangee > 5) {
            showAlert("Erreur", "Colonne doit être entre 0 et 7. Rangée doit être entre 1 et 5.");
            return;
        }

        for (Book book : booksData) {
            if (book.getTitre().equals(titre) && book.getAuteur().equals(auteur) && book.getParution() == parution) {
                showAlert("Erreur", "Un livre avec le même titre, auteur et année de parution existe déjà.");
                return;
            }
        }

        Book nouveauLivre = new Book(titre, auteur, presentation, parution, colonne, rangee, gazetteURL, disponible);
        booksData.add(nouveauLivre);

        titreTextField.clear();
        auteurTextField.clear();
        presentationTextArea.clear();
        parutionTextField.clear();
        colonneTextField.clear();
        rangeeTextField.clear();
        gazetteTextField.clear();
        disponibleCheckBox.setSelected(false);
    }

    /**
     * Manages the action of deleting a selected book from the library.
     *
     * @param tableBooks the table view containing the list of books
     */
    public void handleDeleteAction(TableView<Book> tableBooks) {
        int selectedIndex = tableBooks.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            tableBooks.getItems().remove(selectedIndex);
        } else {
            showAlert("Aucune sélection", "Veuillez sélectionner un livre dans la liste.");
        }
    }

    /**
     * Manages the action of modifying details of a selected book in the library.
     *
     * @param tableBooks            the table view containing the list of books
     * @param titreTextField        the text field for entering/modifying the book title
     * @param auteurTextField       the text field for entering/modifying the author's name
     * @param presentationTextArea  the text area for entering/modifying the book description
     * @param parutionTextField     the text field for entering/modifying the publication year
     * @param colonneTextField      the text field for entering/modifying the column number
     * @param rangeeTextField       the text field for entering/modifying the row number
     * @param gazetteTextField      the text field for entering/modifying the gazette URL
     * @param disponibleCheckBox    the check box indicating book availability
     */
    public void handleModifierAction(TableView<Book> tableBooks, TextField titreTextField, TextField auteurTextField,
                                     TextArea presentationTextArea, TextField parutionTextField, TextField colonneTextField,
                                     TextField rangeeTextField, TextField gazetteTextField, CheckBox disponibleCheckBox) {
        Book selectedBook = tableBooks.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            String titre = titreTextField.getText();
            String auteur = auteurTextField.getText();
            String presentation = presentationTextArea.getText();
            int parution = Integer.parseInt(parutionTextField.getText());
            int colonne = Integer.parseInt(colonneTextField.getText());
            int rangee = Integer.parseInt(rangeeTextField.getText());

            selectedBook.setTitre(titre);
            selectedBook.setAuteur(auteur);
            selectedBook.setPresentation(presentation);
            selectedBook.setParution(parution);
            selectedBook.setColonne(colonne);
            selectedBook.setRangee(rangee);
            selectedBook.setGazette(gazetteTextField.getText());
            selectedBook.setDisponible(disponibleCheckBox.isSelected());

            tableBooks.refresh();
        }
    }

    /**
     * Shows an alert dialog box with the title and content.
     *
     * @param title   the title of the alert
     * @param content the content text of the alert
     */
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
