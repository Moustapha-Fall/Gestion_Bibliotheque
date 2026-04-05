package com.bibliotheque.service;

import com.bibliotheque.model.Livre;
import com.bibliotheque.model.Ouvrage;
import com.bibliotheque.enums.EnumOuvrage;
import java.sql.*;

public class OuvrageService {

    // ajouter un ouvrage
    public static void ajouterOuvrage(Connection conn, Ouvrage o) throws SQLException {
        String sql = "INSERT INTO ouvrages (isbn, titre, auteur, genre, datePublication)" +
                " VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, o.getIsbn());
        pst.setString(2, o.getTitre());
        pst.setString(3, o.getAuteur());
        pst.setString(4, o.getGenre().name());
        pst.setString(5, o.getDatePublication());
        pst.executeUpdate();
        pst.close();
        System.out.println("Ouvrage ajoute avec succes !");
    }

    // lister tous les ouvrages
    public static void listerOuvrages(Connection conn) throws SQLException {
        String sql = "SELECT * FROM ouvrages";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        System.out.println("Liste des ouvrages :");
        boolean vide = true;
        while (rs.next()) {
            vide = false;
            System.out.println("  #" + rs.getInt("id")
                    + " | " + rs.getString("titre")
                    + " | " + rs.getString("auteur")
                    + " | " + rs.getString("genre")
                    + " | ISBN: " + rs.getString("isbn"));
        }
        if (vide)
            System.out.println("  Aucun ouvrage enregistre.");
        rs.close();
        stmt.close();
    }

    // lister les ouvrages disponibles (qui ont au moins un exemplaire DISPONIBLE)
    public static void listerOuvragesDisponibles(Connection conn) throws SQLException {
        String sql = "SELECT DISTINCT o.* FROM ouvrages o" +
                " JOIN exemplaires e ON e.ouvrageId = o.id" +
                " WHERE e.etat = 'DISPONIBLE'";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        System.out.println("Ouvrages disponibles :");
        boolean vide = true;
        while (rs.next()) {
            vide = false;
            System.out.println("  #" + rs.getInt("id")
                    + " | " + rs.getString("titre")
                    + " | " + rs.getString("auteur")
                    + " | " + rs.getString("genre"));
        }
        if (vide)
            System.out.println("  Aucun ouvrage disponible.");
        rs.close();
        stmt.close();
    }

    // chercher un ouvrage par id
    public static Ouvrage trouverParId(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM ouvrages WHERE id = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            Livre l = new Livre(
                    rs.getString("isbn"),
                    rs.getString("titre"),
                    rs.getString("auteur"),
                    EnumOuvrage.valueOf(rs.getString("genre")),
                    rs.getString("datePublication"));
            l.setId(rs.getInt("id"));
            rs.close();
            pst.close();
            return l;
        }
        rs.close();
        pst.close();
        return null;
    }

    // supprimer un ouvrage
    public static void supprimerOuvrage(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM ouvrages WHERE id = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, id);
        pst.executeUpdate();
        pst.close();
        System.out.println("Ouvrage supprime avec succes !");
    }
}
