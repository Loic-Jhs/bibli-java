package org.example.biblijava.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.biblijava.model.Book;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.w3c.dom.*;

import java.io.File;

public class BibliController {
    private File currentFile;


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

                    // Exemple pour le titre
                    NodeList titreNodeList = eElement.getElementsByTagName("titre");
                    String titre = titreNodeList.getLength() > 0 ? titreNodeList.item(0).getTextContent() : "Titre inconnu";

                    // Exemple pour l'auteur
                    NodeList auteurNodeList = eElement.getElementsByTagName("auteur");
                    String nom = auteurNodeList.getLength() > 0 ? ((Element) auteurNodeList.item(0)).getElementsByTagName("nom").item(0).getTextContent() : "Nom inconnu";
                    String prenom = auteurNodeList.getLength() > 0 ? ((Element) auteurNodeList.item(0)).getElementsByTagName("prenom").item(0).getTextContent() : "Prenom inconnu";
                    String auteur = prenom + " " + nom; // Formatage du nom de l'auteur

                    // Exemple pour la présentation
                    NodeList presentationNodeList = eElement.getElementsByTagName("presentation");
                    String presentation = presentationNodeList.getLength() > 0 ? presentationNodeList.item(0).getTextContent() : "";

                    // Exemple pour la parution
                    NodeList parutionNodeList = eElement.getElementsByTagName("parution");
                    int parution = parutionNodeList.getLength() > 0 ? Integer.parseInt(parutionNodeList.item(0).getTextContent()) : 0;

                    // Exemple pour la colonne
                    NodeList colonneNodeList = eElement.getElementsByTagName("colonne");
                    int colonne = colonneNodeList.getLength() > 0 ? Integer.parseInt(colonneNodeList.item(0).getTextContent()) : 0;

                    // Exemple pour la rangée
                    NodeList rangeeNodeList = eElement.getElementsByTagName("rangee");
                    int rangee = rangeeNodeList.getLength() > 0 ? Integer.parseInt(rangeeNodeList.item(0).getTextContent()) : 0;

                    // Création et ajout du livre à la liste
                    Book book = new Book(titre, auteur, presentation, parution, colonne, rangee);
                    booksData.add(book);
                }
            }
        } catch (Exception e) {
            // En cas d'erreur lors du traitement du fichier XML, affiche la trace de l'erreur.
            e.printStackTrace();
        }
    }

    public void saveBooksToXML(File file) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // Créer l'élément racine "bibliotheque"
            Element rootElement = doc.createElement("bibliotheque");
            doc.appendChild(rootElement);

            // Parcourir la liste des livres pour les ajouter au document
            for (Book book : booksData) {
                Element livre = doc.createElement("livre");
                rootElement.appendChild(livre);

                // Titre
                Element titre = doc.createElement("titre");
                titre.appendChild(doc.createTextNode(book.getTitre()));
                livre.appendChild(titre);

                // Auteur
                Element auteur = doc.createElement("auteur");
                livre.appendChild(auteur);
                // Nom
                String[] nomPrenom = book.getAuteur().split(" ", 2); // Supposition que le format est "Prenom Nom"
                Element nom = doc.createElement("nom");
                nom.appendChild(doc.createTextNode(nomPrenom.length > 1 ? nomPrenom[1] : "")); // Le nom est après le prénom
                auteur.appendChild(nom);
                // Prenom
                Element prenom = doc.createElement("prenom");
                prenom.appendChild(doc.createTextNode(nomPrenom[0])); // Le prénom est le premier élément
                auteur.appendChild(prenom);

                // Présentation
                Element presentation = doc.createElement("presentation");
                presentation.appendChild(doc.createTextNode(book.getPresentation()));
                livre.appendChild(presentation);

                // Parution
                Element parution = doc.createElement("parution");
                parution.appendChild(doc.createTextNode(String.valueOf(book.getParution())));
                livre.appendChild(parution);

                // Colonne
                Element colonne = doc.createElement("colonne");
                colonne.appendChild(doc.createTextNode(String.valueOf(book.getColonne())));
                livre.appendChild(colonne);

                // Rangée
                Element rangee = doc.createElement("rangee");
                rangee.appendChild(doc.createTextNode(String.valueOf(book.getRangee())));
                livre.appendChild(rangee);
            }

            // Sauvegarder le document XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCurrentFile(File file) {
        this.currentFile = file;
    }

}
