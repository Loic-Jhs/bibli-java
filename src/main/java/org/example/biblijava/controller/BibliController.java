package org.example.biblijava.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.biblijava.model.Book;
import org.example.biblijava.service.BookService;
import org.example.biblijava.util.XMLUtil;
import org.example.biblijava.util.WordExportUtil;

import java.io.File;

/**
 * Controller class for managing the BibliJava application's main library view.
 */
public class BibliController {
    private File currentFile;
    private BookService bookService = new BookService();

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
    private TextField gazetteTextField;
    @FXML
    private CheckBox disponibleCheckBox;
    @FXML
    private Button ajouterButton;
    @FXML
    private Button modifierButton;
    @FXML
    private ImageView bookImageView;

    private ObservableList<Book> booksData = FXCollections.observableArrayList();

    /**
     * Initializes the controller class. This method is automatically called after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        titreColumn.setCellValueFactory(cellData -> cellData.getValue().titreProperty());
        auteurColumn.setCellValueFactory(cellData -> cellData.getValue().auteurProperty());
        presentationColumn.setCellValueFactory(cellData -> cellData.getValue().presentationProperty());
        parutionColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getParution()));
        colonneColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getColonne()));
        rangeeColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getRangee()));

        tableBooks.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                fillFormWithBookDetails(newSelection);
            }
        });

        ajouterButton.setDisable(true);

        tableBooks.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                ajouterButton.setDisable(true);
                modifierButton.setDisable(false);
                modifierButton.setVisible(true);
            } else {
                ajouterButton.setDisable(false);
                modifierButton.setDisable(true);
                modifierButton.setVisible(false);
            }
        });

        disponibleCheckBox.setSelected(true);
        tableBooks.setItems(booksData);
    }

    /**
     * Manages the action of adding a new book.
     */
    @FXML
    private void handleAjouterAction() {
        bookService.handleAjouterAction(booksData, titreTextField, auteurTextField, presentationTextArea, parutionTextField,
                colonneTextField, rangeeTextField, gazetteTextField, disponibleCheckBox);
    }

    /**
     * Manages the action of deleting a selected book.
     */
    @FXML
    private void handleDeleteAction() {
        bookService.handleDeleteAction(tableBooks);
    }

    /**
     * Manages the action of modifying an existing book.
     */
    @FXML
    private void handleModifierAction() {
        bookService.handleModifierAction(tableBooks, titreTextField, auteurTextField, presentationTextArea, parutionTextField,
                colonneTextField, rangeeTextField, gazetteTextField, disponibleCheckBox);
    }

    /**
     * Fills the form fields with the details of the selected book.
     *
     * @param book the book
     */
    private void fillFormWithBookDetails(Book book) {
        titreTextField.setText(book.getTitre());
        auteurTextField.setText(book.getAuteur());
        presentationTextArea.setText(book.getPresentation());
        parutionTextField.setText(String.valueOf(book.getParution()));
        colonneTextField.setText(String.valueOf(book.getColonne()));
        rangeeTextField.setText(String.valueOf(book.getRangee()));
        gazetteTextField.setText(book.getGazette());
        disponibleCheckBox.setSelected(book.getDisponible());

        if (book.getGazette() != null && !book.getGazette().isEmpty()) {
            Image image = new Image(book.getGazette(), true);
            bookImageView.setImage(image);
        } else {
            bookImageView.setImage(null);
        }

        handleUnlockAction();
    }

    /**
     * Unlocks the form fields, allowing the user to enter book details.
     */
    @FXML
    private void handleUnlockAction() {
        titreTextField.setDisable(false);
        auteurTextField.setDisable(false);
        presentationTextArea.setDisable(false);
        parutionTextField.setDisable(false);
        colonneTextField.setDisable(false);
        rangeeTextField.setDisable(false);
        gazetteTextField.setDisable(false);
        disponibleCheckBox.setDisable(false);
        ajouterButton.setDisable(false);
    }

    /**
     * Loads books from an XML file into the table.
     *
     * @param file the XML file
     */
    public void loadBooksFromXML(File file) {
        booksData.clear();
        booksData.addAll(XMLUtil.loadBooksFromXML(file));
    }

    /**
     * Saves the current list of books to an XML file.
     *
     * @param file the XML file
     */
    public void saveBooksToXML(File file) {

    }

    /**
     * Exports the current list of books to a Word document.
     *
     * @param file the Word document file
     */
    public void exportBooksToWord(File file) {

    }

    /**
     * Sets the current file being used by the controller.
     *
     * @param file the current file
     */
    public void setCurrentFile(File file) {
        this.currentFile = file;
    }
}