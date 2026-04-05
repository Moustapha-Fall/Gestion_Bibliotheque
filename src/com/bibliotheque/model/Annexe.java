package com.bibliotheque.model;

public class Annexe {

    private int id;
    private String nom;
    private String adresse;
    private String telephone;

    public Annexe(String nom, String adresse, String telephone) {
        this.nom = nom;
        this.adresse = adresse;
        this.telephone = telephone;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setAdresse(String a) {
        this.adresse = a;
    }

    public void setTelephone(String t) {
        this.telephone = t;
    }

    public String toString() {
        return "[Annexe #" + id + "] " + nom + " | " + adresse + " | " + telephone;
    }
}
