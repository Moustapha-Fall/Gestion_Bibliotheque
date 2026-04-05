package com.bibliotheque.service;

import com.bibliotheque.model.Annexe;
import java.sql.*;

public class AnnexeService {

    // ajouter une annexe
    public static void ajouterAnnexe(Connection conn, Annexe a) throws SQLException {
        String sql = "INSERT INTO annexes (nom, adresse, telephone) VALUES (?, ?, ?)";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, a.getNom());
        pst.setString(2, a.getAdresse());
        pst.setString(3, a.getTelephone());
        pst.executeUpdate();
        pst.close();
        System.out.println("Annexe ajoutee avec succes !");
    }

    // lister toutes les annexes
    public static void listerAnnexes(Connection conn) throws SQLException {
        String sql = "SELECT * FROM annexes";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        System.out.println("Liste des annexes :");
        boolean vide = true;
        while (rs.next()) {
            vide = false;
            System.out.println("  #" + rs.getInt("id")
                    + " | " + rs.getString("nom")
                    + " | " + rs.getString("adresse")
                    + " | Tel: " + rs.getString("telephone"));
        }
        if (vide)
            System.out.println("  Aucune annexe enregistree.");
        rs.close();
        stmt.close();
    }

    // chercher une annexe par id
    public static Annexe trouverParId(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM annexes WHERE id = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            Annexe a = new Annexe(
                    rs.getString("nom"),
                    rs.getString("adresse"),
                    rs.getString("telephone"));
            a.setId(rs.getInt("id"));
            rs.close();
            pst.close();
            return a;
        }
        rs.close();
        pst.close();
        return null;
    }
}
