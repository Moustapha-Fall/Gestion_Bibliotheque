package com.bibliotheque.service;

import java.sql.*;

public class CaisseService {

    // encaisser un montant dans la caisse
    public static void encaisser(Connection conn, double montant) throws SQLException {
        String sql = "UPDATE caisse SET solde = solde + ? WHERE id = 1";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setDouble(1, montant);
        pst.executeUpdate();
        pst.close();
    }

    // afficher le solde de la caisse
    public static void afficherSolde(Connection conn) throws SQLException {
        String sql = "SELECT solde FROM caisse WHERE id = 1";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            System.out.println("  Solde de la caisse : " + rs.getDouble("solde") + " FCFA");
        }
        rs.close();
        stmt.close();
    }
}
