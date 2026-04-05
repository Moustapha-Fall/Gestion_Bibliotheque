package com.bibliotheque.model;

public class Membre extends Personne {

    private int id;
    private String numeroCarte;
    private int quotaEmprunt;

    public Membre(String email, String nom, String prenom, String adresse,
            String numeroCarte, int quotaEmprunt) {
        super(email, nom, prenom, adresse);
        this.numeroCarte = numeroCarte;
        this.quotaEmprunt = quotaEmprunt;
    }

    public int getId() {
        return id;
    }

    public String getNumeroCarte() {
        return numeroCarte;
    }

    public int getQuotaEmprunt() {
        return quotaEmprunt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNumeroCarte(String n) {
        this.numeroCarte = n;
    }

    public void setQuotaEmprunt(int q) {
        this.quotaEmprunt = q;
    }

    public boolean peutEmprunter() {
        return quotaEmprunt > 0;
    }

    public String toString() {
        return "[Membre] " + super.toString()
                + " | Carte: " + numeroCarte
                + " | Quota: " + quotaEmprunt;
    }
}
