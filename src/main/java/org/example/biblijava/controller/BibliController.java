package org.example.biblijava.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.biblijava.model.Book;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

import java.io.File;

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

//        booksData.add(new Book("Le Petit Prince", "Antoine de Saint-Exupéry", "Une belle histoire", 1943, 1, 1));
//        booksData.add(new Book("1984", "George Orwell", "Dystopie classique", 1949, 2, 3));


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

    public void loadBooksFromXML(File file) {
        try {
            // Efface tous les livres existants dans la liste observable pour commencer avec une liste vide.
            // Cela évite d'afficher les livres précédemment chargés ou ajoutés manuellement.
            booksData.clear();

            // Prépare un constructeur de documents XML, nécessaire pour lire le contenu du fichier XML.
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            // Parse le fichier XML en un objet Document, qui permet de naviguer dans la structure du fichier XML.
            Document doc = dBuilder.parse(file);
            // Normalise le document XML pour assurer une structure cohérente.
            doc.getDocumentElement().normalize();

            // Obtient tous les éléments "livre" du document XML.
            NodeList nList = doc.getElementsByTagName("livre");

            // Itère sur chaque élément "livre" trouvé dans le document.
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                // Vérifie si le nœud courant est bien un élément (et non du texte ou autre).
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    // Récupère les informations du livre à partir des éléments fils.
                    // Notez que vous devrez adapter ces lignes si le nom des éléments dans votre fichier XML est différent.
                    String titre = eElement.getElementsByTagName("titre").item(0).getTextContent();
                    Element auteurElement = (Element) eElement.getElementsByTagName("auteur").item(0);
                    String nom = auteurElement.getElementsByTagName("nom").item(0).getTextContent();
                    String prenom = auteurElement.getElementsByTagName("prenom").item(0).getTextContent();
                    // Combine le prénom et le nom pour obtenir le nom complet de l'auteur.
                    String auteur = prenom + " " + nom;
                    String presentation = eElement.getElementsByTagName("presentation").item(0).getTextContent();
                    int parution = Integer.parseInt(eElement.getElementsByTagName("parution").item(0).getTextContent());
                    int colonne = Integer.parseInt(eElement.getElementsByTagName("colonne").item(0).getTextContent());
                    int rangee = Integer.parseInt(eElement.getElementsByTagName("rangee").item(0).getTextContent());

                    // Crée une nouvelle instance de Book avec les informations récupérées.
                    Book book = new Book(titre, auteur, presentation, parution, colonne, rangee);
                    // Ajoute le nouveau livre à la liste observable, ce qui mettra à jour l'affichage dans le TableView.
                    booksData.add(book);
                }
            }
        } catch (Exception e) {
            // En cas d'erreur lors du traitement du fichier XML, affiche la trace de l'erreur.
            e.printStackTrace();
        }
    }

}
