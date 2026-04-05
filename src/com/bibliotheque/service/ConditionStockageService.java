package com.bibliotheque.service;

import com.bibliotheque.model.ConditionStockage;
import java.sql.*;

public class ConditionStockageService {

    // ajouter un releve de conditions
    public static void ajouterReleve(Connection conn, ConditionStockage c) throws SQLException {
        // verifier les conditions avant d'enregistrer
        boolean alerteActive = !c.verifierConditions();
        if (alerteActive) {
            c.declencherAlerte();
        }

        String sql = "INSERT INTO conditions_stockage (temperature, humidite, dateReleve, alerte, annexeId)" +
                " VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setDouble(1, c.getTemperature());
        pst.setDouble(2, c.getHumidite());
        pst.setString(3, c.getDateReleve());
        pst.setInt(4, alerteActive ? 1 : 0);
        pst.setInt(5, c.getAnnexeId());
        pst.executeUpdate();
        pst.close();

        if (!alerteActive) {
            System.out.println("  Releve enregistre. Conditions normales.");
        }
    }

    // lister tous les releves avec alertes
    public static void listerAlertes(Connection conn) throws SQLException {
        String sql = "SELECT cs.*, a.nom FROM conditions_stockage cs" +
                " JOIN annexes a ON a.id = cs.annexeId" +
                " WHERE cs.alerte = 1";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        System.out.println("Alertes de stockage :");
        boolean vide = true;
        while (rs.next()) {
            vide = false;
            System.out.println("  ⚠  Annexe: " + rs.getString("nom")
                    + " | Temp: " + rs.getDouble("temperature") + "°C"
                    + " | Humidite: " + rs.getDouble("humidite") + "%"
                    + " | Date: " + rs.getString("dateReleve"));
        }
        if (vide)
            System.out.println("  Aucune alerte detectee.");
        rs.close();
        stmt.close();
    }

    // lister tous les releves
    public static void listerTousLesReleves(Connection conn) throws SQLException {
        String sql = "SELECT cs.*, a.nom FROM conditions_stockage cs" +
                " JOIN annexes a ON a.id = cs.annexeId" +
                " ORDER BY cs.dateReleve DESC";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        System.out.println("Historique des releves :");
        boolean vide = true;
        while (rs.next()) {
            vide = false;
            System.out.println("  #" + rs.getInt("id")
                    + " | Annexe: " + rs.getString("nom")
                    + " | Temp: " + rs.getDouble("temperature") + "°C"
                    + " | Humidite: " + rs.getDouble("humidite") + "%"
                    + " | Date: " + rs.getString("dateReleve")
                    + " | Alerte: " + (rs.getInt("alerte") == 1 ? "OUI" : "NON"));
        }
        if (vide)
            System.out.println("  Aucun releve enregistre.");
        rs.close();
        stmt.close();
    }
}
