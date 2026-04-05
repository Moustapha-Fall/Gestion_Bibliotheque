package com.bibliotheque.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

// Classe Service
// Connexion JDBC MySQL + creation des tables
public class Service {

    // 1/5 — Chaine de connexion
    public static Connection getConnexion(String URL, String USER, String PASSWORD) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connexion a la base de donnees reussie !");
            return conn;
        } catch (Exception e) {
            System.out.println("Erreur de connexion : " + e.getMessage());
            return null;
        }
    }

    // Creation des tables si elles n'existent pas
    public static void initialiserTables(Connection conn) {
        try {
            Statement stmt = conn.createStatement();

            // table membres
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS membres (" +
                            "id INT PRIMARY KEY AUTO_INCREMENT," +
                            "email VARCHAR(255)," +
                            "nom VARCHAR(255)," +
                            "prenom VARCHAR(255)," +
                            "adresse VARCHAR(255)," +
                            "numeroCarte VARCHAR(255) UNIQUE," +
                            "quotaEmprunt INT DEFAULT 3)");

            // table ouvrages
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS ouvrages (" +
                            "id INT PRIMARY KEY AUTO_INCREMENT," +
                            "isbn VARCHAR(255)," +
                            "titre VARCHAR(255)," +
                            "auteur VARCHAR(255)," +
                            "genre VARCHAR(50)," +
                            "datePublication VARCHAR(20))");

            // table annexes
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS annexes (" +
                            "id INT PRIMARY KEY AUTO_INCREMENT," +
                            "nom VARCHAR(255)," +
                            "adresse VARCHAR(255)," +
                            "telephone VARCHAR(50))");

            // table exemplaires
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS exemplaires (" +
                            "id INT PRIMARY KEY AUTO_INCREMENT," +
                            "dateAcquisition VARCHAR(20)," +
                            "etat VARCHAR(50) DEFAULT 'DISPONIBLE'," +
                            "ouvrageId INT," +
                            "annexeId INT)");

            // table emprunts
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS emprunts (" +
                            "id INT PRIMARY KEY AUTO_INCREMENT," +
                            "dateEmprunt VARCHAR(20)," +
                            "dateRetourPrevue VARCHAR(20)," +
                            "dateRetourReelle VARCHAR(20)," +
                            "statut VARCHAR(20) DEFAULT 'EN_COURS'," +
                            "membreId INT," +
                            "exemplaireId INT)");

            // table amendes
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS amendes (" +
                            "id INT PRIMARY KEY AUTO_INCREMENT," +
                            "montant DOUBLE," +
                            "paye INT DEFAULT 0," +
                            "motif VARCHAR(50)," +
                            "empruntId INT)");

            // table caisse (une seule ligne)
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS caisse (" +
                            "id INT PRIMARY KEY AUTO_INCREMENT," +
                            "solde DOUBLE DEFAULT 0)");

            // inserer la caisse si elle n'existe pas
            stmt.execute(
                    "INSERT IGNORE INTO caisse (id, solde) VALUES (1, 0)");

            // table vehicules
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS vehicules (" +
                            "id INT PRIMARY KEY AUTO_INCREMENT," +
                            "immatriculation VARCHAR(50)," +
                            "modele VARCHAR(100)," +
                            "marque VARCHAR(100)," +
                            "kilometrage DOUBLE DEFAULT 0)");

            // table conditions de stockage
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS conditions_stockage (" +
                            "id INT PRIMARY KEY AUTO_INCREMENT," +
                            "temperature DOUBLE," +
                            "humidite DOUBLE," +
                            "dateReleve VARCHAR(20)," +
                            "alerte INT DEFAULT 0," +
                            "annexeId INT)");

            stmt.close();
            System.out.println("Tables initialisees avec succes !");

        } catch (Exception e) {
            System.out.println("Erreur initialisation tables : " + e.getMessage());
        }
    }
}
