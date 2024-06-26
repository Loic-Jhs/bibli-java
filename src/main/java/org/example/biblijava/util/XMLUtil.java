package org.example.biblijava.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.example.biblijava.model.Book;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javafx.collections.ObservableList;

/**
 * Utility class for managing XML operations related to the Book model.
 */
public class XMLUtil {

    /**
     * Loads books from an XML file.
     *
     * @param file the XML file
     * @return a list of books loaded from the XML file
     */
    public static List<Book> loadBooksFromXML(File file) {
        List<Book> booksData = new ArrayList<>();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("livre");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String titre = getElementTextContent(eElement, "titre", "Titre inconnu");
                    String nom = getElementTextContent(eElement, "auteur/nom", "Nom inconnu");
                    String prenom = getElementTextContent(eElement, "auteur/prenom", "Prenom inconnu");
                    String auteur = prenom + " " + nom;
                    String presentation = getElementTextContent(eElement, "presentation", "");
                    int parution = Integer.parseInt(getElementTextContent(eElement, "parution", "0"));
                    int colonne = Integer.parseInt(getElementTextContent(eElement, "colonne", "0"));
                    int rangee = Integer.parseInt(getElementTextContent(eElement, "rangee", "0"));
                    String gazette = getElementTextContent(eElement, "gazette", "");
                    boolean disponible = Boolean.parseBoolean(getElementTextContent(eElement, "disponible", "false"));

                    Book book = new Book(titre, auteur, presentation, parution, colonne, rangee, gazette, disponible);
                    booksData.add(book);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return booksData;
    }

    /**
     * Retrieves the contents of an element into a parent element.
     *
     * @param parentElement the parent element
     * @param tagName the name of the tag to retrieve content from
     * @param defaultValue the default value to return if the element is not found
     * @return the text content of the specified element, or the default value if not found
     */
    private static String getElementTextContent(Element parentElement, String tagName, String defaultValue) {
        NodeList nodeList = parentElement.getElementsByTagName(tagName);
        return nodeList.getLength() > 0 ? nodeList.item(0).getTextContent() : defaultValue;
    }

    /**
     * Saves books to an XML file.
     *
     * @param file the XML file
     * @param booksData the list of books
     */
    public static void saveBooksToXML(File file, ObservableList<Book> booksData) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement("bibliotheque");
            doc.appendChild(rootElement);

            for (Book book : booksData) {
                Element livre = doc.createElement("livre");
                rootElement.appendChild(livre);

                appendChildElement(doc, livre, "titre", book.getTitre());
                Element auteur = doc.createElement("auteur");
                livre.appendChild(auteur);
                String[] nomPrenom = book.getAuteur().split(" ", 2);
                appendChildElement(doc, auteur, "nom", nomPrenom.length > 1 ? nomPrenom[1] : "");
                appendChildElement(doc, auteur, "prenom", nomPrenom[0]);
                appendChildElement(doc, livre, "presentation", book.getPresentation());
                appendChildElement(doc, livre, "parution", String.valueOf(book.getParution()));
                appendChildElement(doc, livre, "colonne", String.valueOf(book.getColonne()));
                appendChildElement(doc, livre, "rangee", String.valueOf(book.getRangee()));
                appendChildElement(doc, livre, "gazette", book.getGazette());
                appendChildElement(doc, livre, "disponible", String.valueOf(book.getDisponible()));
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Appends a child element with text content to a parent element.
     *
     * @param doc the document
     * @param parent the parent element
     * @param tagName the name of the tag to create
     * @param textContent the text content of the new element
     */
    private static void appendChildElement(Document doc, Element parent, String tagName, String textContent) {
        Element element = doc.createElement(tagName);
        element.appendChild(doc.createTextNode(textContent));
        parent.appendChild(element);
    }
}
