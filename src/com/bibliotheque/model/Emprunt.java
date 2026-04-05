package com.bibliotheque.model;

import com.bibliotheque.enums.EnumEmprunt;

public class Emprunt {

    private int id;
    private String dateEmprunt;
    private String dateRetourPrevue;
    private String dateRetourReelle;
    private EnumEmprunt statut;
    private int membreId;
    private int exemplaireId;

    public Emprunt(String dateEmprunt, String dateRetourPrevue,
            int membreId, int exemplaireId) {
        this.dateEmprunt = dateEmprunt;
        this.dateRetourPrevue = dateRetourPrevue;
        this.dateRetourReelle = null;
        this.statut = EnumEmprunt.EN_COURS;
        this.membreId = membreId;
        this.exemplaireId = exemplaireId;
    }

    public int getId() {
        return id;
    }

    public String getDateEmprunt() {
        return dateEmprunt;
    }

    public String getDateRetourPrevue() {
        return dateRetourPrevue;
    }

    public String getDateRetourReelle() {
        return dateRetourReelle;
    }

    public EnumEmprunt getStatut() {
        return statut;
    }

    public int getMembreId() {
        return membreId;
    }

    public int getExemplaireId() {
        return exemplaireId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDateEmprunt(String d) {
        this.dateEmprunt = d;
    }

    public void setDateRetourPrevue(String d) {
        this.dateRetourPrevue = d;
    }

    public void setDateRetourReelle(String d) {
        this.dateRetourReelle = d;
    }

    public void setStatut(EnumEmprunt s) {
        this.statut = s;
    }

    public void setMembreId(int membreId) {
        this.membreId = membreId;
    }

    public void setExemplaireId(int exemplaireId) {
        this.exemplaireId = exemplaireId;
    }

    public String toString() {
        return "[Emprunt #" + id + "] "
                + "Emprunte le: " + dateEmprunt
                + " | Retour prevu: " + dateRetourPrevue
                + " | Statut: " + statut;
    }
}
