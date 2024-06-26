package org.example.biblijava.util;

import javafx.collections.ObservableList;
import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.usermodel.*;
import org.example.biblijava.model.Book;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for exporting book data to a Word document.
 */
public class WordExportUtil {

    /**
     * Exports the list of books to a Word document.
     *
     * @param file the file
     * @param booksData the list of books
     */
    public static void exportBooksToWord(File file, ObservableList<Book> booksData) {
        try (XWPFDocument document = new XWPFDocument()) {
            addCoverPage(document);
            addDocumentHeader(document);

            XWPFParagraph headerParagraph = document.createParagraph();
            headerParagraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun headerRun = headerParagraph.createRun();
            headerRun.setText("Liste des livres");
            headerRun.setFontSize(16);
            headerRun.setBold(true);

            for (Book book : booksData) {
                document.createParagraph();

                XWPFParagraph titleParagraph = document.createParagraph();
                titleParagraph.setAlignment(ParagraphAlignment.LEFT);
                XWPFRun titleRun = titleParagraph.createRun();
                titleRun.setText(book.getTitre());
                titleRun.setFontSize(14);
                titleRun.setBold(true);

                XWPFParagraph authorParagraph = document.createParagraph();
                authorParagraph.setAlignment(ParagraphAlignment.LEFT);
                XWPFRun authorRun = authorParagraph.createRun();
                authorRun.setText("Auteur : " + book.getAuteur());
                authorRun.setFontSize(12);

                XWPFParagraph presentationParagraph = document.createParagraph();
                presentationParagraph.setAlignment(ParagraphAlignment.LEFT);
                XWPFRun presentationRun = presentationParagraph.createRun();
                presentationRun.setText("Présentation : " + book.getPresentation());
                presentationRun.setFontSize(12);

                XWPFParagraph otherInfoParagraph = document.createParagraph();
                otherInfoParagraph.setAlignment(ParagraphAlignment.LEFT);
                XWPFRun otherInfoRun = otherInfoParagraph.createRun();
                otherInfoRun.setText("Parution : " + book.getParution());
                otherInfoRun.addBreak();
                otherInfoRun.setText("Colonne : " + book.getColonne());
                otherInfoRun.addBreak();
                otherInfoRun.setText("Rangée : " + book.getRangee());
                otherInfoRun.addBreak();
                otherInfoRun.setText("Gazette : " + book.getGazette());
                otherInfoRun.addBreak();
                otherInfoRun.setText("Disponible : " + (book.getDisponible() ? "Oui" : "Non"));
                otherInfoRun.setFontSize(12);
            }

            try (FileOutputStream out = new FileOutputStream(file)) {
                document.write(out);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a cover page to the Word document.
     *
     * @param document the Word document
     */
    private static void addCoverPage(XWPFDocument document) {
        XWPFParagraph coverPageParagraph = document.createParagraph();
        coverPageParagraph.setAlignment(ParagraphAlignment.CENTER);

        XWPFRun coverPageRun = coverPageParagraph.createRun();
        coverPageRun.setText("Page de Garde");
        coverPageRun.setFontSize(20);
        coverPageRun.addBreak();

        coverPageRun.setText("Projet bibliothèque Java");
        coverPageRun.addBreak();
        coverPageRun.setText("Développeurs : Benjamin, Loïc, Matteo et Élise");

        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setPageBreak(true);
    }

    /**
     * Adds a header to the Word document.
     *
     * @param document the Word document
     */
    private static void addDocumentHeader(XWPFDocument document) {
        XWPFHeader header = document.createHeader(HeaderFooterType.DEFAULT);

        XWPFParagraph headerParagraph = header.createParagraph();
        headerParagraph.setAlignment(ParagraphAlignment.RIGHT);

        XWPFRun headerRun = headerParagraph.createRun();
        headerRun.setText("Nom du document : Bibliothèque");
        headerRun.addBreak();
        headerRun.setText("Date de génération : "
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
    }
}
