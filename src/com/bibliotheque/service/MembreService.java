package com.bibliotheque.service;

import com.bibliotheque.model.Membre;
import java.sql.*;

// Service Membre (methodes statiques + Connection en parametre)
public class MembreService {

    // ajouter un membre
    public static void ajouterMembre(Connection conn, Membre m) throws SQLException {
        String sql = "INSERT INTO membres (email, nom, prenom, adresse, numeroCarte, quotaEmprunt)" +
                " VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, m.getEmail());
        pst.setString(2, m.getNom());
        pst.setString(3, m.getPrenom());
        pst.setString(4, m.getAdresse());
        pst.setString(5, m.getNumeroCarte());
        pst.setInt(6, m.getQuotaEmprunt());
        pst.executeUpdate();
        pst.close();
        System.out.println("Membre ajoute avec succes !");
    }

    // lister tous les membres
    public static void listerMembres(Connection conn) throws SQLException {
        String sql = "SELECT * FROM membres";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        System.out.println("Liste des membres :");
        boolean vide = true;
        while (rs.next()) {
            vide = false;
            System.out.println("  #" + rs.getInt("id")
                    + " | " + rs.getString("prenom") + " " + rs.getString("nom")
                    + " | Carte: " + rs.getString("numeroCarte")
                    + " | Quota: " + rs.getInt("quotaEmprunt"));
        }
        if (vide)
            System.out.println("  Aucun membre enregistre.");
        rs.close();
        stmt.close();
    }

    // chercher un membre par son numero de carte
    public static Membre trouverParCarte(Connection conn, String numeroCarte) throws SQLException {
        String sql = "SELECT * FROM membres WHERE numeroCarte = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, numeroCarte);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            Membre m = new Membre(
                    rs.getString("email"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("adresse"),
                    rs.getString("numeroCarte"),
                    rs.getInt("quotaEmprunt"));
            m.setId(rs.getInt("id"));
            rs.close();
            pst.close();
            return m;
        }
        rs.close();
        pst.close();
        return null;
    }

    // chercher un membre par son id
    public static Membre trouverParId(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM membres WHERE id = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            Membre m = new Membre(
                    rs.getString("email"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("adresse"),
                    rs.getString("numeroCarte"),
                    rs.getInt("quotaEmprunt"));
            m.setId(rs.getInt("id"));
            rs.close();
            pst.close();
            return m;
        }
        rs.close();
        pst.close();
        return null;
    }

    // mettre a jour le quota d'un membre
    public static void mettreAJourQuota(Connection conn, int membreId, int quota) throws SQLException {
        String sql = "UPDATE membres SET quotaEmprunt = ? WHERE id = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, quota);
        pst.setInt(2, membreId);
        pst.executeUpdate();
        pst.close();
    }
}
