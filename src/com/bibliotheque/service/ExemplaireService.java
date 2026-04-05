package com.bibliotheque.service;

import com.bibliotheque.model.Exemplaire;
import com.bibliotheque.enums.EnumExemplaire;
import java.sql.*;

public class ExemplaireService {

    // ajouter un exemplaire
    public static void ajouterExemplaire(Connection conn, Exemplaire e) throws SQLException {
        String sql = "INSERT INTO exemplaires (dateAcquisition, etat, ouvrageId, annexeId)" +
                " VALUES (?, ?, ?, ?)";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, e.getDateAcquisition());
        pst.setString(2, e.getEtatExemplaire().name());
        pst.setInt(3, e.getOuvrageId());
        pst.setInt(4, e.getAnnexeId());
        pst.executeUpdate();
        pst.close();
        System.out.println("Exemplaire ajoute avec succes !");
    }

    // trouver un exemplaire disponible pour un ouvrage donne
    public static Exemplaire trouverDisponible(Connection conn, int ouvrageId) throws SQLException {
        String sql = "SELECT * FROM exemplaires WHERE ouvrageId = ? AND etat = 'DISPONIBLE' LIMIT 1";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, ouvrageId);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            Exemplaire ex = new Exemplaire(
                    rs.getString("dateAcquisition"),
                    rs.getInt("ouvrageId"),
                    rs.getInt("annexeId"));
            ex.setId(rs.getInt("id"));
            ex.setEtatExemplaire(EnumExemplaire.valueOf(rs.getString("etat")));
            rs.close();
            pst.close();
            return ex;
        }
        rs.close();
        pst.close();
        return null;
    }

    // mettre a jour l'etat d'un exemplaire
    public static void changerEtat(Connection conn, int exemplaireId, EnumExemplaire nouvelEtat) throws SQLException {
        String sql = "UPDATE exemplaires SET etat = ? WHERE id = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, nouvelEtat.name());
        pst.setInt(2, exemplaireId);
        pst.executeUpdate();
        pst.close();
    }

    // lister les exemplaires d'un ouvrage
    public static void listerParOuvrage(Connection conn, int ouvrageId) throws SQLException {
        String sql = "SELECT * FROM exemplaires WHERE ouvrageId = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, ouvrageId);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            System.out.println("  Exemplaire #" + rs.getInt("id")
                    + " | Etat: " + rs.getString("etat")
                    + " | Acquisition: " + rs.getString("dateAcquisition"));
        }
        rs.close();
        pst.close();
    }
}
