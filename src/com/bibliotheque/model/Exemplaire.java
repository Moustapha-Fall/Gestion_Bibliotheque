package com.bibliotheque.model;

import com.bibliotheque.enums.EnumExemplaire;

public class Exemplaire {

    private int id;
    private String dateAcquisition;
    private EnumExemplaire etatExemplaire;
    private int ouvrageId;
    private int annexeId;

    public Exemplaire(String dateAcquisition, int ouvrageId, int annexeId) {
        this.dateAcquisition = dateAcquisition;
        this.etatExemplaire = EnumExemplaire.DISPONIBLE; // disponible par defaut
        this.ouvrageId = ouvrageId;
        this.annexeId = annexeId;
    }

    public int getId() {
        return id;
    }

    public String getDateAcquisition() {
        return dateAcquisition;
    }

    public EnumExemplaire getEtatExemplaire() {
        return etatExemplaire;
    }

    public int getOuvrageId() {
        return ouvrageId;
    }

    public int getAnnexeId() {
        return annexeId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDateAcquisition(String d) {
        this.dateAcquisition = d;
    }

    public void setEtatExemplaire(EnumExemplaire e) {
        this.etatExemplaire = e;
    }

    public void setOuvrageId(int ouvrageId) {
        this.ouvrageId = ouvrageId;
    }

    public void setAnnexeId(int annexeId) {
        this.annexeId = annexeId;
    }

    public void changerEtat(EnumExemplaire nouvelEtat) {
        this.etatExemplaire = nouvelEtat;
    }

    public boolean estDisponible() {
        return etatExemplaire == EnumExemplaire.DISPONIBLE;
    }

    public String toString() {
        return "[Exemplaire #" + id + "] Etat: " + etatExemplaire
                + " | Acquisition: " + dateAcquisition;
    }
}
