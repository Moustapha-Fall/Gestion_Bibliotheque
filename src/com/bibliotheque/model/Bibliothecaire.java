package com.bibliotheque.model;

public class Bibliothecaire extends Personne {

    private int id;
    private String matricule;

    public Bibliothecaire(String email, String nom, String prenom, String adresse, String matricule) {
        super(email, nom, prenom, adresse);
        this.matricule = matricule;
    }

    public int getId() {
        return id;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMatricule(String m) {
        this.matricule = m;
    }

    public String toString() {
        return "[Bibliothecaire] " + super.toString() + " | Matricule: " + matricule;
    }
}
