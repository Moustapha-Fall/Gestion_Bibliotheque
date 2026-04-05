package com.bibliotheque.model;

import com.bibliotheque.enums.EnumOuvrage;

public abstract class Ouvrage {

    private int id;
    private String isbn;
    private String titre;
    private String auteur;
    private EnumOuvrage genre;
    private String datePublication;

    public Ouvrage(String isbn, String titre, String auteur,
            EnumOuvrage genre, String datePublication) {
        this.isbn = isbn;
        this.titre = titre;
        this.auteur = auteur;
        this.genre = genre;
        this.datePublication = datePublication;
    }

    public int getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitre() {
        return titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public EnumOuvrage getGenre() {
        return genre;
    }

    public String getDatePublication() {
        return datePublication;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public void setGenre(EnumOuvrage genre) {
        this.genre = genre;
    }

    public void setDatePublication(String d) {
        this.datePublication = d;
    }

    // methode abstraite — chaque sous-classe la redefinit
    public abstract void afficherDetails();

    public String toString() {
        return titre + " | " + auteur + " | " + genre + " | " + isbn;
    }
}
