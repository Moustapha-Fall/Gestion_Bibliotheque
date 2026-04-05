package com.bibliotheque.service;

import com.bibliotheque.model.Bibliothecaire;
import java.sql.*;

// Service Bibliothecaire — style du prof (methodes statiques + Connection en parametre)
public class BibliothecaireService {

    // 2/5 — Fonction d'insertion
    public static void ajouterBibliothecaire(Connection conn, Bibliothecaire b) throws SQLException {
        String sql = "INSERT INTO bibliothecaires (email, nom, prenom, adresse, matricule)" +
                     " VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, b.getEmail());
        pst.setString(2, b.getNom());
        pst.setString(3, b.getPrenom());
        pst.setString(4, b.getAdresse());
        pst.setString(5, b.getMatricule());
        pst.executeUpdate();
        pst.close();
        System.out.println("Bibliothecaire ajoute avec succes !");
    }

    // 3/5 — Fonction de consultation
    public static void listerBibliothecaires(Connection conn) throws SQLException {
        String sql = "SELECT * FROM bibliothecaires";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        System.out.println("Liste des bibliothecaires :");
        boolean vide = true;
        while (rs.next()) {
            vide = false;
            System.out.println("  #" + rs.getInt("id")
                + " | " + rs.getString("prenom") + " " + rs.getString("nom")
                + " | Matricule: " + rs.getString("matricule")
                + " | Email: " + rs.getString("email"));
        }
        if (vide) System.out.println("  Aucun bibliothecaire enregistre.");
        rs.close();
        stmt.close();
    }

    // chercher un bibliothecaire par matricule
    public static Bibliothecaire trouverParMatricule(Connection conn, String matricule) throws SQLException {
        String sql = "SELECT * FROM bibliothecaires WHERE matricule = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, matricule);
        ResultSet rs = pst.executeQuery();
        Bibliothecaire b = null;
        if (rs.next()) {
            b = new Bibliothecaire(
                rs.getString("email"),
                rs.getString("nom"),
                rs.getString("prenom"),
                rs.getString("adresse"),
                rs.getString("matricule")
            );
            b.setId(rs.getInt("id"));
        }
        rs.close();
        pst.close();
        return b;
    }

    // 4/5 — Fonction de mise a jour
    public static void mettreAJourEmail(Connection conn, int id, String nouvelEmail) throws SQLException {
        String sql = "UPDATE bibliothecaires SET email = ? WHERE id = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, nouvelEmail);
        pst.setInt(2, id);
        pst.executeUpdate();
        pst.close();
        System.out.println("Email du bibliothecaire mis a jour !");
    }

    // 5/5 — Fonction de suppression
    public static void supprimerBibliothecaire(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM bibliothecaires WHERE id = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, id);
        pst.executeUpdate();
        pst.close();
        System.out.println("Bibliothecaire supprime avec succes !");
    }
}
