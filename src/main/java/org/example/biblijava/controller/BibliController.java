package org.example.biblijava.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import java.time.LocalDate;

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

    @FXML
    private void initialize() {

        titreColumn.setCellValueFactory(cellData -> cellData.getValue().titreProperty());
        auteurColumn.setCellValueFactory(cellData -> cellData.getValue().auteurProperty());
        presentationColumn.setCellValueFactory(cellData -> cellData.getValue().presentationProperty());
        parutionColumn
                .setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<Number>(cellData.getValue().getParution()));
        colonneColumn
                .setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<Number>(cellData.getValue().getColonne()));
        rangeeColumn
                .setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<Number>(cellData.getValue().getRangee()));

        tableBooks.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                fillFormWithBookDetails(newSelection);
            }
        });

        // Désactive le bouton "Valider" au démarrage
        ajouterButton.setDisable(true);

        // Affiche le bouton "Modifier" lorsque l'utilisateur sélectionne un livre
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

        // booksData.add(new Book("Le Petit Prince", "Antoine de Saint-Exupéry", "Une
        // belle histoire", 1943, 1, 1));
        // booksData.add(new Book("1984", "George Orwell", "Dystopie classique", 1949,
        // 2, 3));

        tableBooks.setItems(booksData);
    }

    @FXML
    private void handleAjouterAction() {
        String titre = titreTextField.getText();
        String auteur = auteurTextField.getText();
        String presentation = presentationTextArea.getText();
        String parutionStr = parutionTextField.getText();
        String colonneStr = colonneTextField.getText();
        String rangeeStr = rangeeTextField.getText();
        String gazetteURL = gazetteTextField.getText();
        boolean disponible = disponibleCheckBox.isSelected();

        // Vérification que tous les champs sont remplis
        if (titre.isEmpty() || auteur.isEmpty() || presentation.isEmpty() || parutionStr.isEmpty()
                || colonneStr.isEmpty() || rangeeStr.isEmpty()) {
            showAlert("Erreur", "Tous les champs doivent être remplis.");
            return;
        }

        // Vérification des valeurs numériques
        int parution, colonne, rangee;
        try {
            parution = Integer.parseInt(parutionStr);
            colonne = Integer.parseInt(colonneStr);
            rangee = Integer.parseInt(rangeeStr);
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Les champs Parution, Colonne et Rangée doivent être numériques.");
            return;
        }

        // Vérification de la date de parution
        if (parution > LocalDate.now().getYear()) {
            showAlert("Erreur", "L'année de parution ne peut pas être supérieure à l'année actuelle.");
            return;
        }

        // Vérification des valeurs pour colonne et rangée
        if (colonne < 0 || colonne > 7 || rangee < 1 || rangee > 5) {
            showAlert("Erreur", "Colonne doit être entre 0 et 7. Rangée doit être entre 1 et 5.");
            return;
        }

        // Vérification de l'unicité du livre
        for (Book book : booksData) {
            if (book.getTitre().equals(titre) && book.getAuteur().equals(auteur) && book.getParution() == parution) {
                showAlert("Erreur", "Un livre avec le même titre, auteur et année de parution existe déjà.");
                return;
            }
        }

        // Ajout du livre
        Book nouveauLivre = new Book(titre, auteur, presentation, parution, colonne, rangee, gazetteURL, disponible);
        booksData.add(nouveauLivre);

        // Nettoyage des champs du formulaire
        titreTextField.clear();
        auteurTextField.clear();
        presentationTextArea.clear();
        parutionTextField.clear();
        colonneTextField.clear();
        rangeeTextField.clear();
        gazetteTextField.clear();
        disponibleCheckBox.setSelected(false);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void handleDeleteAction() {
        int selectedIndex = tableBooks.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            tableBooks.getItems().remove(selectedIndex);
        } else {
            // Afficher un message d'erreur si aucun livre n'est sélectionné
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un livre dans la liste.");
            alert.showAndWait();
        }
    }

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
            Image image = new Image(book.getGazette(), true); // Le deuxième paramètre `true` permet le chargement en arrière-plan
            bookImageView.setImage(image);
        } else {
            bookImageView.setImage(null); // Efface l'image précédente si le livre sélectionné n'a pas d'URL d'image
        }

        handleUnlockAction();
    }

    public void loadBooksFromXML(File file) {
        try {
            // Efface tous les livres existants dans la liste observable pour commencer avec
            // une liste vide.
            // Cela évite d'afficher les livres précédemment chargés ou ajoutés
            // manuellement.
            booksData.clear();

            // Prépare un constructeur de documents XML, nécessaire pour lire le contenu du
            // fichier XML.
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            // Parse le fichier XML en un objet Document, qui permet de naviguer dans la
            // structure du fichier XML.
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
                    String titre = titreNodeList.getLength() > 0 ? titreNodeList.item(0).getTextContent()
                            : "Titre inconnu";

                    // Exemple pour l'auteur
                    NodeList auteurNodeList = eElement.getElementsByTagName("auteur");
                    String nom = auteurNodeList.getLength() > 0
                            ? ((Element) auteurNodeList.item(0)).getElementsByTagName("nom").item(0).getTextContent()
                            : "Nom inconnu";
                    String prenom = auteurNodeList.getLength() > 0
                            ? ((Element) auteurNodeList.item(0)).getElementsByTagName("prenom").item(0).getTextContent()
                            : "Prenom inconnu";
                    String auteur = prenom + " " + nom; // Formatage du nom de l'auteur

                    // Exemple pour la présentation
                    NodeList presentationNodeList = eElement.getElementsByTagName("presentation");
                    String presentation = presentationNodeList.getLength() > 0
                            ? presentationNodeList.item(0).getTextContent()
                            : "";

                    // Exemple pour la parution
                    NodeList parutionNodeList = eElement.getElementsByTagName("parution");
                    int parution = parutionNodeList.getLength() > 0
                            ? Integer.parseInt(parutionNodeList.item(0).getTextContent())
                            : 0;

                    // Exemple pour la colonne
                    NodeList colonneNodeList = eElement.getElementsByTagName("colonne");
                    int colonne = colonneNodeList.getLength() > 0
                            ? Integer.parseInt(colonneNodeList.item(0).getTextContent())
                            : 0;

                    // Exemple pour la rangée
                    NodeList rangeeNodeList = eElement.getElementsByTagName("rangee");
                    int rangee = rangeeNodeList.getLength() > 0
                            ? Integer.parseInt(rangeeNodeList.item(0).getTextContent())
                            : 0;

                    // Exemple pour la Gazette
                    NodeList gazetteNodeList = eElement.getElementsByTagName("gazette");
                    String gazette = gazetteNodeList.getLength() > 0 ? gazetteNodeList.item(0).getTextContent() : "";

                    // Exemple pour Disponible
                    NodeList disponibleNodeList = eElement.getElementsByTagName("disponible");
                    boolean disponible = disponibleNodeList.getLength() > 0 ? Boolean.parseBoolean(disponibleNodeList.item(0).getTextContent()) : false;


                    // Création et ajout du livre à la liste
                    Book book = new Book(titre, auteur, presentation, parution, colonne, rangee, gazette, disponible);
                    booksData.add(book);
                }
            }
        } catch (Exception e) {
            // En cas d'erreur lors du traitement du fichier XML, affiche la trace de
            // l'erreur.
            e.printStackTrace();
        }
    }

    @FXML
    private void handleModifierAction() {
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

            // Mets à jour le tableau avec les nouvelles valeurs du livre modifié
            tableBooks.refresh();
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
                nom.appendChild(doc.createTextNode(nomPrenom.length > 1 ? nomPrenom[1] : "")); // Le nom est après le
                                                                                               // prénom
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

                // Gazette
                Element gazette = doc.createElement("gazette");
                gazette.appendChild(doc.createTextNode(book.getGazette()));
                livre.appendChild(gazette);

                // Disponible
                Element disponible = doc.createElement("disponible");
                disponible.appendChild(doc.createTextNode(String.valueOf(book.getDisponible())));
                livre.appendChild(disponible);
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
