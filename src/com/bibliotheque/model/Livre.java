package com.bibliotheque.model;

import com.bibliotheque.enums.EnumOuvrage;

// Livre est une sous-classe concrete d'Ouvrage
public class Livre extends Ouvrage {

    public Livre(String isbn, String titre, String auteur,
            EnumOuvrage genre, String datePublication) {
        super(isbn, titre, auteur, genre, datePublication);
    }

    // redefinition de la methode abstraite
    public void afficherDetails() {
        System.out.println("  Titre  : " + getTitre());
        System.out.println("  Auteur : " + getAuteur());
        System.out.println("  Genre  : " + getGenre());
        System.out.println("  ISBN   : " + getIsbn());
        System.out.println("  Paru   : " + getDatePublication());
    }

    public String toString() {
        return "[Livre] " + super.toString();
    }
}
