package com.bibliotheque.model;

public class Caisse {

    private int id;
    private double solde;

    public Caisse(double solde) {
        this.solde = solde;
    }

    public int getId() {
        return id;
    }

    public double getSolde() {
        return solde;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public void encaisser(double montant) {
        this.solde += montant;
    }

    public void afficherSolde() {
        System.out.println("  Solde de la caisse : " + solde + " FCFA");
    }

    public String toString() {
        return "[Caisse] Solde: " + solde + " FCFA";
    }
}
