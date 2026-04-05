package com.bibliotheque.model;

public class Vehicule {

    private int id;
    private String immatriculation;
    private String modele;
    private String marque;
    private double kilometrage;

    public Vehicule(String immatriculation, String modele,
            String marque, double kilometrage) {
        this.immatriculation = immatriculation;
        this.modele = modele;
        this.marque = marque;
        this.kilometrage = kilometrage;
    }

    public int getId() {
        return id;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public String getModele() {
        return modele;
    }

    public String getMarque() {
        return marque;
    }

    public double getKilometrage() {
        return kilometrage;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImmatriculation(String i) {
        this.immatriculation = i;
    }

    public void setModele(String m) {
        this.modele = m;
    }

    public void setMarque(String m) {
        this.marque = m;
    }

    public void setKilometrage(double k) {
        this.kilometrage = k;
    }

    public void effectuerLivraison(Annexe depart, Annexe arrivee) {
        System.out.println("  Livraison : " + depart.getNom()
                + " --> " + arrivee.getNom());
    }

    public String toString() {
        return "[Vehicule #" + id + "] " + marque + " " + modele
                + " | Immat: " + immatriculation
                + " | Km: " + kilometrage;
    }
}
