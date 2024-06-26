package org.example.biblijava.model;

import javafx.beans.property.*;

/**
 * Represents a book in the BibliJava app.
 * This class uses JavaFX properties to support data binding in the UI.
 */
public class Book {
    private final SimpleStringProperty titre;
    private final SimpleStringProperty auteur;
    private final SimpleStringProperty presentation;
    private final SimpleIntegerProperty parution;
    private final SimpleIntegerProperty colonne;
    private final SimpleIntegerProperty rangee;
    private final SimpleStringProperty gazette; // URL de la gazette
    private final SimpleBooleanProperty disponible; // Disponibilité du livre

    /**
     * Constructs a new Book with some details.
     *
     * @param titre        the title of the book
     * @param auteur       the author of the book
     * @param presentation a brief description of the book
     * @param parution     the year of publication
     * @param colonne      the column location of the book in the library
     * @param rangee       the row location of the book in the library
     * @param gazette      the URL of the book's gazette image
     * @param disponible   the availability status of the book
     */
    public Book(String titre, String auteur, String presentation, int parution, int colonne, int rangee, String gazette, boolean disponible) {

        this.titre = new SimpleStringProperty(titre);
        this.auteur = new SimpleStringProperty(auteur);
        this.presentation = new SimpleStringProperty(presentation);
        this.parution = new SimpleIntegerProperty(parution);
        this.colonne = new SimpleIntegerProperty(colonne);
        this.rangee = new SimpleIntegerProperty(rangee);
        this.gazette = new SimpleStringProperty(gazette);
        this.disponible = new SimpleBooleanProperty(disponible);
    }

    public String getTitre() { return titre.get(); }
    public void setTitre(String value) { titre.set(value); }
    public StringProperty titreProperty() { return titre; }

    public String getAuteur() { return auteur.get(); }
    public void setAuteur(String value) { auteur.set(value); }
    public StringProperty auteurProperty() { return auteur; }

    public String getPresentation() { return presentation.get(); }
    public void setPresentation(String value) { presentation.set(value); }
    public StringProperty presentationProperty() { return presentation; }

    public int getParution() { return parution.get(); }
    public void setParution(int value) { parution.set(value); }
    public IntegerProperty parutionProperty() { return parution; }

    public int getColonne() { return colonne.get(); }
    public void setColonne(int value) { colonne.set(value); }
    public IntegerProperty colonneProperty() { return colonne; }

    public int getRangee() { return rangee.get(); }
    public void setRangee(int value) { rangee.set(value); }
    public IntegerProperty rangeeProperty() { return rangee; }

    public String getGazette() { return gazette.get(); }
    public void setGazette(String value) { gazette.set(value); }
    public StringProperty gazetteProperty() { return gazette; }

    public boolean getDisponible() { return disponible.get(); }
    public void setDisponible(boolean value) { disponible.set(value); }
    public BooleanProperty disponibleProperty() { return disponible; }
}
