package com.bibliotheque.service;

import com.bibliotheque.enums.EnumAmende;
import java.sql.*;

public class AmendeService {

    // ajouter une amende
    public static void ajouterAmende(Connection conn, double montant,
            EnumAmende motif, int empruntId) throws SQLException {
        String sql = "INSERT INTO amendes (montant, paye, motif, empruntId)" +
                " VALUES (?, 0, ?, ?)";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setDouble(1, montant);
        pst.setString(2, motif.name());
        pst.setInt(3, empruntId);
        pst.executeUpdate();
        pst.close();
        System.out.println("  Amende de " + montant + " FCFA enregistree !");
    }

    // lister les amendes non payees d'un membre
    public static void listerAmendesNonPayees(Connection conn, int membreId) throws SQLException {
        String sql = "SELECT a.* FROM amendes a" +
                " JOIN emprunts e ON e.id = a.empruntId" +
                " WHERE e.membreId = ? AND a.paye = 0";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, membreId);
        ResultSet rs = pst.executeQuery();
        System.out.println("Amendes non payees :");
        boolean vide = true;
        while (rs.next()) {
            vide = false;
            System.out.println("  Amende #" + rs.getInt("id")
                    + " | Montant: " + rs.getDouble("montant") + " FCFA"
                    + " | Motif: " + rs.getString("motif"));
        }
        if (vide)
            System.out.println("  Aucune amende non payee.");
        rs.close();
        pst.close();
    }

    // lister toutes les amendes
    public static void listerToutesLesAmendes(Connection conn) throws SQLException {
        String sql = "SELECT a.*, m.prenom, m.nom FROM amendes a" +
                " JOIN emprunts e ON e.id = a.empruntId" +
                " JOIN membres m ON m.id = e.membreId";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        System.out.println("Liste de toutes les amendes :");
        boolean vide = true;
        while (rs.next()) {
            vide = false;
            System.out.println("  Amende #" + rs.getInt("id")
                    + " | Membre: " + rs.getString("prenom") + " " + rs.getString("nom")
                    + " | Montant: " + rs.getDouble("montant") + " FCFA"
                    + " | Motif: " + rs.getString("motif")
                    + " | Payee: " + (rs.getInt("paye") == 1 ? "OUI" : "NON"));
        }
        if (vide)
            System.out.println("  Aucune amende.");
        rs.close();
        stmt.close();
    }

    // payer une amende
    public static void payerAmende(Connection conn, int amendeId) throws SQLException {
        // recuperer le montant
        String sqlSelect = "SELECT montant FROM amendes WHERE id = ?";
        PreparedStatement pst1 = conn.prepareStatement(sqlSelect);
        pst1.setInt(1, amendeId);
        ResultSet rs = pst1.executeQuery();
        if (!rs.next()) {
            System.out.println("  Amende introuvable !");
            rs.close();
            pst1.close();
            return;
        }
        double montant = rs.getDouble("montant");
        rs.close();
        pst1.close();

        // marquer comme payee
        String sqlUpdate = "UPDATE amendes SET paye = 1 WHERE id = ?";
        PreparedStatement pst2 = conn.prepareStatement(sqlUpdate);
        pst2.setInt(1, amendeId);
        pst2.executeUpdate();
        pst2.close();

        // encaisser dans la caisse
        CaisseService.encaisser(conn, montant);

        System.out.println("  Amende #" + amendeId + " payee ! Montant encaisse : " + montant + " FCFA");
    }
}
