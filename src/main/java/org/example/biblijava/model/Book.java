package org.example.biblijava.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.IntegerProperty;

public class Book {
    private final SimpleStringProperty titre;
    private final SimpleStringProperty auteur;
    private final SimpleStringProperty presentation;
    private final SimpleIntegerProperty parution;
    private final SimpleIntegerProperty colonne;
    private final SimpleIntegerProperty rangee;

    public Book(String titre, String auteur, String presentation, int parution, int colonne, int rangee) {

        this.titre = new SimpleStringProperty(titre);
        this.auteur = new SimpleStringProperty(auteur);
        this.presentation = new SimpleStringProperty(presentation);
        this.parution = new SimpleIntegerProperty(parution);
        this.colonne = new SimpleIntegerProperty(colonne);
        this.rangee = new SimpleIntegerProperty(rangee);
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
}
