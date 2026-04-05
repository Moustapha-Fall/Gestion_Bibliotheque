package com.bibliotheque.model;

import com.bibliotheque.enums.EnumAmende;

public class Amende {

    private int id;
    private double montant;
    private boolean paye;
    private EnumAmende motif;
    private int empruntId;

    public Amende(double montant, EnumAmende motif, int empruntId) {
        this.montant = montant;
        this.motif = motif;
        this.empruntId = empruntId;
        this.paye = false;
    }

    public int getId() {
        return id;
    }

    public double getMontant() {
        return montant;
    }

    public boolean isPaye() {
        return paye;
    }

    public EnumAmende getMotif() {
        return motif;
    }

    public int getEmpruntId() {
        return empruntId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMontant(double m) {
        this.montant = m;
    }

    public void setPaye(boolean p) {
        this.paye = p;
    }

    public void setMotif(EnumAmende m) {
        this.motif = m;
    }

    public void setEmpruntId(int e) {
        this.empruntId = e;
    }

    public void payer() {
        this.paye = true;
    }

    public String toString() {
        return "[Amende #" + id + "] Montant: " + montant + " FCFA"
                + " | Motif: " + motif
                + " | Payee: " + (paye ? "OUI" : "NON");
    }
}
