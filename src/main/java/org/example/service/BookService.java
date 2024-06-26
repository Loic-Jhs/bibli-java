package org.example.biblijava.service;

import java.time.LocalDate;

import org.example.biblijava.model.Book;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class BookService {

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

    public void handleDeleteAction(TableView<Book> tableBooks) {
        int selectedIndex = tableBooks.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            tableBooks.getItems().remove(selectedIndex);
        } else {
            showAlert("Aucune sélection", "Veuillez sélectionner un livre dans la liste.");
        }
    }

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

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
