package com.bibliotheque.service;

import com.bibliotheque.model.Vehicule;
import com.bibliotheque.model.Annexe;
import java.sql.*;

public class VehiculeService {

    // ajouter un vehicule
    public static void ajouterVehicule(Connection conn, Vehicule v) throws SQLException {
        String sql = "INSERT INTO vehicules (immatriculation, modele, marque, kilometrage)" +
                " VALUES (?, ?, ?, ?)";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, v.getImmatriculation());
        pst.setString(2, v.getModele());
        pst.setString(3, v.getMarque());
        pst.setDouble(4, v.getKilometrage());
        pst.executeUpdate();
        pst.close();
        System.out.println("Vehicule ajoute avec succes !");
    }

    // lister tous les vehicules
    public static void listerVehicules(Connection conn) throws SQLException {
        String sql = "SELECT * FROM vehicules";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        System.out.println("Liste des vehicules :");
        boolean vide = true;
        while (rs.next()) {
            vide = false;
            System.out.println("  #" + rs.getInt("id")
                    + " | " + rs.getString("marque") + " " + rs.getString("modele")
                    + " | Immat: " + rs.getString("immatriculation")
                    + " | Km: " + rs.getDouble("kilometrage"));
        }
        if (vide)
            System.out.println("  Aucun vehicule enregistre.");
        rs.close();
        stmt.close();
    }

    // effectuer une livraison entre deux annexes
    public static void effectuerLivraison(Connection conn, int vehiculeId,
            Annexe depart, Annexe arrivee) throws SQLException {
        // verifier que le vehicule existe
        String sql = "SELECT * FROM vehicules WHERE id = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, vehiculeId);
        ResultSet rs = pst.executeQuery();
        if (!rs.next()) {
            System.out.println("  Vehicule introuvable !");
            rs.close();
            pst.close();
            return;
        }
        String info = rs.getString("marque") + " " + rs.getString("modele");
        rs.close();
        pst.close();

        System.out.println("  Livraison effectuee avec le vehicule : " + info);
        System.out.println("  Trajet : " + depart.getNom() + " --> " + arrivee.getNom());
    }
}
