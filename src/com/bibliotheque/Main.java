package com.bibliotheque;

import com.bibliotheque.service.*;
import com.bibliotheque.model.*;
import com.bibliotheque.enums.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Main {

        // INFORMATIONS DE CONNEXION MySQL 

        static final String URL = "jdbc:mysql://localhost:3306/bibliotheque?createDatabaseIfNotExist=true";
        static final String USER = "root";
        static final String PASSWORD = ""; // modifier si votre MySQL a un mot de passe

        static Connection conn;
        static Scanner scanner;

        // POINT D'ENTREE

        public static void main(String[] args) {

                titre("SIMULATION DU SYSTEME DE BIBLIOTHEQUE MUNICIPALE");
                scanner = new Scanner(System.in);
                System.out.println("\nAppuyez sur Entree pour demarrer la simulation...");
                scanner.nextLine();

                // Connexion a la base de donnees — style du prof
                conn = Service.getConnexion(URL, USER, PASSWORD);
                if (conn == null) {
                        System.out.println("Impossible de se connecter.");
                        System.out.println("Verifiez que MySQL est demarre et que le JAR mysql-connector est present.");
                        return;
                }

                // Initialisation des tables
                Service.initialiserTables(conn);

                try {
                        reinitialiserDonnees();
                        simulerScenario();
                } catch (Exception e) {
                        System.out.println("Erreur durant la simulation : " + e.getMessage());
                        e.printStackTrace();
                }

                try {
                        conn.close();
                        scanner.close();
                } catch (Exception ignored) {
                }

                sep();
                System.out.println("  FIN DE LA SIMULATION");
                sep();
        }

        // SCENARIO COMPLET
        static void simulerScenario() throws Exception {

                // ETAPE 1 : Creation des annexes
        
                titre("ETAPE 1 : CREATION DES ANNEXES");
                System.out.println("\nAppuyez sur Entree pour continuer...");
                scanner.nextLine();

                action("Ajout de l'annexe principale : Dakar Centre");
                AnnexeService.ajouterAnnexe(conn,
                                new Annexe("Dakar Centre", "1 Rue de la Republique, Dakar", "33-821-0001"));

                action("Ajout de l'annexe secondaire : Dakar Nord");
                AnnexeService.ajouterAnnexe(conn,
                                new Annexe("Dakar Nord", "45 Av. Cheikh Anta Diop, Dakar", "33-824-0002"));

                etat("Etat des annexes apres creation :");
                AnnexeService.listerAnnexes(conn);

                Annexe annexeCentre = getAnnexe("Dakar Centre");
                Annexe annexeNord = getAnnexe("Dakar Nord");

                // ETAPE 2 : Ajout des vehicules

                titre("ETAPE 2 : AJOUT DES VEHICULES DE LIVRAISON");
                System.out.println("\nAppuyez sur Entree pour continuer...");
                scanner.nextLine();

                action("Enregistrement du vehicule 1 : Renault Kangoo");
                VehiculeService.ajouterVehicule(conn,
                                new Vehicule("DK-1234-AB", "Kangoo", "Renault", 45000.0));

                action("Enregistrement du vehicule 2 : Ford Transit");
                VehiculeService.ajouterVehicule(conn,
                                new Vehicule("DK-5678-CD", "Transit", "Ford", 78500.0));

                etat("Flotte de vehicules disponible :");
                VehiculeService.listerVehicules(conn);

                int idVehicule = getPremierVehiculeId();

                // ETAPE 3 : Ajout des ouvrages au catalogue
    
                titre("ETAPE 3 : CONSTITUTION DU CATALOGUE");
                System.out.println("\nAppuyez sur Entree pour continuer...");
                scanner.nextLine();

                action("Ajout : 'Les Bouts de Bois de Dieu' de Sembene Ousmane");
                OuvrageService.ajouterOuvrage(conn, new Livre(
                                "978-2-07-036024-5", "Les Bouts de Bois de Dieu",
                                "Sembene Ousmane", EnumOuvrage.ROMAN, "1960-01-01"));

                action("Ajout : 'Une si longue lettre' de Mariama Ba");
                OuvrageService.ajouterOuvrage(conn, new Livre(
                                "978-2-296-04028-2", "Une si longue lettre",
                                "Mariama Ba", EnumOuvrage.ROMAN, "1979-01-01"));

                action("Ajout : 'L Enfant noir' de Camara Laye");
                OuvrageService.ajouterOuvrage(conn, new Livre(
                                "978-2-07-036615-5", "L Enfant noir",
                                "Camara Laye", EnumOuvrage.ROMAN, "1953-01-01"));

                etat("Catalogue complet apres ajout :");
                OuvrageService.listerOuvrages(conn);

                Ouvrage ouvrage1 = getOuvrage("978-2-07-036024-5");
                Ouvrage ouvrage2 = getOuvrage("978-2-296-04028-2");
                Ouvrage ouvrage3 = getOuvrage("978-2-07-036615-5");

                // ETAPE 4 : Ajout des exemplaires physiques
        
                titre("ETAPE 4 : AJOUT DES EXEMPLAIRES PHYSIQUES");
                System.out.println("\nAppuyez sur Entree pour continuer...");
                scanner.nextLine();

                action("3 exemplaires de 'Les Bouts de Bois de Dieu' (2 au Centre, 1 au Nord)");
                ExemplaireService.ajouterExemplaire(conn,
                                new Exemplaire("2023-01-15", ouvrage1.getId(), annexeCentre.getId()));
                ExemplaireService.ajouterExemplaire(conn,
                                new Exemplaire("2023-03-10", ouvrage1.getId(), annexeCentre.getId()));
                ExemplaireService.ajouterExemplaire(conn,
                                new Exemplaire("2023-05-20", ouvrage1.getId(), annexeNord.getId()));

                action("1 exemplaire de 'Une si longue lettre' (Centre)");
                ExemplaireService.ajouterExemplaire(conn,
                                new Exemplaire("2022-06-01", ouvrage2.getId(), annexeCentre.getId()));

                action("1 exemplaire de 'L Enfant noir' (Nord)");
                ExemplaireService.ajouterExemplaire(conn,
                                new Exemplaire("2021-09-20", ouvrage3.getId(), annexeNord.getId()));

                etat("Ouvrages disponibles a l'emprunt apres ajout des exemplaires :");
                OuvrageService.listerOuvragesDisponibles(conn);

                // ETAPE 5 : Inscription des membres
        
                titre("ETAPE 5 : INSCRIPTION DES MEMBRES");
                System.out.println("\nAppuyez sur Entree pour continuer...");
                scanner.nextLine();

                action("Inscription de Amadou Diallo (quota = 3 emprunts)");
                MembreService.ajouterMembre(conn, new Membre(
                                "amadou@mail.com", "Diallo", "Amadou",
                                "12 Rue Tolbiac, Dakar", "CARTE-001", 3));

                action("Inscription de Fatou Ndiaye (quota = 3 emprunts)");
                MembreService.ajouterMembre(conn, new Membre(
                                "fatou@mail.com", "Ndiaye", "Fatou",
                                "78 Avenue Bourguiba, Dakar", "CARTE-002", 3));

                action("Inscription de Oumar Sow (quota = 3 emprunts)");
                MembreService.ajouterMembre(conn, new Membre(
                                "oumar@mail.com", "Sow", "Oumar",
                                "3 Boulevard Liberation, Dakar", "CARTE-003", 3));

                etat("Liste des membres inscrits :");
                MembreService.listerMembres(conn);

                // Charger les membres avec leurs IDs reels
                Membre amadou = MembreService.trouverParCarte(conn, "CARTE-001");
                Membre fatou = MembreService.trouverParCarte(conn, "CARTE-002");
                Membre oumar = MembreService.trouverParCarte(conn, "CARTE-003");

                // ETAPE 6 : Emprunts multiples
        
                titre("ETAPE 6 : GESTION DES EMPRUNTS");
                System.out.println("\nAppuyez sur Entree pour continuer...");
                scanner.nextLine();

                // Amadou fait 3 emprunts successifs pour epuiser son quota
                action("Amadou emprunte 'Les Bouts de Bois de Dieu' (1er exemplaire)");
                EmpruntService.emprunter(conn, amadou, ouvrage1.getId(), "2026-01-05", "2026-01-20");
                amadou = MembreService.trouverParId(conn, amadou.getId());
                System.out.println("  Quota restant d'Amadou : " + amadou.getQuotaEmprunt() + "/3");

                etat("Ouvrages disponibles apres le 1er emprunt d'Amadou :");
                OuvrageService.listerOuvragesDisponibles(conn);

                action("Amadou emprunte 'Les Bouts de Bois de Dieu' (2e exemplaire)");
                EmpruntService.emprunter(conn, amadou, ouvrage1.getId(), "2026-01-06", "2026-01-21");
                amadou = MembreService.trouverParId(conn, amadou.getId());
                System.out.println("  Quota restant d'Amadou : " + amadou.getQuotaEmprunt() + "/3");

                action("Amadou emprunte 'Les Bouts de Bois de Dieu' (3e exemplaire — quota epuise apres)");
                EmpruntService.emprunter(conn, amadou, ouvrage1.getId(), "2026-01-07", "2026-01-22");
                amadou = MembreService.trouverParId(conn, amadou.getId());
                System.out.println("  Quota restant d'Amadou : " + amadou.getQuotaEmprunt() + "/3");

                action("Fatou emprunte 'Une si longue lettre'");
                EmpruntService.emprunter(conn, fatou, ouvrage2.getId(), "2026-01-08", "2026-01-23");
                fatou = MembreService.trouverParId(conn, fatou.getId());
                System.out.println("  Quota restant de Fatou : " + fatou.getQuotaEmprunt() + "/3");

                action("Oumar emprunte 'L Enfant noir'");
                EmpruntService.emprunter(conn, oumar, ouvrage3.getId(), "2026-01-09", "2026-01-24");
                oumar = MembreService.trouverParId(conn, oumar.getId());
                System.out.println("  Quota restant d'Oumar : " + oumar.getQuotaEmprunt() + "/3");

                etat("Tous les emprunts en cours :");
                EmpruntService.listerEmpruntsEnCours(conn);

                etat("Ouvrages encore disponibles (tous les exemplaires de Bouts de Bois sont pris) :");
                OuvrageService.listerOuvragesDisponibles(conn);
        
                // ETAPE 7 : Test du depassement de quota
        
                titre("ETAPE 7 : TEST DU DEPASSEMENT DE QUOTA");
                System.out.println("\nAppuyez sur Entree pour continuer...");
                scanner.nextLine();

                action("Amadou tente un 4e emprunt (quota = 0) -> refus attendu");
                EmpruntService.emprunter(conn, amadou, ouvrage2.getId(), "2026-01-10", "2026-01-25");

                etat("Les emprunts en cours restent inchanges (Amadou n'a pas pu emprunter) :");
                EmpruntService.listerEmpruntsEnCours(conn);
        
                // ETAPE 8 : Retour a temps (Fatou)
        
                titre("ETAPE 8 : RETOUR A TEMPS — FATOU NDIAYE");
                System.out.println("\nAppuyez sur Entree pour continuer...");
                scanner.nextLine();

                int empruntFatouId = getEmpruntEnCoursByMembre(fatou.getId());
                action("Fatou retourne 'Une si longue lettre' 3 jours avant la date prevue (20 jan < 23 jan)");
                EmpruntService.retourner(conn, empruntFatouId, "2026-01-20");
                fatou = MembreService.trouverParId(conn, fatou.getId());
                System.out.println("  Quota de Fatou apres retour : " + fatou.getQuotaEmprunt() + "/3");

                etat("Pas d'amende pour Fatou — etat global des amendes :");
                AmendeService.listerToutesLesAmendes(conn);

                etat("Ouvrages disponibles apres le retour de Fatou :");
                OuvrageService.listerOuvragesDisponibles(conn);

                etat("Emprunts en cours apres le retour de Fatou :");
                EmpruntService.listerEmpruntsEnCours(conn);

                // ETAPE 9 : Retour en retard + amende (Oumar)
        
                titre("ETAPE 9 : RETOUR EN RETARD + AMENDE — OUMAR SOW");
                System.out.println("\nAppuyez sur Entree pour continuer...");
                scanner.nextLine();

                int empruntOumarId = getEmpruntEnCoursByMembre(oumar.getId());
                action("Oumar retourne 'L Enfant noir' avec 10 jours de retard (prevu : 24 jan | rendu : 03 fev)");
                EmpruntService.retourner(conn, empruntOumarId, "2026-02-03");
                oumar = MembreService.trouverParId(conn, oumar.getId());
                System.out.println("  Quota d'Oumar apres retour : " + oumar.getQuotaEmprunt() + "/3");

                action("Enregistrement de l'amende : 10 jours x 500 FCFA = 5 000 FCFA (motif : DEPASSE)");
                AmendeService.ajouterAmende(conn, 5000.0, EnumAmende.DEPASSE, empruntOumarId);

                etat("Etat des amendes apres enregistrement :");
                AmendeService.listerToutesLesAmendes(conn);

                etat("Solde de la caisse avant paiement de l'amende :");
                CaisseService.afficherSolde(conn);
        
                // ETAPE 10 : Paiement de l'amende
        
                titre("ETAPE 10 : PAIEMENT DE L'AMENDE");
                System.out.println("\nAppuyez sur Entree pour continuer...");
                scanner.nextLine();

                int amendeId = getDerniereAmendeNonPayee();
                action("Oumar s'acquitte de l'amende #" + amendeId + " : 5 000 FCFA");
                AmendeService.payerAmende(conn, amendeId);

                etat("Etat des amendes apres paiement :");
                AmendeService.listerToutesLesAmendes(conn);

                etat("Solde de la caisse apres encaissement de l'amende :");
                CaisseService.afficherSolde(conn);
        
                // ETAPE 11 : Livraison entre annexes
        
                titre("ETAPE 11 : LIVRAISON ENTRE ANNEXES");
                System.out.println("\nAppuyez sur Entree pour continuer...");
                scanner.nextLine();

                action("Livraison de livres de Dakar Centre vers Dakar Nord avec le Renault Kangoo");
                VehiculeService.effectuerLivraison(conn, idVehicule, annexeCentre, annexeNord);

                etat("Flotte de vehicules (inchangee) :");
                VehiculeService.listerVehicules(conn);
        
                // ETAPE 12 : Releves de conditions de stockage
        
                titre("ETAPE 12 : RELEVES DE CONDITIONS DE STOCKAGE");
                System.out.println("\nAppuyez sur Entree pour continuer...");
                scanner.nextLine();

                action("Releve normal a Dakar Centre : 18 C / 50% humidite (dans les normes)");
                ConditionStockageService.ajouterReleve(conn,
                                new ConditionStockage(18.0, 50.0, "2026-01-15", annexeCentre.getId()));

                action("Releve anormal a Dakar Nord : 28 C / 80% humidite -> ALERTE attendue");
                ConditionStockageService.ajouterReleve(conn,
                                new ConditionStockage(28.0, 80.0, "2026-01-15", annexeNord.getId()));

                action("Releve de suivi a Dakar Nord : 17 C / 45% humidite (conditions corrigees)");
                ConditionStockageService.ajouterReleve(conn,
                                new ConditionStockage(17.0, 45.0, "2026-01-16", annexeNord.getId()));

                etat("Alertes de stockage actives :");
                ConditionStockageService.listerAlertes(conn);

                etat("Historique complet des releves de conditions :");
                ConditionStockageService.listerTousLesReleves(conn);

                // BILAN FINAL
        
                titre("BILAN FINAL DU SYSTEME");
                System.out.println("\nAppuyez sur Entree pour voir le bilan final...");
                scanner.nextLine();

                etat("Membres de la bibliotheque :");
                MembreService.listerMembres(conn);

                etat("Catalogue complet des ouvrages :");
                OuvrageService.listerOuvrages(conn);

                etat("Ouvrages actuellement disponibles a l'emprunt :");
                OuvrageService.listerOuvragesDisponibles(conn);

                etat("Emprunts encore en cours :");
                EmpruntService.listerEmpruntsEnCours(conn);

                etat("Recapitulatif de toutes les amendes :");
                AmendeService.listerToutesLesAmendes(conn);

                etat("Solde final de la caisse :");
                CaisseService.afficherSolde(conn);

                etat("Annexes de la bibliotheque :");
                AnnexeService.listerAnnexes(conn);

                etat("Vehicules de la bibliotheque :");
                VehiculeService.listerVehicules(conn);
        }

        // REINITIALISATION DE LA BASE POUR UNE SIMULATION PROPRE
        static void reinitialiserDonnees() throws Exception {
                Statement stmt = conn.createStatement();

                // supprimer toutes les donnees (style du prof : DELETE FROM)
                stmt.execute("DELETE FROM conditions_stockage");
                stmt.execute("DELETE FROM amendes");
                stmt.execute("DELETE FROM emprunts");
                stmt.execute("DELETE FROM exemplaires");
                stmt.execute("DELETE FROM vehicules");
                stmt.execute("DELETE FROM ouvrages");
                stmt.execute("DELETE FROM membres");
                stmt.execute("DELETE FROM annexes");
                stmt.execute("UPDATE caisse SET solde = 0 WHERE id = 1");

                // reinitialiser les compteurs AUTO_INCREMENT (MySQL)
                stmt.execute("ALTER TABLE conditions_stockage AUTO_INCREMENT = 1");
                stmt.execute("ALTER TABLE amendes AUTO_INCREMENT = 1");
                stmt.execute("ALTER TABLE emprunts AUTO_INCREMENT = 1");
                stmt.execute("ALTER TABLE exemplaires AUTO_INCREMENT = 1");
                stmt.execute("ALTER TABLE vehicules AUTO_INCREMENT = 1");
                stmt.execute("ALTER TABLE ouvrages AUTO_INCREMENT = 1");
                stmt.execute("ALTER TABLE membres AUTO_INCREMENT = 1");
                stmt.execute("ALTER TABLE annexes AUTO_INCREMENT = 1");

                stmt.close();
                System.out.println("  Base de donnees reinitalisee — simulation en cours...");
        }

        // UTILITAIRES D'AFFICHAGE

        static void sep() {
                System.out.println("==========================================================");
        }

        static void titre(String t) {
                System.out.println();
                sep();
                System.out.println("  " + t);
                sep();
        }

        static void action(String msg) {
                System.out.println();
                System.out.println("  >>> " + msg);
                System.out.println("  ----------------------------------------------------------");
        }

        static void etat(String msg) {
                System.out.println();
                System.out.println("  [ETAT] " + msg);
        }

        // UTILITAIRES DE REQUETES DIRECTES
        // Recuperer une annexe par son nom
        static Annexe getAnnexe(String nom) throws Exception {
                PreparedStatement pst = conn.prepareStatement(
                                "SELECT * FROM annexes WHERE nom = ? LIMIT 1");
                pst.setString(1, nom);
                ResultSet rs = pst.executeQuery();
                Annexe a = null;
                if (rs.next()) {
                        a = new Annexe(rs.getString("nom"), rs.getString("adresse"), rs.getString("telephone"));
                        a.setId(rs.getInt("id"));
                }
                rs.close();
                pst.close();
                return a;
        }

        // Recuperer un ouvrage par son ISBN
        static Ouvrage getOuvrage(String isbn) throws Exception {
                PreparedStatement pst = conn.prepareStatement(
                                "SELECT * FROM ouvrages WHERE isbn = ? LIMIT 1");
                pst.setString(1, isbn);
                ResultSet rs = pst.executeQuery();
                Ouvrage o = null;
                if (rs.next()) {
                        Livre l = new Livre(
                                        rs.getString("isbn"), rs.getString("titre"),
                                        rs.getString("auteur"),
                                        EnumOuvrage.valueOf(rs.getString("genre")),
                                        rs.getString("datePublication"));
                        l.setId(rs.getInt("id"));
                        o = l;
                }
                rs.close();
                pst.close();
                return o;
        }

        // Recuperer l'ID du premier emprunt en cours d'un membre
        static int getEmpruntEnCoursByMembre(int membreId) throws Exception {
                PreparedStatement pst = conn.prepareStatement(
                                "SELECT id FROM emprunts WHERE membreId = ? AND statut = 'EN_COURS' LIMIT 1");
                pst.setInt(1, membreId);
                ResultSet rs = pst.executeQuery();
                int id = -1;
                if (rs.next())
                        id = rs.getInt("id");
                rs.close();
                pst.close();
                return id;
        }

        // Recuperer la derniere amende non payee
        static int getDerniereAmendeNonPayee() throws Exception {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(
                                "SELECT id FROM amendes WHERE paye = 0 ORDER BY id DESC LIMIT 1");
                int id = -1;
                if (rs.next())
                        id = rs.getInt("id");
                rs.close();
                stmt.close();
                return id;
        }

        // Recuperer l'ID du premier vehicule enregistre
        static int getPremierVehiculeId() throws Exception {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT id FROM vehicules ORDER BY id ASC LIMIT 1");
                int id = -1;
                if (rs.next())
                        id = rs.getInt("id");
                rs.close();
                stmt.close();
                return id;
        }
}
